package com.dh.commont.jpush;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.NettyHttpClient;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jpush.api.push.model.notification.*;
import com.google.gson.*;
import io.netty.handler.codec.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.SMS;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;

public class SendPush {
    protected static final Logger LOG = LoggerFactory.getLogger(SendPush.class);

    // demo App defined in resources/jpush-api.conf
    private static final String appKey ="a72e63dfbcc16782a941d5a3";
    private static final String masterSecret = "ec948e33a15981610432d9da";

    protected static final String APP_KEY ="a72e63dfbcc16782a941d5a3";
    protected static final String MASTER_SECRET = "ec948e33a15981610432d9da";

    public static final String TITLE = "Test from API example";
    public static final String ALERT = "Test from API Example - alert";
    public static final String MSG_CONTENT = "Test from API Example - msgContent";
    public static final String REGISTRATION_ID = "1a1018970aaa523fa44";
    public static final String TAG = "tag_api";

//    public static void main(String[] args) {
////        SendPushWithCustomConfig();
////        SendIosAlert();
////		SendPush();
////        SendPush_fromJSON();
////        SendPushWithCallback();
////        SendPushesWithMultiCallback();
//        pushToUser("98E5181A_84EE_48B2_8BCE_5EFA9CB7C0DB", "用户 测试用户 发布了一个 设备维修 的订单，请注意查看！", "推送测试", "1", "1878");
////        pushToUser("861022006023719", "用户 测试用户 发布了一个 设备维修 的订单，请注意查看！", "推送测试", "1", "1878");
//    }

