package com.dh.common;

import com.dh.dto.TreeNode;
import org.springside.modules.utils.Clock;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class  CommonButil {
	
	private static Clock clock = Clock.DEFAULT;
	
	/**
	 * 判断变量是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
        return null == s || "".equals(s) || "".equals(s.trim()) || "null".equalsIgnoreCase(s);
	}

	public static boolean isEmpty(Object[] s) {
        return null == s || s.length < 1;
	}

	public static boolean isEmpty(List<?> s) {
        return null == s || s.isEmpty() || s.size() < 1;
	}

	public static boolean isEmpty(Object s) {
        return null == s;
	}

	public static boolean isEmpty(Map<?, ?> s) {
        return null == s || s.isEmpty() || s.size() < 1 || s.keySet().size() < 1;
	}
	
	/**
	 * 获取指定长度的随机数
	 * 
	 * @param length
	 *            长度
	 * @return
	 */
	public static String getRandomNum(int length) {
		String num = "";
		for (int i = 1; i < length; i++) {
			num += "0";
		}
		long a = Long.parseLong(9 + num);
		long b = Long.parseLong(1 + num);
		return Math.round(Math.random() * a + b) + "";
	}
	
	
	
	/**
	 * 解析树形结构对象为list结构
	 * @param tempList
	 * @param treeList
	 * @return
	 */
	public static List<TreeNode> deTreeNode(List<TreeNode> tempList,List<TreeNode> treeList){
		for (TreeNode tree : treeList) {
			tempList.add(tree);
			if(tree.getChildren().size() > 0){
				deTreeNode(tempList,tree.getChildren());
				tree.setChildren(null);
			}
		}
		return tempList;
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static Date getNowTime(){
		return clock.getCurrentDate();
	}
	/**
	 * 通过时间的字符串判断是在现在时间之前还是之后
	 * @return
	 */
	public static String[] getTimeStrArray(String time){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String[] oneTimes={};
		try {
			Date selectTime=sdf.parse(time);
			Date nowTime=new Date();
			if (selectTime.getTime() > nowTime.getTime()) {
				System.out.println("之前");
				oneTimes[0]=time;
				oneTimes[1]=sdf.format(nowTime);
			} else if (selectTime.getTime() < nowTime.getTime()) {
				System.out.println("之后");
				oneTimes[0]=sdf.format(nowTime);
				oneTimes[1]=time;
			} else if(selectTime.getTime() == nowTime.getTime()){
				oneTimes[0]=time;
				oneTimes[1]=time;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return oneTimes;
	}


		public static String encodeStr(String str) {
			try {
				return new String(str.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {			e.printStackTrace();
				return null;
			}
		}
		/*格式化时间*/
		public  static List<String> StartEndtoList(String start,String end){
			List<String> orderTime=new ArrayList<String>();
			if (!isEmpty(start)){
				start+=" 00:00:00";
			}
			if (!isEmpty(end)){
				end+=" 23:59:59";
			}
			orderTime.add(start);
			orderTime.add(end);
			return orderTime;
		}


	/**
	 * 获取当前时间字符串
	 *
	 * @return
	 */
	public static String getNowTimeStr() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getNowTime());
	}


}
