/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dh.service.system.socket;

import com.google.gson.Gson;

/**
 * 
 * @author Owner
 */
public class CommUtils {

	/**
	 * 参数序列化
	 * 
	 * @param obj
	 *            任意对象
	 * @return
	 */
	public static String getParamJson(Object obj) {
		Gson gson=new Gson();
		return gson.toJson(obj);
	}

}
