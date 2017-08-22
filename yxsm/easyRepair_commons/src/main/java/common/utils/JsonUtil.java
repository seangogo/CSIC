package common.utils;

import com.google.gson.*;
import common.utils.gson.DmsExclusionStrategy;
import common.utils.gson.NullStringToEmptyAdapterFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Type;

/**
 * Created by sean on 16-11-07.
 */
public class JsonUtil {

    protected final static Log logger = LogFactory.getLog(JsonUtil.class);

    public static GsonBuilder createGsonBuilder(){
        return new GsonBuilder().serializeNulls().
                setDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * json字符串转换成对象
     * @param str  json字符串
     * @param type 对象类型
     * @param <T>
     * @return
     */
    public static <T> T json2Obj(String str, Type type){
        Gson gson = createGsonBuilder().create();
        return gson.fromJson(str,type);
    }




    public static String obj2Json(Object obj){
        Gson gson = createGsonBuilder().create();
        return  gson.toJson(obj);
    }

    /**
     * java对象转换成json字符串
     * @param obj  java对象
     * @param excludes 过滤字段
     * @return
     */
    public static String obj2Json(Object obj, String... excludes ){
        ExclusionStrategy strategy = new DmsExclusionStrategy(excludes);
        Gson gson =createGsonBuilder()
                .setExclusionStrategies(strategy)
                .create();
        return  gson.toJson(obj);
    }


    /**
     * java对象转换成json字符串
     * @param obj
     * @param classes 类别数组
     * @param excludes  字符串数组
     * @return
     */
    public static String obj2Json(Object obj, Class[] classes, String... excludes){
        ExclusionStrategy strategy = new DmsExclusionStrategy(excludes,classes);
        Gson gson =createGsonBuilder()
                  .setExclusionStrategies(strategy)
                .create();
        return  gson.toJson(obj);
    }


    /**
     * java对象转换成json字符串,过滤成api需要的字段值
     * @param obj
     * @param excludes
     * @return
     */
    public static String obj2ApiJson(Object obj, String... excludes ){
        ExclusionStrategy strategy = new DmsExclusionStrategy(excludes);
        Gson gson = createGsonBuilder()
                .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
                .setExclusionStrategies(strategy)
                .create();
        String json = gson.toJson(obj);
        return  filterJson(json);
    }

    /**
     * 过滤字符串 -> true转换成0，false转换成1,null转换成""或者{}
     * @param json
     * @return
     */
    public static String filterJson(String json){
        //"image":null
        String lastStr =  json.replaceAll(":true", ":\"0\"")
                .replaceAll(":false", ":\"1\"")
                .replaceAll(":null", ":{}");

        return lastStr;
    }


    public static Boolean isGoodJson(String json){
        if (StringUtils.isBlank(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            logger.error("bad json: " + json);
            return false;
        }
    }









}
