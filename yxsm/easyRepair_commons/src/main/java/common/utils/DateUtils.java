package common.utils;

import org.apache.poi.ss.usermodel.DateUtil;

import java.text.SimpleDateFormat;

/**
 * Created by sean on 2016/11/24.
 */
public class DateUtils {
    /**
     * 判断时间格式 格式必须为“YYYY-MM-dd”
     * 2004-2-30 是无效的
     * 2003-2-29 是无效的
     * @param s
     * @return
     */
    public static boolean isValidDate(String s)
    {
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            dateFormat.parse(s);
            return true;
        }
        catch (Exception e)
        {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println( DateUtils.isValidDate("201620-30"));
    }
}
