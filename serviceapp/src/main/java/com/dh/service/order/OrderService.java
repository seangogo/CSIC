package com.dh.service.order;

import com.dh.common.CommonButil;
import com.dh.common.UploadFileUtils;
import com.dh.configure.Consts;
import com.dh.configure.annotation.SystemServiceLog;
import com.dh.dto.ShowRepair;
import com.dh.entity.*;
import com.dh.repository.CommonDao;
import com.dh.repository.RepairOrderDao;
import com.dh.repository.RepairOrderLogDao;
import com.dh.repository.UploadFileOrdersDao;
import com.dh.service.account.AccountService;
import com.dh.service.system.CodeNumService;
import com.dh.service.system.WordBookService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Transactional
public class OrderService {

    @Autowired
    private RepairOrderDao roDao;

    @Autowired
    private RepairOrderLogDao rolDao;

    @Autowired
    private OrderCostService orderCostService;

    @Autowired
    private CommonDao cDao;

    @Autowired
    private OrderEngineerService orderEngineerService;

    @Autowired
    private WordBookService wordService;

    @Autowired
    private AccountService userService;

    @Autowired
    private CodeNumService codeService;

    @Autowired
    private UploadFileOrdersDao uploadFileOrdersDao;

    @PersistenceContext
    private EntityManager em;// 注入entitymanager

    /**
     * 获取订单编号的 标示
     */
    private static String CODE_PARAM = "SERVICEORDER";

    /**
     * 定义订单编号的流水号长度 不够则前面填充0
     */
    private static int CODE_LENGTH = 4;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * 获取订单列表
     *
     * @param user
     * @param startDate
     * @param endDate
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @SystemServiceLog(description = "条件查询订单列表")
    public Page<RepairOrder> getOrderList(User user, String startDate, String endDate, Map<String, Object> searchParams, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildOrderListPageRequest(pageNumber, pageSize);
        Specification<RepairOrder> spe = buildOrderListSpecification(user, startDate, endDate, searchParams);
        Page<RepairOrder> page = roDao.findAll(spe, pageRequest);
        return page;
    }

    /**
     * 创建分页请求.
     */
    private PageRequest buildOrderListPageRequest(int pageNumber, int pageSize) {
        Sort sort = new Sort(Sort.Direction.ASC, "orderState").and(new Sort(Sort.Direction.DESC, "hasComplain"))
                .and(new Sort(Sort.Direction.DESC, "createTime"));
        return new PageRequest(pageNumber - 1, pageSize, sort);
    }

