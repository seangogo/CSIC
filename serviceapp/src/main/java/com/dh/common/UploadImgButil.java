package com.dh.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springside.modules.utils.Encodes;

public class UploadImgButil {

	/**
	 * 图片后缀（类型）
	 */
	private static String POSTFIX = "png";
	
	/**
	 * 质量
	 */
	private static Float  QUALITY = 0.8f;
	
	/**
	 * 是否保持图标比例
	 */
	private static Boolean  KEEPRATIO = true;

	public static String encodeImgageToBase64(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		return Encodes.encodeBase64(data);// 返回Base64编码过的字节数组字符串
	}

	/**
	 * 解析base64字符串 生成图标保存在本地
	 * 
	 * @param imgStr
	 * @return
	 */
	public static String decodeBase64ToImage(String imgStr, String folder) {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return null;
		try {
			// Base64解码
			byte[] b = Encodes.decodeBase64(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpg图片
			String folderPath = UploadFileUtils.getPathProperties("config","img.path") + folder;// 查看目录是否存在，不存在则创建
			if (!(new File(folderPath).exists())) {
				new File(folderPath).mkdirs();
			}
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String imgName = dateFormat.format(new Date())+ CommonButil.getRandomNum(3);
			String imgFilePath = folderPath + imgName + "." +POSTFIX;// 新生成的图片
			
			// TODO 若当前操作目录文件需要权限时，需增加更改当前目录的权限访问的功能
			
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			
			File tempFile = new File(imgFilePath);
			if(!tempFile.exists())  {
				return null;
			}
			
			return folder+imgName+"."+POSTFIX;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * 解析base64字符串 生成图标保存在本地
	 * @param imgStr 图片base64
	 * @param folder  保存文件夹
	 * @param targeWidth  压缩后的宽度
	 * @param targetHeight 压缩后的高度
	 * @return
	 */
	public static String decodeBase64ToImage(String imgStr, String folder,int targeWidth, int targetHeight) {
		if (imgStr == null) // 图像数据为空
			return null;
		try {
			// Base64解码
			byte[] b = Encodes.decodeBase64(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpg图片
			String folderPath = UploadFileUtils.getPathProperties("config","img.path") + folder;// 查看目录是否存在，不存在则创建
			if (!(new File(folderPath).exists())) {
				new File(folderPath).mkdirs();
			}
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
			String imgName = dateFormat.format(new Date()) + CommonButil.getRandomNum(3);
			String imgFilePath = folderPath + imgName + "." +POSTFIX;// 新生成的图片
			
			// TODO 若当前操作目录文件需要权限时，需增加更改当前目录的权限访问的功能
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			
			File tempFile = new File(imgFilePath);
			if(!tempFile.exists())  {
				return null;
			}
			
			String newImgName = imgName+"-s"+ "." +POSTFIX;
			String newImgFilePath = folderPath + newImgName;
			
			Boolean flag = ImgCompressButil.resizeImage(tempFile, KEEPRATIO, targeWidth,
					targetHeight, QUALITY, new File(newImgFilePath));
			if(!flag){
				deleteFile(imgFilePath);
				return null;
			}
			//截取指定字符 按逗号分隔 原图和压缩图
			return folder+imgName + "." +POSTFIX +","+folder+newImgName;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 解析base64字符串 生成图标保存在本地
	 * @param imgStrArr 图片base64
	 * @param folder  保存文件夹
	 * @param targeWidth  压缩后的宽度
	 * @param targetHeight 压缩后的高度
	 * @return
	 */
	public static List<String> decodeBase64ToImage(String[] imgStrArr, String folder,int targeWidth, int targetHeight) {
		if (imgStrArr == null || imgStrArr.length==0) // 图像数据为空
			return null;
		try {
			List<String> imgPathList = new ArrayList<String>();
			for(String imgStr:imgStrArr){
				String imgPathStr = decodeBase64ToImage(imgStr,folder,targeWidth,targetHeight);
				if(CommonButil.isEmpty(imgPathStr)){
					imgPathList.add(",");
				}else{
					imgPathList.add(imgPathStr);
				}
			}
			 return imgPathList;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 从指定字符串位置开始截取字符串
	 * @param str
	 * @return
	 */
	private static String subStringBystr(String str){
		return str.substring(str.indexOf("img"));
	}
	
	
	/**
	 * 删除文件
	 * @param sPath
	 * @return
	 */
	public static boolean deleteFile(String sPath) {  
		Boolean flag = false;
		File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  
	
}
