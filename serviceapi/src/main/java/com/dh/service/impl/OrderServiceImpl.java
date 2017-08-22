package com.dh.service.impl;

import com.dh.commont.CommonButil;
import com.dh.configure.Consts;
import com.dh.entity.*;
import com.dh.repository.OrderCostDao;
import com.dh.repository.RepairOrderDao;
import com.dh.repository.RepairOrderLogDao;
import com.dh.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.dh.configure.Consts.*;

/**
 * Created by Coolkid on 2016/9/12.
 */
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    /**
     * 获取订单编号的 标示
     */
    private static String CODE_PARAM = "SERVICEORDER";

    /**
     * 定义订单编号的流水号长度 不够则前面填充0
     */
    private static int CODE_LENGTH = 4;
    @Autowired
    RepairOrderDao repairOrderDao;
    @Autowired
    private CodeNumService codeNumService;
    @Autowired
    private RepairOrderLogDao repairOrderLogDao;
    @Autowired
    private OrderCostDao orderCostDao;
    @Autowired
    private WordBookService wordBookService;
    @Autowired
    private OrderEngineerService orderEngineerService;
    @Autowired
    private RepairOrderLogService repairOrderLogService;
    @Autowired
    private UploadFileOrdersService uploadFileOrdersService;
    @Autowired
    private UserService userService;
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<RepairOrder> findAll() {
        return null;
    }

    @Override
    public Page<RepairOrder> find(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Page<RepairOrder> find(int pageNum) {
        return null;
    }

    @Override
    public RepairOrder getById(int id) {
        return repairOrderDao.findOne(id);
    }

    @Override
    public RepairOrder deleteById(int id) {
        return null;
    }

    @Override
    public RepairOrder create(RepairOrder repairOrder) {
        return null;
    }

    @Override
    @Transactional
    public RepairOrder update(RepairOrder repairOrder) {
        return repairOrderDao.save(repairOrder);
    }

    @Override
    public void deleteAll(int[] ids) {

    }

   /* @Override
    public Page<RepairOrder> getPageByLike(final Integer userId, final String repairLike, int pageNum, int pageSize) {
        Page page = repairOrderDao.findOrderByLike(repairLike, new PageRequest(pageNum - 1, pageSize, new Sort(Sort.Direction.ASC, "orderState")
                .and(new Sort(Sort.Direction.DESC, "hasComplain"))
                .and(new Sort(Sort.Direction.DESC, "createTime"))));
        return page;
    }*/

    @Override
    public Page<RepairOrder> orderPage(int pageNumber, int pageSize, final String orderState,
                                       final User user, final Integer repairId, final String repairLike) {
        Page<RepairOrder> page = repairOrderDao.findAll(new Specification<RepairOrder>() {
            @Override
            public Predicate toPredicate(Root<RepairOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;
                if (user.getUserType()==0) {
                    Predicate predicate = cb.equal(root.get("user").get("id").as(Integer.class), user.getId());
                    predicateList.add(predicate);
                } else if (user.getUserType()==1) {
                    Predicate predicate = cb.equal(root.get("repair").get("id").as(Integer.class), user.getId());
                    Predicate predicate1 = cb.equal(root.get("orderState").as(String.class), 0);
                    predicateList.add(cb.or(predicate, predicate1));

                }
                /*查询订单列表根据不同角色查询(如果是第一个查询订单列表的分页)*/
                if (StringUtils.isEmpty(repairId) && StringUtils.isEmpty(repairLike) && user != null) {
                    /*根据不同的用户类型给不同的订单列表*/
                    /*订单状态*/
                    if (!StringUtils.isEmpty(orderState)){
                        Predicate predicate = cb.equal(root.get("orderState").as(String.class), orderState);
                        predicateList.add(predicate);
                    }
                }
                /*第二个接口的情况*/
                if (!StringUtils.isEmpty(repairId) && StringUtils.isEmpty(repairLike)) {
                    Predicate predicate = cb.equal(root.get("repair").get("id").as(Integer.class), repairId);
                    predicateList.add(predicate);
                }
                /*第三个接口的情况*/
                if (!StringUtils.isEmpty(repairLike)&&StringUtils.isEmpty(repairId)) {
                    Join<RepairOrder, User> userJoin = root.join(root.getModel().getSingularAttribute("repair", User.class), JoinType.LEFT);
                    Predicate predicate = cb.like(root.get("user").get("userName").as(String.class), "%" + repairLike + "%");
                    Predicate predicate1 = cb.like(userJoin.get("userName").as(String.class), "%" + repairLike + "%");
                    Predicate predicate2 = cb.like(root.get("orderNum").as(String.class), "%" + repairLike + "%");
                   // Predicate predicate3=cb.and(predicate, predicate1);
                    predicate = cb.or(predicate, predicate1, predicate2);
                    predicateList.add(predicate);
                }
                if (predicateList.size() > 0) {
                    result = cb.and(predicateList.toArray(new Predicate[]{}));
                }
                if (result != null) {
                    query.where(result);
                }
                return query.getRestriction();
            }

        }, new PageRequest(pageNumber - 1, pageSize, new Sort(Sort.Direction.ASC, "orderState")
                .and(new Sort(Sort.Direction.DESC, "hasComplain"))
                .and(new Sort(Sort.Direction.DESC, "createTime"))));
        return page;
    }

    @Override
    @Transactional
    public RepairOrder saveOrder(String orderCost, Integer repairType, String contactUser, String contactPhone,
                                 String contactAddress, String orderDesc, String orderImgs, String orderImgsThumb,
                                 String contactLocation, Integer id, Integer qty) {
        // 根据标示获取编号
        int num = codeNumService.createCodeNum(CODE_PARAM);

        // 获取当前年月日 + 填充0后的流水号
        String numStr = new SimpleDateFormat("yyyyMM").format(new Date())
                + String.format("%0" + CODE_LENGTH + "d", num);

        RepairOrder order = new RepairOrder();
        order.setRepairType(repairType.toString());
        order.setContactAddress(contactAddress);
        order.setContactPhone(contactPhone);
        order.setContactUser(contactUser);
        order.setOrderDesc(orderDesc);
        order.setContactLocation(contactLocation);
        if (!CommonButil.isEmpty(orderImgs)) {
            order.setOrderImgs(orderImgs);
        }
        if (!CommonButil.isEmpty(orderImgsThumb)) {
            order.setOrderImgsThumb(orderImgsThumb);
        }
        User user = new User();
        user.setId(id);
        order.setUser(user);
        order.setQty(qty);
        Date time = CommonButil.getNowTime();
        order.setCreateTime(time);
        order.setUpdateTime(time);
        order.setHasAgain(0);
        order.setHasComplain(0);
        order.setHasComment(0);
        order.setOrderState("0");
        order.setOrderNum(numStr);
        order.setIsRead(0);
        order.setOrderSource(0);// 订单来源
        RepairOrder o = repairOrderDao.save(order);

        RepairOrderLog rol = new RepairOrderLog();
        User user1 = new User();
        user1.setId(id);
        rol.setCreater(user1);
        rol.setCreateTime(CommonButil.getNowTime());
        rol.setMsgType(0);
        rol.setRepairOrder(o);
        repairOrderLogDao.save(rol);

        //添加订单费用
        if (!CommonButil.isEmpty(orderCost)) {
            try {
                //TODO 这里用循环插入订单费用  效率不高，可优化
                JSONArray orderCostArr = new JSONArray(orderCost);
                for (int i = 0; i < orderCostArr.length(); i++) {
                    JSONObject oj = (JSONObject) orderCostArr.get(i);
                    OrderCost oc = new OrderCost();
                    OrderCostType oct = new OrderCostType();
                    oct.setId(Integer.valueOf(oj.getString("type")));
                    oc.setCostType(oct);
                    oc.setCost(Float.parseFloat(oj.getString("money")));
                    RepairOrder repairOrder = new RepairOrder();
                    repairOrder.setId(o.getId());
                    oc.setRepairOrder(repairOrder);
                    oc.setCreateTime(CommonButil.getNowTime());
                    orderCostDao.save(oc);
                }
            } catch (Exception e) {
                return null;
            }
        }
        return o;
    }

    @Override
    public List<String> getAllRepairDeviceId() {
        return repairOrderDao.getAllRepairDeviceId();
    }

    /**
     * 根据id查询订单详情
     *
     * @param orderId
     * @param userId
     * @return
     */
    @Override
    public RepairOrder getShowOrderDetail(Integer orderId, Integer userId) {
        RepairOrder repairOrder = getById(orderId);
        if (null == repairOrder) {
            return null;
        }
        repairOrder.setRepairType(wordBookService.findByIndex(Integer.parseInt(repairOrder.getRepairType()), Consts.REPAIR_TYPE));
        String orderImgsThumb = repairOrder.getOrderImgsThumb();
        // 从数据库获取图片路径是没有根目录的，再次返回给前台页面时 加上根目录
        if (!CommonButil.isEmpty(repairOrder.getOrderImgsThumb())) {
            String[] arr = orderImgsThumb.split(",");
            String newOrderImgsThumb = "";
            for (String imgUrl : arr) {
                if (CommonButil.isEmpty(imgUrl)) {
                    continue;
                }
                newOrderImgsThumb += ((CommonButil.isEmpty(newOrderImgsThumb) ? "" : ",") + (IMG_URL + imgUrl));
            }
            repairOrder.setOrderImgsThumb(CommonButil.isEmpty(newOrderImgsThumb) ? null : newOrderImgsThumb);
        }
        // 当前订单的接单人为当前用户时，则修改该订单为“已读”
        repairOrder.setUploadFileOrdersList(uploadFileOrdersService.findByRepairOrder(repairOrder));
        //获取订单费用
        List<OrderCost> ocList = orderCostDao.getOrderCostByOid(orderId);
        for (OrderCost oc : ocList) {
            if (!CommonButil.isEmpty(oc) && !CommonButil.isEmpty(oc.getCostType())) {
                oc.setRepairOrder(null);
                //TODO 外键关联实体类 如下：OrderCost.getCostType() 循环的情况下 会多次调用sql语句查询。建议去掉外键
            }
        }
        repairOrder.setOrderCostList(ocList);
        //订单费用集合
        List<OrderEngineer> orderEngineerList = orderEngineerService.findByOrderId(orderId);
        if (orderEngineerList.size() > 0) {
            for (OrderEngineer orderEngineer : orderEngineerList) {
                orderEngineer.setRepairOrder(null);
                User user = new User();
                user.setId(orderEngineer.getUser().getId());
                user.setUserName(orderEngineer.getUser().getUserName());
                orderEngineer.setUser(user);
            }
            repairOrder.setOrderEngineerList(orderEngineerList);
        } else {
            repairOrder.setOrderEngineerList(new ArrayList<OrderEngineer>());
        }
        //订单相关人集合
        return repairOrder;
    }

    @Override
    public List<RepairOrderLog> getOrderStateByOrderId(Integer orderId) {
        return repairOrderLogService.getOrderStateByOrderId(orderId);
    }

    @Override
    @Transactional
    public void updateAppointmentTime(RepairOrder order, String appointmentTimeStr, Integer id) {
        RepairOrderLog rol = new RepairOrderLog();
        rol.setCreater(order.getRepair());
        rol.setCreateTime(CommonButil.getNowTime());
        // 消息状态：0订单发布成功 1已接单 2订单已完成 3评价已完成 4订单已取消 5投诉 6预约时间更改 7再次上门 8更换工程师
        rol.setMsgType(6);
        rol.setRepairOrder(order);
        rol.setMsgContent("时间更改为：" + appointmentTimeStr);
        User user = new User();
        user.setId(id);
        rol.setCreater(user);
        rol.setRepair(order.getRepair());
        rol.setCreateTime(CommonButil.getNowTime());
        repairOrderLogDao.save(rol);
        repairOrderDao.save(order);

    }

    @Override
    public List<User> findAllRepairList(Boolean isContentRepair, RepairOrder order) {
        String sql = "select u.* ,"
                + "(select count(t1.id) from t_repair_order t1 where t1.repair_id=u.id) as total_order_count,"
                + "(select count(t2.id) from t_repair_order t2 where t2.repair_id=u.id and t2.order_state=1) as current_order_count"
                + " from t_user u where u.user_type = 1 or u.user_type =2";
        Query query = em.createNativeQuery(sql, User.class);
        Query queryTo = em.createNativeQuery(sql);
        List<User> users = query.getResultList();
        List<Object[]> objects = queryTo.getResultList();
        List<User> usersTo = new ArrayList<User>();
        List<WordBook> wordBooks = wordBookService.findByType(Consts.REPAIR_TYPE);
        if (users.size() == objects.size()) {
            for (int i = 0; i < users.size(); i++) {
                User user = new User();
                user.setId(users.get(i).getId());
                user.setLoginName(users.get(i).getLoginName());
                user.setUserName(users.get(i).getUserName());
                user.setUserIco(CommonButil.isEmpty(users.get(i).getUserIco()) ? null : Consts.IMG_URL + users.get(i).getUserIco());
                user.setLastLocation(users.get(i).getLastLocation());
                user.setLastLocationTime(users.get(i).getLastLocationTime());
                user.setRepairType(users.get(i).getRepairType());
                user.setIsLogin(CommonButil.isEmpty(users.get(i).getIsLogin()) ? 0 : users.get(i).getIsLogin());
                user.setTypeStr("工程师");
                wordBooksFor:
                for (WordBook wordBook : wordBooks) {
                    if (users.get(i).getRepairType() == wordBook.getWordIndex()) {
                        user.setTypeStr(wordBook.getWordValue() + "工程师");
                        break wordBooksFor;
                    }
                }
                user.setTotalOrderCount(Long.parseLong(objects.get(i)[25].toString()));
                user.setCurrentOrderCount(Long.parseLong(objects.get(i)[26].toString()));
                if (isContentRepair||order==null||order.getRepair()==null||order.getRepair().getId()!=users.get(i).getId()){
                    usersTo.add(user);
                }
            }
        }
        em.close();
        return usersTo;
    }

    /**
     * 查找相关人工程师列表 (排除了当前接单人)
     */
    @Override
    public List<User> findRepairCCList(RepairOrder order) {
        List<Object[]> users=orderEngineerService.findEngineerCCList(order.getId());
        List<WordBook> wordBooks = wordBookService.findByType(Consts.REPAIR_TYPE);
        List<User> usersTO=new ArrayList<User>();
        for (int i=0;i<users.size();i++){
            User user=(User) users.get(i)[0];
            user.setTotalOrderCount((Long) users.get(i)[1]);
            user.setCurrentOrderCount((Long) users.get(i)[2]);
            user.setTypeStr("工程师");
            wordBooksFor:
            for (WordBook wordBook : wordBooks) {
                if (user.getRepairType() == wordBook.getWordIndex()) {
                    user.setTypeStr(wordBook.getWordValue() + "工程师");
                    break wordBooksFor;
                }
            }
            usersTO.add(user);
        }
        em.close();
        return usersTO;
    }

    @Override
    @Transactional
    public RepairOrder updateOrderState(Integer orderState, RepairOrder order, User repair, String orderExplain) {
        order.setOrderState(orderState.toString());
        if (5 != orderState) {
            order.setRepair(repair);
        }
        // 保存订单状态记录
        RepairOrderLog rol = new RepairOrderLog();
        rol.setCreater(repair);
        rol.setRepair(order.getRepair());
        if (orderState == 1) {// 接单
            // 消息状态：0订单发布成功 1已接单 2订单已完成 3评价已完成 4订单已取消 5投诉 6预约时间更改 7再次上门 8更换工程师
            rol.setMsgType(1);
            rol.setMsgContent("接单人：" + repair.getUserName());
            // 接单时设置订单为已读
            order.setIsRead(1);
        } else if (orderState == 2) {// 完成订单
            if (!CommonButil.isEmpty(orderExplain)) {
                order.setOrderExplain(orderExplain);
            }
            order.setCompletionTime(CommonButil.getNowTime());
            rol.setMsgType(2);
            rol.setMsgContent("接单人：" + repair.getUserName());
        } else if (orderState == 5) {
            if (!CommonButil.isEmpty(orderExplain)) {
                order.setOrderExplain(orderExplain);
            }
            order.setCompletionTime(CommonButil.getNowTime());
            rol.setMsgType(10);
            rol.setMsgContent("付款人：" + repair.getUserName());
        }
        rol.setCreateTime(CommonButil.getNowTime());
        rol.setRepairOrder(order);
        repairOrderLogService.create(rol);
        return update(order);
    }

    @Override
    @Transactional
    public RepairOrder assignRepairCC(RepairOrder order, String repairIds, Integer userId) {
        // 设置相关人
        String[] repairIdArray = repairIds.split(",");
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(repairIdArray));
        String[] repairIdArr = new String[arrayList.size()];
        arrayList.toArray(repairIdArr);
        orderEngineerService.deleteByOrderId(order.getId());
        for (String s : repairIdArr) {
            OrderEngineer oe = new OrderEngineer();
            oe.setRepairOrder(order);
            User user = new User();
            user.setId(Integer.parseInt(s));
            oe.setUser(user);
            orderEngineerService.create(oe);
        }
//		order.setCcIds(repairIds);
        // 保存订单状态记录
        RepairOrderLog rol = new RepairOrderLog();
        User user = new User();
        user.setId(userId);
        rol.setCreater(user);
        rol.setRepair(order.getRepair());
        // 消息状态：0订单发布成功 1已接单 2订单已完成 3评价已完成 4订单已取消 5投诉 6预约时间更改 7再次上门 8更换工程师 9分配相关人
        rol.setMsgType(9);
        //\查询相关人姓名
        List<Object[]> users = em.createNativeQuery("select u.id,u.user_name from t_user u  where u.id in(" + repairIds + ")").getResultList();
        String ccNames = "";
        for (Object[] obj : users) {
            ccNames += (CommonButil.isEmpty(ccNames) ? "" : ",") + obj[1];
        }
        rol.setMsgContent("相关人：" + ccNames);
        rol.setCreateTime(CommonButil.getNowTime());
        rol.setRepairOrder(order);
        repairOrderLogService.create(rol);
        order.setUpdateTime(CommonButil.getNowTime());
        order = update(order);
        order.setRepairName(ccNames);
        return order;
    }


    /**
     * 分配工程师
     *
     * @param order
     * @param repair
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public RepairOrder assignRepair(RepairOrder order, User repair, Integer userId) {
        // 设置订单为已接单
        order.setOrderState("1");
        // 设置接单人
        order.setRepair(repair);
        // 分配工程师后 设置订单为“未读”
        order.setIsRead(0);
        // 保存订单状态记录
        RepairOrderLog rol = new RepairOrderLog();
        User user = new User();
        user.setId(userId);
        rol.setCreater(user);
        rol.setRepair(order.getRepair());
        // 消息状态：0订单发布成功 1已接单 2订单已完成 3评价已完成 4订单已取消 5投诉 6预约时间更改 7再次上门 8更换工程师
        rol.setMsgType(8);
        rol.setCreateTime(CommonButil.getNowTime());
        rol.setMsgContent("接单人：" + repair.getUserName());
        rol.setRepairOrder(order);
        repairOrderLogService.create(rol);
        //若相关人不为空，则更改接单人时，则去掉包含在相关人中的接单人
        if(order.getRepair()!=null){
            orderEngineerService.deleteByorderIdAnduserId(order.getId(), order.getRepair().getId());
        }
        order = update(order);
        //der.setRepairName(ccNames);
        return order;
    }

    @Override
    public User findRepairDetail(Integer repairId) {
        User user = userService.getById(repairId);
        if (null == user) {
            return null;
        }
        User userTo = new User();
        userTo.setId(user.getId());
        userTo.setUserIco(CommonButil.isEmpty(user.getUserIco()) ? null : Consts.IMG_URL + user.getUserIco());
        userTo.setUserName(user.getUserName());
        userTo.setLoginName(user.getLoginName());
        String typeStr = wordBookService.findByIndex(user.getRepairType(), Consts.REPAIR_TYPE);
        typeStr = CommonButil.isEmpty(typeStr) ? "工程师" : typeStr + "工程师";
        userTo.setTypeStr(typeStr);
        // 获取工程师的当前接单数和总接单数
        List<Object[]> objects = em.createNativeQuery("SELECT count(*) as totalCount,("
                + "SELECT count(*) FROM t_repair_order ro2 WHERE ro.repair_id = ro2.repair_id AND ro2.order_state = 1 ) as currentCount "
                + " FROM t_repair_order ro WHERE  ro.repair_id =" + repairId).getResultList();
        for (Object[] obj : objects) {
            userTo.setTotalOrderCount(Long.parseLong(obj[0].toString()));
            userTo.setCurrentOrderCount(Long.parseLong(obj[1].toString()));
        }
        userTo.setIsLogin(CommonButil.isEmpty(user.getIsLogin()) ? 0 : user.getIsLogin());
        userTo.setLastLocation(user.getLastLocation());
        userTo.setLastLocationTime(user.getLastLocationTime());
        return userTo;
    }

    /**
     * 填写事由
     *
     * @param type        1取消订单 2投诉 3再次上门
     * @param contentType
     * @param msgContent
     * @param order
     * @param user
     * @return
     */
    @Override
    @Transactional
    public RepairOrderLog saveReason(Integer type, Integer contentType, String msgContent, RepairOrder order, User user) {
        RepairOrderLog rol = new RepairOrderLog();
        rol.setRepairOrder(order);
        rol.setRepair(order.getRepair());
        rol.setCreater(user);
        rol.setContentType(contentType);
        String str = "";
        // msgType消息状态：0订单发布成功 1已接单 2订单已完成 3评价已完成 4订单已取消 5投诉 6预约时间更改 7再次上门
        // 8更换工程师
        // orderState订单状态：0待处理 1已接单 2已完成 3已取消 4已评价
        // contentType事由状态：0其他
        switch (type) {
            case 1:// 取消订单
                rol.setMsgType(4);
                order.setOrderState("3");
                order.setCompletionTime(CommonButil.getNowTime());
                if (0 != contentType) {
                    str = wordBookService.findByIndex(contentType, CANCEL_ORDER);
                }
                break;
            case 2:// 投诉
                rol.setMsgType(5);
                order.setHasComplain(1);
                // 当用户投诉时，修改订单为“未读”
                order.setIsRead(0);
                if (0 != contentType) {
                    str = wordBookService.findByIndex(contentType, COMPLAIN_TYPE);
                }
                break;
            case 3:// 再次上门
                rol.setMsgType(7);
                order.setHasAgain(1);
                if (0 != contentType) {
                    str = wordBookService.findByIndex(contentType, REASON_TYPE);
                }
                break;
        }
        if (CommonButil.isEmpty(rol.getMsgType())) {
            return null;
        }
        rol.setMsgContent("".equals(str) ? msgContent : (str + "。" + msgContent));
        order.setUpdateTime(CommonButil.getNowTime());
        create(order);
        rol.setCreateTime(CommonButil.getNowTime());
        return repairOrderLogService.create(rol);
    }

    @Override
    public List<String> findManagerDeviceId() {
        return repairOrderDao.findManagerDeviceId();
    }

    @Override
    @Transactional
    public RepairOrderLog saveComment(RepairOrder order, String commentStar, String commentContent, Integer id) {
        order.setHasComment(1);
        order.setCommentContent(commentContent);
        order.setCommentStar(commentStar);
        order.setOrderState("4");
        order.setUpdateTime(CommonButil.getNowTime());
        create(order);

        RepairOrderLog rol = new RepairOrderLog();
        rol.setRepairOrder(order);
        rol.setRepair(order.getRepair());
        User user = new User();
        user.setId(id);
        rol.setCreater(user);
        rol.setMsgType(3);
        rol.setCommentStar(commentStar);
        rol.setMsgContent(commentContent);
        rol.setCreateTime(CommonButil.getNowTime());
        return repairOrderLogService.create(rol);
    }

    @Override
    @Transactional
    public int updateIsRead(Integer orderId) {
       /* return repairOrderDao.updateIsReadById(orderId);*/
        RepairOrder repairOrder=getById(orderId);
        repairOrder.setIsRead(1);
        repairOrder=repairOrderDao.save(repairOrder);
         return  repairOrder==null?0:1;
    }

}