    /**
     * 设置查询订单列表的条件
     *
     * @param user
     * @param startDate
     * @param endDate
     * @param searchParams
     * @return
     */
    private Specification<RepairOrder> buildOrderListSpecification(User user, String startDate, String endDate, Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (!CommonButil.isEmpty(startDate)) {
            try {
                filters.put("startTime", new SearchFilter("createTime", Operator.GTE, sdf.parse(startDate + " 00:00:00")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (!CommonButil.isEmpty(endDate)) {
            try {
                filters.put("endTime", new SearchFilter("createTime", Operator.LTE, sdf.parse(endDate + " 23:59:59")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (0 == user.getUserType()) {
            filters.put("userId", new SearchFilter("user.id", Operator.EQ, user.getId()));
        }
        Specification<RepairOrder> spec = DynamicSpecifications.bySearchFilter(filters.values(), RepairOrder.class);
        return spec;
    }

    /**
     * 获取订单详情
     *
     * @param id
     * @return
     */
    @SystemServiceLog(description = "查询订单详情")
    public RepairOrder getOrderById(Long id) {
        RepairOrder order = roDao.findOne(id);
        return order;
    }

    /**
     * 获取订单状态列表
     *
     * @param orderId
     * @return
     */
    @SystemServiceLog(description = "查询订单状态")
    public List<RepairOrderLog> findByOrderIdOrderByCreateTimeDesc(Long orderId) {
        List<RepairOrderLog> rolList = rolDao.findByOrderIdOrderByCreateTimeDesc(orderId);
        for (RepairOrderLog rol : rolList) {
            rol.setMsgTypeStr(wordService.findByIndex(rol.getMsgType(), Consts.MSG_TYPE));
        }
        return rolList;
    }

    /**
     * 获取订单费用列表
     *
     * @param orderId
     * @return
     */
    @SystemServiceLog(description = "查询订单费用")
    public List<OrderCost> findByOrderIdOrderByIdAsc(Long orderId) {
        List<OrderCost> ocList = orderCostService.findByOrderIdOrderByIdAsc(orderId);

        return ocList;
    }

    /**
     * 查询订单相关人
     *
     * @param orderId
     * @return
     */
    @SystemServiceLog(description = "查询订单相关人")
    public List<String> getOrderEngineerByOId(Long orderId) {
        List<String> oeList = orderEngineerService.getOrderEngineerByOId(orderId);

        return oeList;
    }

    /**
     * 获取全部用户
     *
     * @return
     */
    @SystemServiceLog(description = "查询工程师列表")
    public List<User> getRepairList() {
        return userService.getRepairList();
    }

    @SystemServiceLog(description = "查询订单")
    public RepairOrder findOne(Long orderId) {
        return roDao.findOne(orderId);
    }

    /**
     * 查找相关人工程师列表 (排除了当前接单人)
     */
    public List<ShowRepair> findRepairCCList(Long orderId) {
        RepairOrder order = roDao.findOne(orderId);
        List<User> repairList = roDao.getRepairCCList(order.getRepairId());
        List<ShowRepair> list = new ArrayList<ShowRepair>();
        for (User user : repairList) {
            ShowRepair sr = new ShowRepair();
            sr.setRepairId(user.getId());
            sr.setRepairIco(CommonButil.isEmpty(user.getUserIco()) ? null : UploadFileUtils.getPathProperties("config", "img.url") + user.getUserIco());
            sr.setRepairName(user.getUserName());
            sr.setRepairPhone(user.getLoginName());
            sr.setRepairTypeStr(wordService.findByIndex(user.getRepairType(), Consts.REPAIR_TYPE) + "工程师");
            if (0 < user.getCurrentOrderCount()) {
                sr.setRepairStateStr("正在接单");
            } else {
                sr.setRepairStateStr("空闲");
            }
            sr.setCurrentOrderCount(user.getCurrentOrderCount());
            sr.setTotalOrderCount(user.getTotalOrderCount());
            sr.setRepairLocation(user.getLastLocation());
            sr.setIsLogin(CommonButil.isEmpty(user.getIsLogin()) ? "0" : user.getIsLogin().toString());
            sr.setLastLocationTime(CommonButil.isEmpty(user.getLastLocationTime()) ? null
                    : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getLastLocationTime()));
            list.add(sr);
        }
        return list;
    }

    /**
     * 查找相关人工程师列表 (不排除了当前接单人)
     */
    public List<ShowRepair> getOrderAllCCList() {
        List<User> repairList = roDao.getOrderAllCCList();
        List<ShowRepair> list = new ArrayList<ShowRepair>();
        for (User user : repairList) {
            ShowRepair sr = new ShowRepair();
            sr.setRepairId(user.getId());
            sr.setRepairIco(CommonButil.isEmpty(user.getUserIco()) ? null : UploadFileUtils.getPathProperties("config", "img.url") + user.getUserIco());
            sr.setRepairName(user.getUserName());
            sr.setRepairPhone(user.getLoginName());
            sr.setRepairTypeStr(wordService.findByIndex(user.getRepairType(), Consts.REPAIR_TYPE) + "工程师");
            if (0 < user.getCurrentOrderCount()) {
                sr.setRepairStateStr("正在接单:已有" + user.getCurrentOrderCount() + "单");
            } else {
                sr.setRepairStateStr("空闲");
            }
            sr.setCurrentOrderCount(user.getCurrentOrderCount());
            sr.setTotalOrderCount(user.getTotalOrderCount());
            sr.setRepairLocation(user.getLastLocation());
            sr.setIsLogin(CommonButil.isEmpty(user.getIsLogin()) ? "0" : user.getIsLogin().toString());
            sr.setLastLocationTime(CommonButil.isEmpty(user.getLastLocationTime()) ? null
                    : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getLastLocationTime()));
            list.add(sr);
        }
        return list;
    }

    /**
     * 查找当前工程师已经分配哪些先相关人
     */
    public List<String> findIdsByRepair(Long repairId) {
        return roDao.findIdsByRepair(repairId);
    }

    /**
     * 添加相关人员
     *
     * @param order
     * @param repairIds
     * @return
     */
    public RepairOrder assignRepairs(RepairOrder order, String repairIds, String repairIdsName) {
        // 设置订单为已接单
        order.setOrderState(1);
        String[] repairIdArray = repairIds.split(",");
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(repairIdArray));
        String[] repairIdArr = new String[arrayList.size()];
        arrayList.toArray(repairIdArr);
        for (String s : repairIdArr) {
            OrderEngineer oe = new OrderEngineer();
            oe.setOrderId(order.getId());
            oe.setUserId(Long.parseLong(s));
            orderEngineerService.save(oe);
        }
        // 设置相关人
//        order.setCcIds(repairIds);
        // 分配订单时，修改订单为“未读”
        order.setIsRead(0);
        // 保存订单状态记录
        RepairOrderLog rol = new RepairOrderLog();
//        rol.setCreater(user);
//        rol.setRepair(repair);
        // 消息状态：0订单发布成功 1已接单 2订单已完成 3评价已完成 4订单已取消 5投诉 6预约时间更改 7再次上门 8更换工程师 9更换相关人
        rol.setMsgType(9);
        rol.setMsgContent("相关人：" + repairIdsName);
        rol.setOrderId(order.getId());
        saveRol(rol);
        return saveOrder(order);
    }

    /**
     * 分配订单
     *
     * @param order
     * @param repair
     * @param user
     * @return
     */
    @SystemServiceLog(description = "分配订单")
    public RepairOrder assignRepair(RepairOrder order, User repair, User user) {
        // 设置订单为已接单
        order.setOrderState(1);
        // 设置接单人
        order.setRepair(repair);

        // 分配订单时，修改订单为“未读”
        order.setIsRead(0);

        // 保存订单状态记录
        RepairOrderLog rol = new RepairOrderLog();
        rol.setCreater(user);
        rol.setRepair(repair);
        // 消息状态：0订单发布成功 1已接单 2订单已完成 3评价已完成 4订单已取消 5投诉 6预约时间更改 7再次上门 8更换工程师
        rol.setMsgType(8);
        rol.setMsgContent("接单人：" + repair.getUserName());
        rol.setOrderId(order.getId());
        saveRol(rol);
        return saveOrder(order);
    }

    /**
     * 保存订单记录
     *
     * @param rol
     * @return
     */
    public RepairOrderLog saveRol(RepairOrderLog rol) {
        rol.setCreateTime(CommonButil.getNowTime());
        return rolDao.save(rol);
    }

    /**
     * 保存实体
     *
     * @param order
     * @return
     */
    public RepairOrder saveOrder(RepairOrder order) {
        order.setUpdateTime(CommonButil.getNowTime());
        return roDao.save(order);
    }

    /**
     * 保存订单
     *
     * @param repairType
     * @param contactUser
     * @param contactPhone
     * @param contactAddress
     * @param orderDesc
     * @param orderImgs
     * @param orderImgsThumb
     * @param user
     * @return
     */
    @SystemServiceLog(description = "保存订单")
    public RepairOrder saveOrder(Integer repairType, String contactUser, String contactPhone, String contactAddress, Integer qty,
                                 String orderDesc, String orderImgs, String orderImgsThumb, User user) {
        // 根据标示获取编号
        Long num = codeService.createCodeNum(CODE_PARAM);

        // 获取当前年月日 + 填充0后的流水号
        String numStr = new SimpleDateFormat("yyyyMM").format(new Date())
                + String.format("%0" + CODE_LENGTH + "d", num);

        RepairOrder order = new RepairOrder();
        order.setRepairType(repairType);
        order.setContactAddress(contactAddress);
        order.setContactPhone(contactPhone);
        order.setContactUser(contactUser);
        order.setOrderDesc(orderDesc);
        order.setQty(qty);
        if (!CommonButil.isEmpty(orderImgs)) {
            order.setOrderImgs(orderImgs);
        }
        if (!CommonButil.isEmpty(orderImgsThumb)) {
            order.setOrderImgsThumb(orderImgsThumb);
        }
        order.setUser(user);
        Date time = CommonButil.getNowTime();
        order.setCreateTime(time);
        order.setUpdateTime(time);
        order.setHasAgain(0);
        order.setHasComplain(0);
        order.setHasComment(0);
        order.setOrderState(0);
        order.setOrderNum(numStr);
        order.setIsRead(0);
        order.setOrderSource(1);// 订单来源
        RepairOrder o = saveOrder(order);

        RepairOrderLog rol = new RepairOrderLog();
        rol.setCreater(user);
        rol.setCreateTime(CommonButil.getNowTime());
        rol.setMsgType(0);
        rol.setOrderId(o.getId());
        rolDao.save(rol);
        return o;

    }

    /**
     * 修改订单
     *
     * @param order
     * @param repairType
     * @param contactUser
     * @param contactPhone
     * @param contactAddress
     * @param orderDesc
     * @param orderImgs
     * @param orderImgsThumb
     * @return
     */
    @SystemServiceLog(description = "修改订单")
    public RepairOrder updateOrder(RepairOrder order, Integer repairType, String contactUser, String contactPhone,
                                   String contactAddress, String orderDesc, String orderImgs, String orderImgsThumb) {
        order.setRepairType(repairType);
        order.setContactAddress(contactAddress);
        order.setContactPhone(contactPhone);
        order.setContactUser(contactUser);
        order.setOrderDesc(orderDesc);
        if (!CommonButil.isEmpty(orderImgs)) {
            order.setOrderImgs(orderImgs);
        }
        if (!CommonButil.isEmpty(orderImgsThumb)) {
            order.setOrderImgsThumb(orderImgsThumb);
        }
        return saveOrder(order);
    }

    /**
     * 接单
     *
     * @param order
     * @param user
     */
    @SystemServiceLog(description = "工程师接单")
    public void acceptOrder(RepairOrder order, User user) {
        // 设置订单为已接单
        order.setOrderState(1);
        // 设置接单人
        order.setRepair(user);

        // 保存订单状态记录
        RepairOrderLog rol = new RepairOrderLog();
        rol.setCreater(user);
        rol.setRepair(user);
        // 消息状态：0订单发布成功 1已接单 2订单已完成 3评价已完成 4订单已取消 5投诉 6预约时间更改 7再次上门 8更换工程师
        rol.setMsgType(1);
        rol.setMsgContent("接单人：" + user.getUserName());
        rol.setOrderId(order.getId());
        saveRol(rol);
        saveOrder(order);
    }

    /**
     * 完成订单
     *
     * @param order
     * @param user
     */
    @SystemServiceLog(description = "完成订单")
    public void completeOrder(RepairOrder order, User user, String orderExplain) {
        // 设置订单为已完成
        order.setOrderState(2);
        // 设置订单完成时间
        order.setCompletionTime(CommonButil.getNowTime());
        // 保存订单处理说明
        if (!CommonButil.isEmpty(orderExplain)) {
            order.setOrderExplain(orderExplain);
        }
        // 保存订单状态记录
        RepairOrderLog rol = new RepairOrderLog();
        rol.setCreater(user);
        rol.setRepair(user);
        // 消息状态：0订单发布成功 1已接单 2订单已完成 3评价已完成 4订单已取消 5投诉 6预约时间更改 7再次上门 8更换工程师
        rol.setMsgType(2);
        rol.setMsgContent("接单人：" + user.getUserName());
        rol.setOrderId(order.getId());
        saveRol(rol);
        saveOrder(order);
    }

    /**
     * 取消订单
     *
     * @param order
     * @param user
     */
    @SystemServiceLog(description = "取消订单")
    public void cancelOrder(RepairOrder order, User user) {
        order.setOrderState(3);
        order.setCompletionTime(CommonButil.getNowTime());
        saveOrder(order);
        RepairOrderLog rol = new RepairOrderLog();
        rol.setOrderId(order.getId());
        rol.setCreater(user);
        rol.setMsgType(4);
        saveRol(rol);
    }

    /**
     * 评价超时已完成的订单
     */
    @SystemServiceLog(description = "自动评价超时订单")
    public void commentTimeoutOrder() {
        List<RepairOrder> list = roDao.getTimeoutOrder();
        for (RepairOrder order : list) {
            order.setHasComment(1);
            order.setCommentStar(Consts.DEFAULT_STAR);
            order.setCommentContent(Consts.DEFAULT_COMMENT);
            order.setOrderState(4);
            saveOrder(order);

            // 记录订单状态列表
            RepairOrderLog rol = new RepairOrderLog();
            rol.setOrderId(order.getId());
            rol.setRepair(order.getRepair());
            // 默认评价是以
            User user = new User(1L);
            rol.setCreater(user);
            // msgType消息状态：0订单发布成功 1已接单 2订单已完成 3评价已完成 4订单已取消 5投诉 6预约时间更改 7再次上门
            rol.setMsgType(3);
            rol.setCommentStar(Consts.DEFAULT_STAR);
            rol.setMsgContent(Consts.DEFAULT_COMMENT);
            saveRol(rol);
        }
    }

    /**
     * 获取全部工程师订单统计
     *
     * @param orderTime
     * @param repairName
     * @return
     */
    @SuppressWarnings("unchecked")
    @SystemServiceLog(description = "查询工程师的订单统计")
    public List getRepairOrderCountList(List<String> orderTime, String repairName) {
        String sql = " SELECT ol.repair_id,u.user_Name AS repairName,"
                + "	SUM(CASE WHEN ol.msg_Type = 2 THEN 1 ELSE 0 END) AS orderCount,"
                + "	SUM(CASE WHEN ol.msg_Type = 3 THEN 1 ELSE 0 END) AS comment,"
                + "	SUM(CASE WHEN ol.msg_Type = 5 THEN 1 ELSE 0 END ) AS complaint,"
                + "	SUM(CASE WHEN ol.comment_Star >= 3 AND ol.comment_Star <=5 THEN 1 ELSE 0 END ) AS praise,"
                + "	SUM(CASE WHEN ol.comment_Star >= 1 AND ol.comment_Star <=2 THEN 1 ELSE 0 END ) AS bad"
                + " FROM t_repair_order_log ol left join t_user u on ol.repair_id = u.id where ol.repair_id IS NOT NULL";
        if (orderTime.size() == 2) {
            if (!CommonButil.isEmpty(orderTime.get(0)) && !CommonButil.isEmpty(orderTime.get(1))) {
                sql += " AND ol.create_Time >= '" + orderTime.get(0) + "' AND ol.create_Time <= '" + orderTime.get(1)
                        + "' ";
            } else if (!CommonButil.isEmpty(orderTime.get(0)) && CommonButil.isEmpty(orderTime.get(1))) {
                sql += " AND ol.create_Time >= '" + orderTime.get(0) + "' ";

            } else if (CommonButil.isEmpty(orderTime.get(0)) && !CommonButil.isEmpty(orderTime.get(1))) {
                sql += " AND ol.create_Time <= '" + orderTime.get(1) + "' ";
            }
        }
        if (!CommonButil.isEmpty(repairName)) {
            sql += " AND  u.user_name like :repairName ";
        }
        sql += " GROUP BY u.user_name HAVING SUM(CASE WHEN ol.msg_Type = 2 THEN 1 ELSE 0 END) > 0 ORDER BY SUM(CASE WHEN ol.msg_Type = 2 THEN 1 ELSE 0 END) DESC";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (!CommonButil.isEmpty(repairName)) {
            paramMap.put("repairName", "%" + repairName + "%");
        }

        return (List) cDao.queryListEntity(sql, paramMap, null);
    }

    /**
     * 查询工程师订单统计明细
     *
     * @param orderTime
     * @param repairId
     * @return
     */
    @SuppressWarnings("unchecked")
    @SystemServiceLog(description = "查询工程师的订单统计明细")
    public List<RepairOrderLog> getRepairOrderCountDetail(String orderTime, Long repairId) {
        // 通过em获取hibernate的session
        Session session = em.unwrap(Session.class);

        String sql = " SELECT   new RepairOrderLog(ol.createTime, ol.repair.userName AS repairName,ol.repair.loginName AS repairPhone,ol.repair.repairType, "
                + "		SUM(CASE WHEN ol.msgType = 2 THEN 1 ELSE 0 END) AS orderCount, "
                + "		SUM(CASE WHEN ol.msgType = 3 THEN 1 ELSE 0 END) AS comment, "
                + "		SUM(CASE WHEN ol.msgType = 5 THEN 1 ELSE 0 END ) AS complaint, "
                + "		SUM(CASE WHEN ol.commentStar >= 3 AND ol.commentStar <=5 THEN 1 ELSE 0 END ) AS praise, "
                + "		SUM(CASE WHEN ol.commentStar >= 1 AND ol.commentStar <=2 THEN 1 ELSE 0 END ) AS bad )  "
                + "FROM RepairOrderLog ol WHERE ol.repair = " + repairId;
        if (!CommonButil.isEmpty(orderTime)) {
            String[] orderTimeArr = orderTime.split(" - ");
            if (orderTimeArr.length == 2) {
                if (!CommonButil.isEmpty(orderTimeArr[0]) && !CommonButil.isEmpty(orderTimeArr[1])) {
                    sql += " AND ol.createTime >= '" + orderTimeArr[0] + "' AND ol.createTime <= '" + orderTimeArr[1]
                            + "' ";
                } else if (!CommonButil.isEmpty(orderTimeArr[0])) {
                    sql += " AND ol.createTime >= '" + orderTimeArr[0] + "' ";
                } else if (!CommonButil.isEmpty(orderTimeArr[1])) {
                    sql += " AND  ol.createTime <= '" + orderTimeArr[1] + "' ";
                }
            }
        }
        sql += " GROUP BY  DATE_FORMAT(ol.createTime, '%Y-%m-%d' )   ORDER BY ol.createTime desc";
        // 获取org.hibernate.Query
        return (List<RepairOrderLog>) session.createQuery(sql).list();
    }

    /**
     * 查询工程师维修订单台数
     *
     * @param orderTime
     * @param repairName
     * @return
     */
    @SuppressWarnings("unchecked")
    @SystemServiceLog(description = "查询工程师的维修订单台数")
    public List<RepairOrder> getRepairOrderQty(List<String> orderTime, String repairName) {
        String sql = " SELECT u.user_name, SUM(IFNULL(ro.qty,1)) AS orderCount FROM t_repair_order ro,t_user u "
                + " WHERE ro.repair_id = u.id and ro.repair_type = 0 and ro.order_state in(2,4,5) ";
        if (!CommonButil.isEmpty(repairName)) {
            sql += " and u.user_name like :repairName ";
        }
        if (orderTime.size() == 2) {
            if (!CommonButil.isEmpty(orderTime.get(0)) && !CommonButil.isEmpty(orderTime.get(1))) {
                sql += " AND ro.completion_time >= '" + orderTime.get(0) + "' AND ro.completion_time <= '" + orderTime.get(1)
                        + "' ";
            } else if (!CommonButil.isEmpty(orderTime.get(0)) && CommonButil.isEmpty(orderTime.get(1))) {
                sql += " AND ro.completion_time >= '" + orderTime.get(0) + "' ";

            } else if (CommonButil.isEmpty(orderTime.get(0)) && !CommonButil.isEmpty(orderTime.get(1))) {
                sql += " AND ro.completion_time <= '" + orderTime.get(1) + "' ";
            }
        }
        sql += " GROUP BY u.user_name ORDER BY SUM(IFNULL(ro.qty,1)) desc";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (!CommonButil.isEmpty(repairName)) {
            paramMap.put("repairName", "%" + repairName + "%");
        }

        return (List<RepairOrder>) cDao.queryListEntity(sql, paramMap, null);
    }

    /**
     * 查询工程师提成报表
     *
     * @param orderTime
     * @param repairName
     * @return
     */
    @SuppressWarnings("unchecked")
    @SystemServiceLog(description = "查询工程师提成报表")
    public List<RepairOrder> getEngineerCommission(List<String> orderTime, String repairName) {
        String sql = " select a.order_num,b.user_name,sum(case when a.id = 1 then a.cost else 0 end) as clf, "
                + " sum(case when a.id = 2 then a.cost else 0 end) as gpf, "
                + " sum(case when a.id = 3 then a.cost else 0 end) as byf, "
                + " sum(case when a.id = 4 then a.cost else 0 end) as azf, "
                + " sum(case when a.id = 5 then a.cost else 0 end) as wxf, "
                + " sum(case when a.id = 6 then a.cost else 0 end) as jtf, "
                + " sum(case when a.id = 7 then a.cost else 0 end) as shf,sum(a.cost) as hj from( "
                + " select roc.repair_id,ro.order_num,oct.id,cost from t_repair_order ro,t_repair_order_cost roc,t_order_cost_type oct "
                + " where ro.id = roc.order_id and roc.cost_type = oct.id ";
        if (orderTime.size() == 2) {
            if (!CommonButil.isEmpty(orderTime.get(0)) && !CommonButil.isEmpty(orderTime.get(1))) {
                sql += " AND ro.completion_time >= '" + orderTime.get(0) + "' AND ro.completion_time <= '" + orderTime.get(1)
                        + "' ";
            } else if (!CommonButil.isEmpty(orderTime.get(0)) && CommonButil.isEmpty(orderTime.get(1))) {
                sql += " AND ro.completion_time >= '" + orderTime.get(0) + "' ";

            } else if (CommonButil.isEmpty(orderTime.get(0)) && !CommonButil.isEmpty(orderTime.get(1))) {
                sql += " AND ro.completion_time <= '" + orderTime.get(1) + "' ";
            }
        }

        sql += " ) a left join t_user b on a.repair_id = b.id where 1 =1 ";
        if (!CommonButil.isEmpty(repairName)) {
            sql += " and b.user_name like :repairName ";
        }
        sql += " group by b.user_name,a.order_num order by a.order_num asc";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (!CommonButil.isEmpty(repairName)) {
            paramMap.put("repairName", "%" + repairName + "%");
        }

        return (List<RepairOrder>) cDao.queryListEntity(sql, paramMap, null);
    }

    // 根据相关人 获取相关人姓名
    public String getRepairCcName(String repairIds) {
        List<User> list = getCC(repairIds);
        String ccNames = "";
        for (User u : list) {
            ccNames += (CommonButil.isEmpty(ccNames) ? "" : ",") + u.getUserName();
        }
        return ccNames;
    }

    // 获取相关人
    public List<User> getCC(String repairIds) {
        List<Long> list = new ArrayList<Long>();
        if (CommonButil.isEmpty(repairIds)) {
            return new ArrayList<User>();
        }

        String[] repairIdArr = repairIds.split(",");
        for (String repairId : repairIdArr) {
            list.add(Long.parseLong(repairId));
        }

        return roDao.getRepairByRepairIds(list);
    }

    /**
     * 保存评论内容
     *
     * @param order
     * @param commentStar    评星
     * @param commentContent 内容
     */
    public RepairOrderLog saveComment(RepairOrder order, String commentStar, String commentContent, User user) {
        order.setHasComment(1);
        order.setCommentContent(commentContent);
        order.setCommentStar(commentStar);
        order.setOrderState(4);
        saveOrder(order);

        RepairOrderLog rol = new RepairOrderLog();
        rol.setOrderId(order.getId());
        rol.setRepairId(order.getRepairId());
        rol.setCreater(user);
        rol.setMsgType(3);
        rol.setCommentStar(commentStar);
        rol.setMsgContent(commentContent);
        return saveRol(rol);

    }

    //保存相关人
    public RepairOrder assignRepairCC(RepairOrder order, String repairIds, User userId) {
        String[] repairIdArray = repairIds.split(",");
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(repairIdArray));
        String[] repairIdArr = new String[arrayList.size()];
        arrayList.toArray(repairIdArr);
        for (String s : repairIdArr) {
            OrderEngineer oe = new OrderEngineer();
            oe.setOrderId(order.getId());
            oe.setUserId(Long.parseLong(s));
            orderEngineerService.save(oe);
        }
        // 设置相关人
//        order.setCcIds(repairIds);
        // 保存订单状态记录
        RepairOrderLog rol = new RepairOrderLog();
        rol.setCreater(userId);
        rol.setRepairId(order.getRepairId());
        // 消息状态：0订单发布成功 1已接单 2订单已完成 3评价已完成 4订单已取消 5投诉 6预约时间更改 7再次上门 8更换工程师 9分配相关人
        rol.setMsgType(9);
        rol.setMsgContent("相关人：" + getRepairCcName(repairIds));
        rol.setOrderId(order.getId());
        saveRol(rol);
        return saveOrder(order);
    }

    public List<RepairOrder> findAllByState() {
        return roDao.findAllByState();
    }

    public int saveCostEngineer(Long orderId, String orderTypes, String repairIds, String costs) {
        String[] orderTypesArr = orderTypes.split(",");
        String[] repairArr = repairIds.split(",");
        String[] costArr = costs.split(",");
        StringBuffer stringBuffer = new StringBuffer("INSERT INTO t_repair_order_cost(order_id,cost_type,repair_id,cost,create_time) VALUES");
        int costCount = 0;
        for (int i = 0; i < orderTypesArr.length; i++) {
            for (int j = 0; j < repairArr.length; j++) {
                if (i != 0 || j != 0) {
                    stringBuffer.append(",");
                }
                stringBuffer.append("(").append(orderId).append(",")
                        .append(orderTypesArr[i]).append(",")
                        .append(repairArr[j]).append(",").append(costArr[costCount]).append(",").append("NOW())");
                costCount++;
            }
        }
        String sql = stringBuffer.toString();
        return em.createNativeQuery(sql).executeUpdate();
    }

    /*保存上传文件*/
    public UploadFileOrders saveUploadFile(Long orderId, FileBo fileBo, String oldName) {
        UploadFileOrders uploadFileOrders = new UploadFileOrders();
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setId(orderId);
        uploadFileOrders.setRepairOrder(repairOrder);
        uploadFileOrders.setDocName(oldName);
        uploadFileOrders.setDocPath(fileBo.getPath());
        uploadFileOrders.setCreateTime(CommonButil.getNowTime());
        uploadFileOrders.setFileSize(UploadFileUtils.GetFileSize(fileBo.getFile()));
        return uploadFileOrdersDao.save(uploadFileOrders);
    }

    /*删除上传文件*/
    public String deleteFile(Long id) {
        String path = uploadFileOrdersDao.findByUpId(id);
        uploadFileOrdersDao.deleteById(id);
        if (path != "") {
            UploadFileUtils.deleteFiles(path);
        }
        return path;
    }

    /*保存订单后存入将订单文件ID修改过来*/
    public void updateByOrderd(Long orderId, String ids) {
        String[] fileIds = ids.split(",");
        if (!CommonButil.isEmpty(ids)) {
            for (int i = 0; i < fileIds.length; i++) {
                uploadFileOrdersDao.updateByOrderd(orderId, Long.parseLong(fileIds[i]));
            }
        }
    }

    /*通过订单ID查询所有上传的附件*/
    public List<UploadFileOrders> findFilesByOrderId(Long orderId) {
        return uploadFileOrdersDao.findFilesByOrderId(orderId);
    }

    /*修改工程师*/
    public void updateRepairByorderId(Long orderId, Long repairId, User user) {
        User repair = userService.getUser(repairId);
        // 保存订单状态记录
        RepairOrderLog rol = new RepairOrderLog();
        rol.setCreater(user);
        rol.setRepair(repair);
        // 消息状态：0订单发布成功 1已接单 2订单已完成 3评价已完成 4订单已取消 5投诉 6预约时间更改 7再次上门 8更换工程师
        rol.setMsgType(8);
        rol.setMsgContent("接单人：" + repair.getUserName());
        rol.setOrderId(orderId);
        saveRol(rol);
        roDao.findIdsByRepair(orderId, repair);
    }


    public List<String> getAllRepairDeviceId() {

        return roDao.getAllRepairDeviceId();
    }

    public int findNewOrderNum(User user) {
        long type = user.getRoleId();
        if (type == 2L || type == 5L) {
            //普通用户查询自己发布的所有订单
            return roDao.countByUserAndOrderStateLessThan(user, 2);
        } else if (type == 4L) {
            //工程师查询接的所有订单
            return roDao.countByRepairAndOrderStateLessThan(user, 2);
        } else {
            //经理查询所有新发布的订单
            return roDao.countByOrderStateLessThan(2);
        }
    }

    public List<Map<String, String>> findAllRepairCountBy4Month() {
        List<Object[]> allRepairCountByMonth = roDao.findAllRepairCountBy4Month();
        List list = new ArrayList();
        for (Object[] ob : allRepairCountByMonth) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", ob[0].toString());
            map.put("count", ob[1].toString());
            map.put("count1", ob[2].toString());
            map.put("count2", ob[3].toString());
            map.put("count3", ob[4].toString());
            list.add(map);
        }
        return list;
    }

    public Map<String, String> findWeekByType() {
        List<Object[]> objects = roDao.findWeekByType();
        Map<String, String> map = new HashMap();
        for (Object[] obj : objects) {
            map.put(obj[0].toString(), obj[1].toString()
            );
        }
        return map;
    }
}
