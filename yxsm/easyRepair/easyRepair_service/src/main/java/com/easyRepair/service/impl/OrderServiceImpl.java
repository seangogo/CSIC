package com.easyRepair.service.impl;

import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import com.easyRepair.dao.OrderDao;
import com.easyRepair.entityUtils.ApiBeanUtils;
import com.easyRepair.entityUtils.SessionUtils;
import com.easyRepair.pojo.OrderListMudule;
import com.easyRepair.pojo.UserSessionModul;
import com.easyRepair.service.*;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.service.commons.DynamicSpecifications;
import com.easyRepair.service.commons.SpecificationUtil;
import com.easyRepair.tabEntity.*;
import common.geo.DistUtils;
import common.utils.ConfigUtil;
import common.utils.JsonUtil;
import common.utils.jpush.SendPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 Created by sean on 2016/11/24. */
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private TradeInfoService tradeInfoService;
    @Autowired
    private RequestDateService requestDateService;
    @Autowired
    private AreasSearchService areasSearchService;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private ServiceTypeService serviceTypeService;
    @Autowired
    private UserCouponService userCouponService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private UserPositionService userPositionService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderImageService orderImageService;
    @Autowired
    private UserRepairInfoService userRepairInfoService;
    @PersistenceContext
    private EntityManager em;
    
    
    
    
    public List<Order> findAll() {
        return orderDao.findAll();
    }
    
    public Page<Order> find(int i, int i1) {
        return null;
    }
    
    public Page<Order> find(int i) {
        return null;
    }
    
    public Order getById(long l) {
        return orderDao.findOne(l);
    }
    
    public Order deleteById(long l) {
        return null;
    }
    
    @Transactional
    public Order create(Order order) {
        orderDao.saveAndFlush(order);
        return order;
    }
    
    @Transactional
    public Order update(Order order) {
        return orderDao.saveAndFlush(order);
    }
    
    public void deleteAll(long[] longs) {
        
    }
    
    public Order findByIdAndUser_Id(Long orderId, Long userId) {
        return orderDao.findByIdAndUser_Id(orderId, userId);
    }
    
    public Order findByIdAndRepair_Id(Long id, Long userId) {
        return orderDao.findByIdAndRepair_Id(id, userId);
    }
    
    public Page<Order> findByRepairId(Long repairId, Pageable pageable) {
        return orderDao.findByRepair_Id(repairId, pageable);
    }
    
    public Page<Order> findByUserId(Long userId, Pageable pageable) {
        return orderDao.findByUser_Id(userId, pageable);
    }
    
    public List<Order> findByIdIn(List<Long> ids) {
        return orderDao.findByIdIn(ids);
    }
    
    /*定时器任务修改过期未支付订单状态*/
    @Transactional
    public void updateExpiredOrderState() {
        String sql = "UPDATE t_order o SET o.`status` = 6 " +
                "WHERE o.order_info_id IN (" +
                "SELECT oi.id FROM t_order_info oi " +
                "WHERE oi.create_time < date_add(now(), INTERVAL - 30 MINUTE) " +
                "AND oi.pay_mode IS NULL) AND o.`status` =2";
        int i = em.createNativeQuery(sql).executeUpdate();
        System.out.println(i + "条过期订单数据被处理");
    }
    
    public String findOrderNum() {
        Query query = em.createNativeQuery("{call orderNum(?,?)}");
        query.setParameter(1, "TM");
        query.setParameter(2, 8);
        return query.getResultList().get(0).toString();
    }
    
    public int findAllCommentsCount(Long reapirId) {
        return orderDao.findAllCommentsCount(reapirId);
    }
    
    //工程师确认匹配请求
    @Transactional
    public boolean confirmOrderRequest(Long orderId) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        //验证请求
        if(requestDateService.machingOrder_IdAndUser_Id(orderId,userSessionModul.getId())== null) {
            return false;
        }
        return updateConfirmOrdersStutas(orderId,userSessionModul.getId());
    }
    /*用户确认抢单请求*/
    @Transactional
    public boolean confirmRepair(Long orderId, Long repairId) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        Order order=findByIdAndUser_Id(orderId,userSessionModul.getId());
        if(order == null||order.getStatus()!=1) {
            return false;
        }
        /*验证请求*/
        if(requestDateService.requestOrder_IdAndUser_Id(orderId,repairId)== null) {
            return false;
        }
        return updateConfirmOrdersStutas(orderId,repairId);
    }
    @Transactional
    public boolean updateConfirmOrdersStutas(Long orderId, Long userId) {
        /*删除订单请求记录*/
        requestDateService.deleteByOrderId(orderId);
        /*删除推荐记录*/
        areasSearchService.deleteByObjectIdAndOr(orderId,true);
        /*更改状态*/
        User user = new User();
        user.setId(userId);
        Order order=getById(orderId);
        order.setRepair(user);
        order.setStatus(2);
        return update(order).getStatus().equals(2);
    }
    
    public Order findByOrderNum(String out_trade_no) {
        return orderDao.findByOrderNum(out_trade_no);
    }
    // 获取到返回的所有参数 先判断是否交易成功trade_status 再做签名校验
    // 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
    // 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
    // 3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
    // 4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。在上述验证通过后商户必须根据支付宝不同类型的业务通知，
    // 正确的进行不同的业务处理，并且过滤重复的通知结果数据。在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
    @Transactional
    public boolean aliPayNotify(HttpServletRequest request) {
        if ("TRADE_SUCCESS".equals(request.getParameter("trade_status"))) {
            Enumeration<?> pNames = request.getParameterNames();
            Map<String, String> param = new HashMap<String, String>();
            try {
                while (pNames.hasMoreElements()) {
                    String pName = (String) pNames.nextElement();
                    if(!pName.equals("sign_type")){
                        param.put(pName, request.getParameter(pName));
                    }
                }
                boolean signVerified = AlipaySignature.rsaCheckV2(param, ConfigUtil.getString("alipay.publicKey"),
                        AlipayConstants.CHARSET_UTF8,"RSA2"); // 验签
                Notify notify=JsonUtil.json2Obj(JsonUtil.obj2Json(param), Notify.class);
                notify.setSign_type(request.getParameter("sign_type"));
                notify.setSign(request.getParameter("sign"));
                if (signVerified) {
                    //验签成功后
                    Order order=findByOrderNum(notify.getOut_trade_no());
                    if(order==null||!order.getStatus().equals(2)||
                            !notify.getBuyer_pay_amount().equals(0.01)||
                            !notify.getSeller_id().equals(ConfigUtil.getString("alipay.sellerId"))||
                            !notify.getSeller_email().equals(ConfigUtil.getString("alipay.sellerEmail"))||
                            !notify.getApp_id().equals(ConfigUtil.getString("alipay.appId"))){
                        notify.setSuccess(false);
                        notifyService.create(notify);
                        return false;
                    }
                    notify.setSuccess(true);
                    order.setStatus(3);
                    order.getOrderInfo().setPayMode("1");
                    update(order);
                    /*记录交易记录*/
                    TradeInfo tradeInfo=new TradeInfo(order.getUser(),2,notify.getBuyer_pay_amount(),(double) order.getUser().getUserInfo().getMoney(),3,new Date(),notify.getTrade_no(),"交易支付订单金额");
                    tradeInfoService.create(tradeInfo);
                    SendPush.pushToUser(order.getRepair().getUserLoginInfo().getDeviceId(), "手机号为:" + order.getUser().getLoginName().substring(0, 2) + "****" + order.getUser().getLoginName().substring(7, 10)
                            + "的用户已经支付订单费用(维修费+上门费)**元,请您及时上门维修", "订单通知", "5", order.getUser().getId().toString());
                     /*更改订单状态*/
                     /*账户金额变动*/
                    // 按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
                    return true;
                } else {
                    notify.setSuccess(false);
                    notifyService.create(notify);
                    return false;
                    //验签失败则记录异常日志，并在response中返回failure.
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    @Transactional
    public Map<String,Object> putOrder(HttpServletRequest request,Order order) {
        Map<String,Object> map=new HashMap<String,Object>();
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        if(userSessionModul.getType().equals("1")) {
            map.put("result",1);
            return map;//工程师不可以发单
        }
        if(order.getServiceType()==null) {
            map.put("result",2);
            return map;//发单服务类型不可为空
        }
        if(order.getOrderInfo() != null){
            if(order.getOrderInfo().getScore() < 0 ||
                    order.getOrderInfo().getScore() > userSessionModul.getScore()){
                map.put("result",3);
                return map;//积分有误
            }
        }else {
            map.put("result",4);
            return map;//参数有误
        }
        User user=new User();
        user.setId(userSessionModul.getId());
        //  相关金额
        ServiceType serviceType = serviceTypeService.getById(order.getServiceType().getId());
        Coupon coupon = null;
        Double disCount = 0.00;
        if(order.getCoupon() != null && order.getCoupon().getId() != null && order.getCoupon().getId() >= 0) {
            //   优惠券判断用户是否可以使用该优惠卷
            List<UserCoupon> userCouponList = userCouponService.findByUser_IdAndCoupon_Id(userSessionModul.getId(), order.getCoupon().getId());
            if(userCouponList == null || userCouponList.size() != 1 || ! userCouponList.get(0).getYesUse()) {
                map.put("result",5);
                return map;
            }
            coupon = couponService.getById(order.getCoupon().getId());
            //  验证优惠券使用条件,金额限制由优惠券领取限制
            if(coupon.getServiceType()==null||coupon.getServiceType().getId() .equals(order.getServiceType().getId())) {
                map.put("result",2);
                return map;
            }
            disCount = coupon.getDeduction();//订单的抵扣金额
        }
        //上门地址 联系方式联系人
        OrderInfo orderInfoSave = new OrderInfo(order.getOrderInfo().getAddress(),
                order.getOrderInfo().getMobile(), order.getOrderInfo().getConsignee(),
                order.getOrderInfo().getScore(), new Date());
        
        //调用返回部分列的存储过程
        Order orderSave = new Order(serviceType.getPrice()- disCount-order.getOrderInfo().getScore() * 0.01,findOrderNum(), user, serviceType.getPrice(),
                serviceType.getExpenses(), disCount, order.getOrderInfo().getScore() * 0.01,
                orderInfoSave, coupon, serviceType, order.getRemark(), 1, order.getAppointmentTime(), order.getOrderImages());//TODO 积分系数先写死
        //  预约时间接单人默认空
        //  订单应付金额查询单时的服务的对应价格和上门费
        //  积分积分抵扣金额
        // orderService.create(orderSave);
        AreasSearch areasSearch=order.getAreasSearch();
        areasSearch.setOrderOrRepair(false);
        if(request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> photos = multipartRequest.getFiles("photos");
           /*上传图片，并保存*/
            List<Map<String, Object>> imagesList = ImageUtilsService.getImages(request, photos, false);
            Set<OrderImage> orderImages = new HashSet<OrderImage>();
            for(Map<String, Object> imgMap : imagesList) {
                //orderImageService.create(new OrderImage(map.get("imgURL").toString(),new Date(),orderSave));
                orderImages.add(new OrderImage(imgMap.get("imgURL").toString(),new Date()));
            }
            orderSave.setOrderImages(orderImages);
        }
        create(orderSave);
        /*插入发布订单经纬度*/
        areasSearch.setObjectId(orderSave.getId());
        userPositionService.create(new UserPosition(user, order.getAreasSearch().getLng(),
                order.getAreasSearch().getLng(), new Date(), 2));
        if(order.getRepair() != null && order.getRepair().getId() != null) {
            User repairUser = userService.getById(order.getRepair().getId());
            RequestDate requestDate=new RequestDate(order,repairUser,true,new Date());
            requestDateService.create(requestDate);
            SendPush.pushToUser(repairUser.getUserLoginInfo().getDeviceId(), "手机号为:" + user.getLoginName().substring(0, 2) + "****" + user.getLoginName().substring(7, 10)
                    + "的用户向您发送了一个订单请求", "订单通知", "5", repairUser.getId().toString());
            map.put("result",7);
            return map;
        }
        areasSearchService.create(order.getAreasSearch());
        map.put("result",0);
        map.put("data",orderSave);
        return map;
    }
    
    public Object indexOrder() {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        //查出距离最近的5条数据
        List<AreasSearch> areasSearchList=areasSearchService.indexTop5Order(userSessionModul.getLastLng(),userSessionModul.getLastLat());
        List<OrderListMudule> orderListMuduleList=new ArrayList<OrderListMudule>();
       List<Long> orderIds=new ArrayList<Long>();
        for(AreasSearch areasSearch:areasSearchList){
            orderIds.add(areasSearch.getObjectId());
        }
        List<Order> orderList=findByIdIn(orderIds);
        for(int i=0;i<areasSearchList.size();i++){
            Double lng1 = areasSearchList.get(i).getLng();
            Double lat1 = areasSearchList.get(i).getLat();
            String dist = DistUtils.DubToStr(userSessionModul.getLastLng(), userSessionModul.getLastLat(), lng1, lat1);
            OrderListMudule orderListMudule= ApiBeanUtils.getOrderListModel(orderList.get(i));
            orderListMudule.setDist(dist);
            orderListMuduleList.add(orderListMudule);
        }
        //找到用户对应的服务类型
        return orderListMuduleList;
    }

    public Boolean findIsWorkByUserId(Long id) {
        return orderDao.findIsWorkByUserId(id).size()>0;
    }

    public Boolean getByIdCheckUser(Long orderId,Long userId) {
        Order order=orderDao.findOne(orderId);
        return order.getStatus().equals(1)||orderDao.findOneChechUser(orderId,userId)!=null;
    }
    //判断用户是否用户查看工程师定位权限
    public boolean checkRepairLngAndLat(Long repairId, Long userId) {
        return orderDao.checkRepairLngAndLat(repairId,userId).size()>0;
    }

    public Long countByRepair_Id(Long repairId) {
        return orderDao.countByRepair_Id(repairId);
    }

    public List<Order> findByServiceTypeIds(long[] arrayId) {
        List<Long> longs = new ArrayList<Long>();
        for (long l : arrayId) {
            longs.add(l);
        }

        return orderDao.findByServicetypeIds(longs);
    }
    //后台分页
    public Page<Order> page(Map<String, Object> searchParams, PageRequest pageRequest) {
        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);
        filters.put("type", new SpecificationUtil("user.type", SpecificationUtil.Operator.LIKE,""));
        filters.put("payMode", new SpecificationUtil("orderInfo.payMode", SpecificationUtil.Operator.LIKE,""));
        Specification<Order> spec = DynamicSpecifications.bySearchFilter(filters.values(), Order.class);
        //Specifications.where(spec).and()
        Page<Order> page= orderDao.findAll(spec, pageRequest);
        return page;
    }


    /*我的订单*/
    public Map<String, Object> mineOrderPage(final Long id,final String type,final Integer status,PageRequest pageRequest) {
        Page<Order> page = orderDao.findAll(new Specification<Order>() {
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;
                if (type.equals("0")) {
                    Predicate predicate = cb.equal(root.get("user").get("id").as(Long.class), id);
                    predicateList.add(predicate);
                }
                if(type.equals("1")){
                    Predicate predicate = cb.equal(root.get("repair").get("id").as(Long.class), id);
                    predicateList.add(predicate);
                }
                if(!status.equals(0)){
                    if(status.equals(2)||status.equals(3)){
                        Predicate predicate = cb.equal(root.get("status").as(Integer.class), 2);
                        Predicate predicate1 = cb.equal(root.get("status").as(Integer.class), 3);
                        Predicate predicateBase = cb.or(predicate,predicate1);
                        predicateList.add(predicateBase);
                    }else {
                        Predicate predicate = cb.equal(root.get("status").as(Integer.class), status);
                        predicateList.add(predicate);
                    }
                }
                    Predicate predicate = cb.equal(root.get("cut").as(boolean.class),false);
                    predicateList.add(predicate);
    
                if (predicateList.size() > 0) {
                    result = cb.and(predicateList.toArray(new Predicate[]{}));
                }
                if (result != null) {
                    query.where(result);
                }
                return query.getRestriction();
            }
        
        }, pageRequest);
        List<OrderListMudule> orderListMuduleList = new ArrayList<OrderListMudule>();
        for(Order order : page.getContent()) {
            orderListMuduleList.add(ApiBeanUtils.getOrderListModel(order));
        }
        Map<String, Object> map = DataTableFactory.fittingPojo(page,orderListMuduleList);
        return map;
    }
    
    @Transactional
    public boolean userPay(Long orderId,Double price,String payModel) {
        Order order=getById(orderId);
        /*更改状态码*/
        if(order.getStatus().equals(2)) {
            order.setStatus(3);
        }
        order.setRealpay(price);
        order.getOrderInfo().setPayMode(payModel);
        /*保存交易记录*/
        //调用返回部分列的存储过程
        Query query = em.createNativeQuery("{call liushui(?)}");
        query.setParameter(1, order.getUser().getId());
        TradeInfo tradeInfo=new TradeInfo(order.getUser(),1,price,(double)order.getUser().getUserInfo().getMoney(),3,new Date(),query.getSingleResult().toString(),"确认支付");
        tradeInfo=tradeInfoService.create(tradeInfo);
        return tradeInfo != null && create(order) != null;
    }
    /*取消订单*/
    @Transactional
    public Order cancel(Order order,String cancelRemark) {
        order.getOrderInfo().setCancelRemark(cancelRemark);
        order.setStatus(6);
        requestDateService.deleteByOrderId(order.getId());
        return orderDao.saveAndFlush(order);
    }
    /*支付业务*/
    @Transactional
    public Order payPrice(Order order) {
        /*更改订单状态*/
        order.setStatus(3);
        /*账户金额变动*/
        UserInfo userInfo=order.getUser().getUserInfo();
        PayLog payLog=userInfoService.changeMoney(userInfo,false,order,1);
        order.setRealpay(payLog.getCosts().doubleValue());
        order.getOrderInfo().setPayMode("2");
        return orderDao.saveAndFlush(order);
    }
  
    public Order findByIdAndUser_IdOrRepairId(Long orderId, Long userId) {
        return orderDao.findByIdAndUser_IdOrRepairId(orderId,userId);
    }
    @Transactional
    public Order cutOrder(Order order) {
        order.setCut(true);
        return update(order);
    }
    @Transactional
    public PayLog confirmPay(Order order, boolean b) {
        order.setStatus(4);
        //todo 获得积分
        update(order);
        return userInfoService.changeMoney(order.getRepair().getUserInfo(),true,order,b?2:3);
    }
    @Transactional
    public PayLog refund(Order order) {
        //todo 退还优惠券 和积分
        order.setStatus(6);
        update(order);
        return userInfoService.changeMoney(order.getUser().getUserInfo(),true,order,4);
    }
}
