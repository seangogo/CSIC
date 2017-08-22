package common.utils.jpush;

import cn.jpush.api.push.PushResult;
import org.springframework.util.StringUtils;

import java.util.List;

public class SendPush {

    /**
     * 推送给全部用户
     *
     * @param content
     * @return
     */
    public static Boolean pushAll(String content) {
        Jpush push = new Jpush("a72e63dfbcc16782a941d5a3", "ec948e33a15981610432d9da");
        PushResult result = push.pushObject(content);
        return !StringUtils.isEmpty(result);
    }

    /**
     * 推送给全部用户，指定消息类型
     *
     * @param content
     * @param objType
     * @param objId
     * @return
     */
    public static Boolean pushAll(String content, String objType, String objId) {
        Jpush push = new Jpush("a72e63dfbcc16782a941d5a3", "ec948e33a15981610432d9da");
        PushResult result = push.pushObject(content, objType, objId);
        return !StringUtils.isEmpty(result);
    }

    /**
     * 根据别名数组推送
     *
     * @param alias
     * @param content
     * @param title   标题只有安卓有效，不传默认为项目名称
     * @param objType
     * @param objId
     * @return
     */
    public static Boolean pushToUser(List<String> alias, String content, String title, String objType, String objId) {
        Jpush push = new Jpush("a72e63dfbcc16782a941d5a3", "ec948e33a15981610432d9da");
       /* PushResult result = push.pushObject(alias, content, title, objType, objId);*/
        String result = null;
        return !StringUtils.isEmpty(result);
    }

    /**
     * 根据单个别名 推送
     *
     * @param alias
     * @param content
     * @param title
     * @param objType
     * @param objId
     * @return
     */
    public static Boolean pushToUser(String alias, String content, String title, String objType, String objId) {
        /*Jpush push = new Jpush("a72e63dfbcc16782a941d5a3", "ec948e33a15981610432d9da");
        PushResult result = push.pushObject(alias, content, title, objType, objId);*/
        String result = null;
        return !StringUtils.isEmpty(result);
    }

//	public static void main(String[] args) {
//		String[] arr = new String[]{"160a3797c8076621fc6"};
//		pushToUser(arr, "2222推22送内容内容内容", "", "2", "20");
//	}
}
