/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.web.api;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dh.common.CommonButil;
import com.dh.common.UploadImgButil;
import com.dh.service.order.OrderService;
import com.dh.service.system.NewsService;

@Controller
@RequestMapping(value = "/api")
public class ServiceApiController {
	@Autowired
	private NewsService newsService;

	@Autowired
	private OrderService orderService;

	/**
	 * 保存图片到指定路径并压缩
	 * 
	 * @param imgStr
	 *            base64图片字符串
	 * @param folder
	 *            指定路径目录
	 * @param targetWidth
	 *            压缩宽度
	 * @param targetHeight
	 *            压缩高度
	 * @return json 每个图片以 xx.jpg,xx-s.jpg 字符串形式返回
	 */
	@RequestMapping(value = "uploadImgThumb", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadImgThumb(@RequestParam(value = "imgStr", defaultValue = "") String imgStr,
			@RequestParam(value = "folder", defaultValue = "") String folder,
			@RequestParam(value = "targetWidth", defaultValue = "") String targetWidth,
			@RequestParam(value = "targetHeight", defaultValue = "") String targetHeight) {
		if (CommonButil.isEmpty(imgStr)) {
			return setReturnValue("请求参数错误", 0, null, null, null);
		}
		int w = 0;
		int h = 0;
		if (!CommonButil.isEmpty(targetWidth)) {
			w = Integer.parseInt(targetWidth);
		}
		if (!CommonButil.isEmpty(targetHeight)) {
			h = Integer.parseInt(targetHeight);
		}
		Object imgUrl = new Object();
		if (w == 0 && h == 0) {
			return setReturnValue("请求参数错误", 0, null, null, null);
		}
		imgUrl = UploadImgButil.decodeBase64ToImage(imgStr, folder, w, h);

		if (imgUrl == null) {
			return setReturnValue("生成图片失败", 0, null, null, null);
		}
		return setReturnValue("生成图片成功", 1, imgUrl, null, null);
	}

	/**
	 * 保存图片到指定路径并压缩
	 * 
	 * @param imgStrs
	 *            base64图片字符串 多个以逗号隔开
	 * @param folder
	 *            指定路径目录
	 * @param targetWidth
	 *            压缩宽度
	 * @param targetHeight
	 *            压缩高度
	 * @return json 每个图片以 [{xx.jpg,xx-s.jpg},{...}] 形式返回
	 */
	@RequestMapping(value = "uploadImgsThumb", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadImgThumbs(@RequestParam(value = "imgStrs", defaultValue = "") String imgStrs,
			@RequestParam(value = "folder", defaultValue = "") String folder,
			@RequestParam(value = "targetWidth", defaultValue = "") String targetWidth,
			@RequestParam(value = "targetHeight", defaultValue = "") String targetHeight) {
		if (CommonButil.isEmpty(imgStrs)) {
			return setReturnValue("请求参数错误", 0, null, null, null);
		}
		int w = 0;
		int h = 0;
		if (!CommonButil.isEmpty(targetWidth)) {
			w = Integer.parseInt(targetWidth);
		}
		if (!CommonButil.isEmpty(targetHeight)) {
			h = Integer.parseInt(targetHeight);
		}
		Object imgUrl = new Object();
		if (w == 0 && h == 0) {
			return setReturnValue("请求参数错误", 0, null, null, null);
		}

		String[] imgStrArr = imgStrs.split(",");

		imgUrl = UploadImgButil.decodeBase64ToImage(imgStrArr, folder, w, h);

		if (imgUrl == null) {
			return setReturnValue("生成图片失败", 0, null, null, null);
		}
		return setReturnValue("生成图片成功", 1, imgUrl, null, null);
	}

	/**
	 * 上传图片保存在指定目录
	 * 
	 * @param imgStr
	 *            base64图片字符串
	 * @param folder
	 *            指定路径目录
	 * @return json 返回图片路径 xx.jpg
	 */
	@RequestMapping(value = "uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadImg(@RequestParam(value = "imgStr", defaultValue = "") String imgStr,
			@RequestParam(value = "folder", defaultValue = "") String folder) {
		if (CommonButil.isEmpty(imgStr)) {
			return setReturnValue("请求参数错误", 0, null, null, null);
		}
		String imgUrl = UploadImgButil.decodeBase64ToImage(imgStr, folder);

		if (imgUrl == null) {
			return setReturnValue("生成图片失败", 0, null, null, null);
		}
		return setReturnValue("生成图片成功", 1, imgUrl, null, null);
	}

	@RequestMapping(value = "uploadImg", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> uploadImg() {
		return setReturnValue("请用post请求访问", 0, null, null, null);
	}

	@RequestMapping(value = "uploadImgThumb", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> uploadImgThumb() {
		return setReturnValue("请用post请求访问", 0, null, null, null);
	}

	/**
	 * 查看新闻修改
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "newsContent/{id}", method = RequestMethod.GET)
	public String newsContent(Model model, @PathVariable("id") Long id) {
		model.addAttribute("news", newsService.findNewsById(id));
		return "api/newsContent";
	}

	/**
	 * 定时操作：自动评价
	 * 
	 * @return
	 */
	@RequestMapping(value = "commentTimeoutOrder", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> commentTimeoutOrder() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				orderService.commentTimeoutOrder();
			}
		}, 1000, 86400000);
		return setReturnValue("操作成功", 1, null, null, null);
	}

	/**
	 * 结构返回值
	 * 
	 * @param m
	 *            返回消息
	 * @param c
	 *            请求成功/失败
	 * @param o
	 *            返回对象
	 * @param e
	 *            返回其他内容
	 * @param r
	 *            错误状态码
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> setReturnValue(String m, int c, Object o, Object e, String r) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("m", m);
		map.put("c", c);
		map.put("o", o);
		map.put("e", e);
		map.put("r", r);
		return map;
	}
}
