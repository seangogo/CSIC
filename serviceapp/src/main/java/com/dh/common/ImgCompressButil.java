package com.dh.common;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class ImgCompressButil {
	
	
	private static String POSTFIX = "png";
	
	
	/**
	 * 缩放图片
	 * @param srcFile {@link File} 源文件
	 * @param keepRatio {@link Boolean} 是否保持原尺寸比例
	 * @param targeWidth {@link Integer} 生成图片的宽度  传0 则按高度等比压缩
	 * @param targetHeight {@link Integer} 生成图片的宽度 传0 则按宽度等比压缩
	 * @param quality {@link Float} 压缩比例
	 * @param targetFile {@link File} 生成图片的文件
	 */
	public static Boolean  resizeImage(File srcFile, boolean keepRatio,
			int targeWidth, int targetHeight, float quality, File targetFile) {
		try {
			BufferedImage srcImage = ImageIO.read(srcFile);
			if(null == srcImage){
				return null;
			}
			int type = srcImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB
					: srcImage.getType();
			int w = srcImage.getWidth();
			int h = srcImage.getHeight();
			if(targetHeight == 0 && targeWidth != 0){
				targetHeight = h * targeWidth / w;
			}
			
			if(targeWidth == 0 && targetHeight != 0){
				targetHeight = w * targetHeight / h;
			}
			
			int[] size = ImgCompressButil.calculateImgSize(srcImage, keepRatio,
					targeWidth, targetHeight);
			int x = size[0];
			int y = size[1];
			int drawWidth = size[2];
			int drawHeight = size[3];
			targeWidth = size[4];
			targetHeight = size[5];
			BufferedImage resizedImage = new BufferedImage(targeWidth,
					targetHeight, type);
			Graphics2D g = resizedImage.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.setPaint(Color.BLACK);
			g.fillRect(0, 0, targeWidth, targetHeight);
			g.drawImage(srcImage, x, y, drawWidth, drawHeight, null);
			g.dispose();
			
			ImageWriter imgWriter = ImageIO.getImageWritersByFormatName(POSTFIX)
					.next();
			ImageWriteParam imgWriteParam = imgWriter.getDefaultWriteParam();
			if (imgWriteParam.canWriteCompressed()) {
				imgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				imgWriteParam.setCompressionQuality(quality);
			}

			ImageOutputStream outputStream = ImageIO
					.createImageOutputStream(targetFile);
			imgWriter.setOutput(outputStream);
			IIOImage outputImage = new IIOImage(resizedImage, null, null);
			imgWriter.write(null, outputImage, imgWriteParam);
			imgWriter.dispose();
			outputStream.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 计算缩放后的图片宽度和高度
	 * @param img {@link BufferedImage} 原图片
	 * @param keepRatio {@link Boolean} 是否等比例缩放
	 * @param targetWidth {@link Integer} 目标宽度
	 * @param targetHeight {@link Integer} 目标高度
	 * @return 返回计算结果数组
	 */
	private static int[] calculateImgSize(BufferedImage img, boolean keepRatio,
			int targetWidth, int targetHeight) {
		int sourceWidth = img.getWidth();
		int sourceHeight = img.getHeight();
		int x = 0;
		int y = 0;
		int drawWidth = targetWidth;
		int drawHeight = targetHeight;

		double sourceRatio = (double) sourceWidth / (double) sourceHeight;
		double targetRatio = (double) targetWidth / (double) targetHeight;

		/*
		 * If the ratios are not the same, then the appropriate width and height
		 * must be picked.
		 */
		if (Double.compare(sourceRatio, targetRatio) != 0) {
			if (sourceRatio > targetRatio) {
				drawHeight = (int) Math.round(targetWidth / sourceRatio);
			} else {
				drawWidth = (int) Math.round(targetHeight * sourceRatio);
			}
		}
		if (keepRatio) {
			targetWidth = drawWidth;
			targetHeight = drawHeight;
		} else {
			x = (targetWidth - drawWidth) / 2;
			y = (targetHeight - drawHeight) / 2;
		}

		targetWidth = (targetWidth == 0) ? 1 : targetWidth;
		targetHeight = (targetHeight == 0) ? 1 : targetHeight;
		int[] size = { x, y, drawWidth, drawHeight, targetWidth, targetHeight };
		return size;
	}
	
//	public static void main(String[] args) {
////		String oldpath = "D:\\20120918052939765.jpg";
////		String newpath = "D:\\000.jpg";
////		ImgCompressButil ddd = new ImgCompressButil();
////		ddd.Tosmallerpic(new File(oldpath), new File(newpath), 300, 200, 1f);
//		String str = "static/uploadFiles/userIco/8/20160429091626.jpg";
//		String[] arr = str.split("[.]");
//		String newImgPath = arr[0] + "-s"+"."+POSTFIX;
//		ImgCompressButil.resizeImage(new File(BASE_PATH+str), true, 100,
//				0, 0.7f, new File(BASE_PATH+newImgPath));
//	}
}
