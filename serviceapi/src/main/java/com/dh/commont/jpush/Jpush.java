package com.dh.commont.jpush;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.dh.commont.CommonButil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Jpush {
    protected static final Logger LOG = LoggerFactory.getLogger(Jpush.class);

    private JPushClient jpushClient = null;
    private static Map<String, String> extras = new HashMap<String, String>();

    public Jpush(String appKey, String masterSecret) {
        this.jpushClient = new JPushClient(masterSecret, appKey);
        extras.put("time", CommonButil.getNowTimeStr());
    }

    /**
     * 根据注册id推送给指定用户
     *
     * @param registrationId 注册id
     * @param content        内容
     * @param title          标题
     * @param objType        对象类型
     * @param objId          对象id
     * @return
     */
    public PushResult pushObject(String alias, String content, String title, String objType, String objId) {
        extras.put("objType", objType);
        extras.put("objId", objId);
        PushPayload payload = buildPushObject_alert_alias(alias, content, title, extras);
        return sendPush(payload);
    }

    /**
     * 根据注册id数组推送给指定用户
     *
     * @param alias 注册id
     * @param content        内容
     * @param title          标题
     * @param objType        对象类型
     * @param objId          对象id
     * @return
     */
    public PushResult pushObject(List<String> alias, String content, String title, String objType, String objId) {
        extras.put("objType", objType);
        extras.put("objId", objId);
        PushPayload payload = buildPushObject_alert_alias(alias, content, title, extras);
        return sendPush(payload);
    }

    /**
     * 推送给全部用户 指定对象类型
     *
     * @param content 内容
     * @param title   标题
     * @param objType 对象类型
     * @param objId   对象id
     * @return
     */
    public PushResult pushObject(String content, String objType, String objId) {
        extras.put("objType", objType);
        extras.put("objId", objId);
        PushPayload payload = buildPushObject_all_alert_extra(content, extras);
        return sendPush(payload);
    }

    /**
     * 推送全部用户
     *
     * @param content 内容
     * @return
     */
    public PushResult pushObject(String content) {
        PushPayload payload = buildPushObject_all_all_alert(content);
        return sendPush(payload);
    }

    public PushResult sendPush(PushPayload payload) {

        try {
            PushResult result = jpushClient.sendPush(payload);

            LOG.info("Got result - " + result);
            return result;
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            return null;
        }
    }

    /**
     * 推送普通消息给全部用户
     *
     * @return
     */
    public static PushPayload buildPushObject_all_all_alert(String content) {
        return PushPayload.alertAll(content);
    }

    /**
     * 推送消息给全部用户 指定消息消息类型
     *
     * @return
     */
    public static PushPayload buildPushObject_all_alert_extra(String content, Map<String, String> extras) {
        return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.all())
                .setNotification(Notification.newBuilder().setAlert(content)
                        .addPlatformNotification(AndroidNotification.newBuilder().addExtras(extras).build())
                        .addPlatformNotification(IosNotification.newBuilder().addExtras(extras).build()).build())
                .build();
    }

    /**
     * 对单个用户通过 registrationId字符串 进行推送
     *
     * @param registrationId
     * @return
     */
    public static PushPayload buildPushObject_alert_registrationId(String registrationId, String content, String title,
                                                                   Map<String, String> extras) {
        return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.registrationId(registrationId))
                .setNotification(Notification.newBuilder().setAlert(content)
                        .addPlatformNotification(
                                AndroidNotification.newBuilder().setTitle(title).addExtras(extras).build())
                        .addPlatformNotification(IosNotification.newBuilder().addExtras(extras).build()).build())
                .setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
    }

    /**
     * 对单个用户通过 别名 字符串 进行推送
     *
     * @param registrationId
     * @return
     */
    public static PushPayload buildPushObject_alert_alias(String alias, String content, String title,
                                                          Map<String, String> extras) {
        return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder().setAlert(content)
                        .addPlatformNotification(
                                AndroidNotification.newBuilder().setTitle(title).addExtras(extras).build())
                        .addPlatformNotification(IosNotification.newBuilder().addExtras(extras).build()).build())
                .setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
    }

    /**
     * 对多个用户通过 别名 数组 进行推送
     *
     * @param registrationId[]
     * @return
     */
    public static PushPayload buildPushObject_alert_alias(List<String> alias, String content, String title,
                                                          Map<String, String> extras) {
        return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder().setAlert(content)
                        .addPlatformNotification(
                                AndroidNotification.newBuilder().setTitle(title).addExtras(extras).build())
                        .addPlatformNotification(IosNotification.newBuilder().addExtras(extras).build()).build())
                .setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
    }

}
