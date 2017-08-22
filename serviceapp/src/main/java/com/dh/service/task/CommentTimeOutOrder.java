package com.dh.service.task;

import com.dh.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.io.Serializable;


/**
 * Created by chengong on 2016/8/4.
 * 每日凌晨3点
 * 对超过7天未评价的用户执行自动评价更新
 */

@Component
public class CommentTimeOutOrder implements Serializable{

    @Autowired
    private OrderService orderService;

    @Scheduled(cron="0 0 3 * * ?") //每天凌晨3点执行
//    @Scheduled(cron="0/5 * * * * ?") //每5秒执行一次
    public void commentTimeOutOrder(){
        orderService.commentTimeoutOrder();
    }
}