    // 使用 NettyHttpClient 异步接口发送请求
    public static void SendPushWithCallback() {
        ClientConfig clientConfig = ClientConfig.getInstance();
        String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
        final NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET),
                null, clientConfig);
        try {
            URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
            PushPayload payload = buildPushObject_all_alias_alert();
            client.sendRequest(HttpMethod.POST, payload.toString(), uri, new NettyHttpClient.BaseCallback() {
                @Override
                public void onSucceed(ResponseWrapper responseWrapper) {
                    LOG.info("Got result: " + responseWrapper.responseContent);
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void SendPushesWithMultiCallback() {
        NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET),
                null, ClientConfig.getInstance());
        String host = (String) ClientConfig.getInstance().get(ClientConfig.PUSH_HOST_NAME);
        URI uri = null;
        try {
            uri = new URI(host + (String) ClientConfig.getInstance().get(ClientConfig.PUSH_PATH));
            PushPayload payload = PushPayload.alertAll("test");
            System.out.println(payload.toString());
            NettyHttpClient.BaseCallback callback1 = new NettyHttpClient.BaseCallback() {
                @Override
                public void onSucceed(ResponseWrapper responseWrapper) {
                    System.out.println("callback1 Got result: " + responseWrapper.responseContent);
                }
            };
            NettyHttpClient.BaseCallback callback2 = new NettyHttpClient.BaseCallback() {
                @Override
                public void onSucceed(ResponseWrapper responseWrapper) {
                    System.out.println("callback2 Got result: " + responseWrapper.responseContent);
                }
            };
            MyThread thread1 = new MyThread(client, callback1);
            MyThread thread2 = new MyThread(client, callback2);
            thread1.start();
            thread2.start();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static class MyThread extends Thread {

        private NettyHttpClient client;
        private NettyHttpClient.BaseCallback callback;

        public MyThread(NettyHttpClient client, NettyHttpClient.BaseCallback callback) {
            this.client = client;
            this.callback = callback;
        }

        @Override
        public void run() {
            // super.run();
            System.out.println("running send push");
            try {
                String host = (String) ClientConfig.getInstance().get(ClientConfig.PUSH_HOST_NAME);
                URI uri = new URI(host + (String) ClientConfig.getInstance().get(ClientConfig.PUSH_PATH));
                PushPayload payload = PushPayload.alertAll("test");
                System.out.println(payload.toString());
                client.sendRequest(HttpMethod.POST, payload.toString(), uri, callback);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }


    public static void SendPush() {
        // HttpProxy proxy = new HttpProxy("localhost", 3128);
        // Can use this https proxy: https://github.com/Exa-Networks/exaproxy
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_all_alias_alert();
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
            Thread.sleep(5000);
            //如果使用 NettyHttpClient，需要手动调用 close 方法退出进程
//            jpushClient.close();
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            LOG.error("Sendno: " + payload.getSendno());

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            LOG.error("Sendno: " + payload.getSendno());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //use String to build PushPayload instance
    public static void SendPush_fromJSON() {
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PlatformNotification.class, new InterfaceAdapter<PlatformNotification>())
                .create();
        // Since the type of DeviceType is enum, thus the value should be uppercase, same with the AudienceType.
        String payloadString = "{\"platform\":{\"all\":false,\"deviceTypes\":[\"IOS\"]},\"audience\":{\"all\":false,\"targets\":[{\"audienceType\":\"TAG_AND\",\"values\":[\"tag1\",\"tag_all\"]}]},\"notification\":{\"notifications\":[{\"soundDisabled\":false,\"badgeDisabled\":false,\"sound\":\"happy\",\"badge\":\"5\",\"contentAvailable\":false,\"alert\":\"Test from API Example - alert\",\"extras\":{\"from\":\"JPush\"},\"type\":\"cn.jpush.api.push.model.notification.IosNotification\"}]},\"message\":{\"msgContent\":\"Test from API Example - msgContent\"},\"options\":{\"sendno\":1429488213,\"overrideMsgId\":0,\"timeToLive\":-1,\"apnsProduction\":true,\"bigPushDuration\":0}}";
        PushPayload payload = gson.fromJson(payloadString, PushPayload.class);
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            LOG.error("Sendno: " + payload.getSendno());

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            LOG.error("Sendno: " + payload.getSendno());
        }
    }

    public static PushPayload buildPushObject_all_all_alert() {
        return PushPayload.alertAll(ALERT);
    }

    public static PushPayload buildPushObject_all_alias_alert() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
//                .setAudience(Audience.alias("alias1"))
//                .setAudience(Audience.alias("864895022026288")) //android 虚拟机
//                .setAudience(Audience.alias("861022006023719"))
                .setAudience(Audience.alias("98E5181A_84EE_48B2_8BCE_5EFA9CB7C0DB"))
                .setNotification(Notification.alert(ALERT))
                .build();
    }

    public static PushPayload buildPushObject_android_tag_alertWithTitle() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.android(ALERT, TITLE, null))
                .build();
    }

    public static PushPayload buildPushObject_android_and_ios() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.newBuilder()
                        .setAlert("alert content")
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle("Android Title").build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .addExtra("extra_key", "extra_value").build())
                        .build())
                .build();
    }

    public static PushPayload buildPushObject_android_and_ios_alias_alert(String alias, String content, String title,
                                                                          Map<String, String> extras) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .setAlert(content)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(title).addExtras(extras).build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .addExtras(extras).build()).build())
                .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .build();
    }

    public static PushPayload buildPushObject_android_and_ios_alias_alert(List<String> alias, String content, String title,
                                                                          Map<String, String> extras) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .setAlert(content)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(title).addExtras(extras).build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .addExtras(extras).build()).build())
                .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .build();
    }

    public static void buildPushObject_with_extra() {

        JsonObject jsonExtra = new JsonObject();
        jsonExtra.addProperty("extra1", 1);
        jsonExtra.addProperty("extra2", false);

        Map<String, String> extras = new HashMap<String, String>();
        extras.put("extra_1", "val1");
        extras.put("extra_2", "val2");

        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.newBuilder()
                        .setAlert("alert content")
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle("Android Title")
                                .addExtras(extras)
                                .addExtra("booleanExtra", false)
                                .addExtra("numberExtra", 1)
                                .addExtra("jsonExtra", jsonExtra)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .addExtra("extra_key", "extra_value").build())
                        .build())
                .build();

        System.out.println(payload.toJSON());
    }

    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag_and("tag1", "tag_all"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(ALERT)
                                .setBadge(5)
                                .setSound("happy")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                .setMessage(Message.content(MSG_CONTENT))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }

    public static PushPayload buildPushObject_android_newly_support() {
        JsonObject inbox = new JsonObject();
        inbox.add("line1", new JsonPrimitive("line1 string"));
        inbox.add("line2", new JsonPrimitive("line2 string"));
        inbox.add("contentTitle", new JsonPrimitive("title string"));
        inbox.add("summaryText", new JsonPrimitive("+3 more"));
        Notification notification = Notification.newBuilder()
                .addPlatformNotification(AndroidNotification.newBuilder()
                        .setAlert(ALERT)
                        .setBigPicPath("path to big picture")
                        .setBigText("long text")
                        .setBuilderId(1)
                        .setCategory("CATEGORY_SOCIAL")
                        .setInbox(inbox)
                        .setStyle(1)
                        .setTitle("Alert test")
                        .setPriority(1)
                        .build())
                .build();
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(REGISTRATION_ID))
                .setNotification(notification)
                .setOptions(Options.sendno())
                .build();
    }

    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(MSG_CONTENT)
                        .addExtra("from", "JPush")
                        .build())
                .build();
    }

    public static void SendPushWithCustomConfig() {
        ClientConfig config = ClientConfig.getInstance();
        // Setup the custom hostname
        config.setPushHostName("https://api.jpush.cn");

        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, config);

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_all_all_alert();

        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }

    public static void SendIosAlert() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);

        IosAlert alert = IosAlert.newBuilder()
                .setTitleAndBody("test alert", "subtitle", "test ios alert json")
                .setActionLocKey("PLAY")
                .build();
        try {
//            PushResult result = jpushClient.sendIosNotificationWithAlias(alert, new HashMap<String, String>(), "alias1");
            PushResult result = jpushClient.sendIosNotificationWithAlias(alert, new HashMap<String, String>(), "98E5181A_84EE_48B2_8BCE_5EFA9CB7C0DB");
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void SendWithSMS() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        try {
            SMS sms = SMS.content("Test SMS", 10);
            PushResult result = jpushClient.sendAndroidMessageWithAlias("Test SMS", "test sms", sms, "alias1");
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void pushToUser(String alias, String content, String title, String objType, String objId) {
        // HttpProxy proxy = new HttpProxy("localhost", 3128);
        // Can use this https proxy: https://github.com/Exa-Networks/exaproxy
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);

        Map<String, String> extras = new HashMap<String, String>();
        extras.put("objType", objType);
        extras.put("objId", objId);

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_android_and_ios_alias_alert(alias, content, title, extras);
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
            Thread.sleep(5000);
            //如果使用 NettyHttpClient，需要手动调用 close 方法退出进程
//            jpushClient.close();
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            LOG.error("Sendno: " + payload.getSendno());

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            LOG.error("Sendno: " + payload.getSendno());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void pushToUser(List<String> alias, String content, String title, String objType, String objId) {
        // HttpProxy proxy = new HttpProxy("localhost", 3128);
        // Can use this https proxy: https://github.com/Exa-Networks/exaproxy
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);

        Map<String, String> extras = new HashMap<String, String>();
        extras.put("objType", objType);
        extras.put("objId", objId);

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_android_and_ios_alias_alert(alias, content, title, extras);
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
            Thread.sleep(5000);
            //如果使用 NettyHttpClient，需要手动调用 close 方法退出进程
//            jpushClient.close();
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            LOG.error("Sendno: " + payload.getSendno());

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            LOG.error("Sendno: " + payload.getSendno());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

