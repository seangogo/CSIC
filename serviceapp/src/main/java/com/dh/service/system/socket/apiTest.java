package com.dh.service.system.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.sunwayland.util.likong.ef21ws.Realtimedata;
import com.sunwayland.util.likong.ef21ws.Realtimedataarray;
import com.sunwayland.util.likong.ef21ws.Tagparnamearray;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sean on 2017/7/26.
 */
public  class apiTest implements Runnable {
     SocketIOClient client;

    public apiTest(SocketIOClient client) {
        this.client = client;
    }

    public apiTest() {
    }

    @Override
    public void run() {
        for (;;) {
            Map<String, String> valueMap = new ConcurrentHashMap<>();
            try (Ef21WsApi api = new Ef21WsApi("58.242.153.87", 8090, "58.242.153.87", 2006)) {
                if (api.connect(500)) {
                    System.out.println("connect success");
                    Map<String, Object> nameMap = api.getAllPointNameAndValue();
                    if (nameMap != null && nameMap.size() > 0) {
                        //获取所有点位名称
                        Tagparnamearray names = (Tagparnamearray) nameMap.get("names");
                        //获取所有点位值
                        Realtimedataarray par = (Realtimedataarray) nameMap.get("par");
                        int i = 0;
                        for (String name : names.getItem()) {
                            if (par != null && par.getItem() != null && par.getItem().size() > 0) {
                                Realtimedata vv = par.getItem().get(i);
                                Double v = vv.getDbVal();
                                valueMap.put(name, v.toString());
                            } else {
                                valueMap.put(name, "");
                            }
                            i++;
                        }

                        // model.addAttribute("msg", "OK");

                    } else {
                        System.out.println("查询结果为空。");
                        // model.addAttribute("msg", "查询结果为空。");
                    }
                } else {
                    System.out.println("调用泵站数据查询接口失败，请联系系统管理员！");
                    //  model.addAttribute("msg", "调用泵站数据查询接口失败，请联系系统管理员！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                //   model.addAttribute("msg", "调用泵站数据查询接口失败，请联系系统管理员！");
                System.out.println("调用泵站数据查询接口失败，请联系系统管理员！");
            }
            client.sendEvent("advert_info", valueMap);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
          /*  Collection<SocketIOClient> clients = server.getAllClients();
            for (SocketIOClient client : clients) {
                client.sendEvent("advert_info", valueMap);
            }*/
        }
    }
}
