package com.dh.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

/**
 * spring加载完毕后执行
 * @author ZYL_PC
 *
 */
@Component("BeanDefineConfigue")
public class BeanDefineConfigue  implements ApplicationListener<ContextRefreshedEvent>{

    @Autowired
    private SocketIoService service;

    //当前服务器的ip
    private String serverIp = "";
    //当前服务器设备id
    private String deviceId = "";
    //执行时间，时间单位为毫秒,读者可自行设定，不得小于等于0
    private static Long cacheTime = Long.MAX_VALUE;
    //延迟时间，时间单位为毫秒,读者可自行设定，不得小于等于0
    private static Integer delay = 3000;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // TODO Auto-generated method stub
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                //启动socket监听

                try{
                    if(service.getServer() == null){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    service.startServer();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }catch(Exception e){
                }
            }
        }, delay,cacheTime);// 这里设定将延时每天固定执行

    }
}