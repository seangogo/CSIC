package com.easyRepair.service.Cache;

/**
 Created by sean on 2016/11/16. */
public class Checkcache {
    static String VAL = "";
    
    public Checkcache(String val) {
        VAL = val;
    }
    
    //返回val
    public static String returnval() {
        return VAL;
    }
}
