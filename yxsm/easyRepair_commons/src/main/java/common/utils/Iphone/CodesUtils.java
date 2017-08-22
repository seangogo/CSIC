package common.utils.Iphone;

import java.util.Date;

/**
 * Created by sean on 2016/11/21.
 */
public class CodesUtils {
    /*根据时间戳生产*/
    public static String orderCode() {
        Date date = new Date("January 01, 2016 00:00:00");
        Long sys = System.currentTimeMillis();
        Long time = sys - date.getTime();
        byte[] bys = String.valueOf(time).getBytes();
        return String.valueOf(time) + bys.hashCode();

    }
    public static String jobCode(String iphoneCode) {
        return  String.valueOf(Long.valueOf(iphoneCode)-9999999998L);

    }
    /*一秒内10000次测试*/
    /*public static void main(String[] args) {
       *//* System.out.println(date.getTime());
        System.out.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis()-date.getTime());

        jobCode(String.valueOf(System.currentTimeMillis()-date.getTime()));*//*
        ok:
        for (int i = 0; i < 10; i++) {
            String times = jobCode("13997986177");
            System.out.println(times);
            List<String> timeList = new ArrayList<String>();
            if (timeList.contains(times)) {
                for (String s : timeList) {
                    if (s.equals(times)) {
                        System.out.println(i + "___");
                        System.out.println(times);
                        break ok;
                    }
                }

            }
            timeList.add(times);
        }
    }*/
}

