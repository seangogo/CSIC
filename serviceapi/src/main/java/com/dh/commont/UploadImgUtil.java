package com.dh.commont;

import com.dh.web.api.BaseApiController;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Coolkid on 2016/9/20.
 */
public class UploadImgUtil extends BaseApiController {
    /**
     * 定义图片保存目录
     */
    private static String folder = "order/";
    /**
     * 定义图片压缩后的宽度 0则按高等比压缩
     */
    private static String targetWidth = "330";
    /**
     * 定义图片压缩后的高度 0则按宽等比压缩
     */
    private static String targetHeight = "0";

    private String orderImgs = "";
    private String orderImgsThumb = "";

    /**
     * 请求上传图片
     *
     * @param imgStrs
     * @param userFolder
     * @return
     */
    public Map<String, Object> uploadImg(String imgStrs, String userFolder) {
        // 设置上传头像参数
        Map<String, String> paramMap = new HashMap<String, String>();
        try {
            // url转码
            paramMap.put("imgStrs", URLEncoder.encode(imgStrs, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return setReturnValue("参数转码发生错误", RETURN_CODE_FAILED, null, null, "4000");
        }
        // 添加文件路径、设置图片压缩宽度、高度
        paramMap.put("folder", userFolder);
        paramMap.put("targetWidth", targetWidth);
        paramMap.put("targetHeight", targetHeight);

        // 请求url
        String requestUrl = APP_BASE_URL + "/api/uploadImgsThumb";
        String resultJson = HttpRequestButil.sendPost(requestUrl, paramMap);
        if (CommonButil.isEmpty(resultJson)) {
            return setReturnValue("上传图片失败", RETURN_CODE_FAILED);
        }

        JSONObject result = new JSONObject(resultJson);
        // 获取resultJson 为实体对象
        if (result.getInt("c") == 0) {
            return setReturnValue(result.getString("m"), RETURN_CODE_FAILED);
        }
        // 获取 图片路径
        JSONArray imgUrlArr = result.getJSONArray("o");
        if (CommonButil.isEmpty(imgUrlArr)) {
            return setReturnValue("保存图片失败", RETURN_CODE_FAILED);
        }
        orderImgs = "";
        orderImgsThumb = "";
        for (int i = 0; i < imgUrlArr.length(); i++) {
            String imgUrl = imgUrlArr.getString(i);
            String[] arr = imgUrl.split(",");
            if (arr.length == 2) {
                orderImgs += (("".equals(orderImgs) ? "" : ",") + arr[0]);
                orderImgsThumb += (("".equals(orderImgsThumb) ? "" : ",") + arr[1]);
            } else {
                orderImgs += (("".equals(orderImgs) ? "" : ",") + null);
                orderImgsThumb += (("".equals(orderImgsThumb) ? "" : ",") + null);
            }
        }
        return null;
    }
}
