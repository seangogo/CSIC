package com.easyRepair.entityUtils;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author sean
 * @Description 图片操作工具类
 * @依赖：servlet-api.jar,spring-core-3.1.2.RELEASE.jar,spring-web-3.1.2.RELEASE.jar
 * @date 2014-09-25 11:49:48
 */
public class ImageUtils {

    //private static String imagePath = PathUtils.getProjectName() + "upload/temp/";
    //private static String imageSuoluePath = PathUtils.getProjectName() + "upload/suolue/";

    /**
     * @return BufferedImage 缩放后的图片
     * @Description 缩放图片
     * source 源图片,targetW 缩放后的宽度,targetH 缩放后的长度;
     * @date 2014-7-30 下午03:08:52
     * @author sean
     */
    private static BufferedImage getResizedImage(BufferedImage source, int targetW, int targetH) {
        int type = source.getType();
        BufferedImage target = null;
        // targetW，targetH分别表示目标长和宽
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        //如果目标长度超出现有长度，则返回原图，如果目标长度超出现有长度，则返回原图
        if (targetW >= source.getWidth() || targetH >= source.getHeight()) {
            return source;
        }
        // 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
        // 则将下面的if else语句注释即可
        if (sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) { // handmade
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(targetW, targetH, type);
        }
        Graphics2D g = target.createGraphics();
        // smoother than exlax:
        g.setColor(Color.WHITE);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    /**
     * @return BufferedImage 缩放后的图片
     * @Description 等比缩放图片
     * file 源图片,targetW 缩放后的宽度,targetH 缩放后的长度;
     * @date 2014-7-30 下午03:08:52
     * @author sean
     */
    public static BufferedImage resize(File file, int width, int height) {
        try {
            BufferedImage srcImage = ImageIO.read(file);
            if (width > 0 || height > 0) {
                srcImage = getResizedImage(srcImage, width, height);
            }
            return srcImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图片上传 并保存缩略图(上传的文件保存在项目同级的目录下) imgWidth 原图宽度， imgHeight 原图高度 ，imgURL 原图路径， imgThu 缩略图路径
     *
     * @param request
     * @param imageFile
     * @param needSmallImage
     * @return
     * @throws Exception
     */
    public static Map<String, Object> saveImage(ServletRequest request, MultipartFile imageFile, boolean needSmallImage, String imagePath) throws Exception {
        Random rand = new Random();
        Map<String, Object> pathMap = new HashMap<String, Object>();
        if (null != imageFile && imageFile.getOriginalFilename().toLowerCase().trim().length() != 0) {
            String originalFileName = imageFile.getOriginalFilename().toLowerCase();
            String filetype = originalFileName.substring(originalFileName.indexOf("."));
            String timeMillis = String.valueOf(System.currentTimeMillis());
            Integer urlRandNum = rand.nextInt(1000000);
            String path = imagePath + "/imgs/" + timeMillis + "_" + urlRandNum + filetype;
            String webpath = getWebPath(request);
            // 获取项目在磁盘上面的物理路径
            File image = new File(webpath + path);
            File dir = image.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileCopyUtils.copy(imageFile.getBytes(), image);

            // 获取图片属性
            BufferedImage imgBufferedImage = ImageIO.read(image);
            // 原图宽度
            pathMap.put("imgWidth", imgBufferedImage.getWidth());
            // 原图高度
            pathMap.put("imgHeight", imgBufferedImage.getHeight());
            // 原图URL
            pathMap.put("imgURL", /*"/" +*/ path);
            if (needSmallImage) {
                String suoluepath = imagePath + "imgsThu/" + timeMillis + "_" + urlRandNum + "-s" + filetype;
                try {
                    FileCopyUtils.copy(imageFile.getBytes(), image);
                    BufferedImage bi = ImageUtils.resize(image, 500, 500);// 根据上传的图片文件生成对应的缩略图
                    File smallfile = new File(new File(webpath) + "/" + suoluepath);
                    if (!smallfile.getParentFile().exists()) {// 如果文件夹不存在
                        smallfile.getParentFile().mkdirs();// 创建上传文件夹
                    }
                    ImageIO.write(bi, "jpg", smallfile);// 将缩略图写入服务器端的制定文件夹中
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 缩略图路径
                pathMap.put("imgThu", "/" + suoluepath);
            } else {
                // 缩略图路径
                pathMap.put("imgThu", "");
            }
        }
        return pathMap;
    }

    /**
     * @return String 时间格式名称
     * @Description 传入原图名称, 获得一个以时间格式的新名称
     * fileName 原图名称;
     * @date 2014-7-30 下午03:08:52
     * @author sean
     */
    public static String generateFileName(String fileName) {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatDate = format.format(new Date());
        int random = new Random().nextInt(10000);
        int position = fileName.lastIndexOf(".");
        String extension = fileName.substring(position);
        return formatDate + random + extension;
    }

    /**
     * @return String 时间格式名称
     * @Description 获取项目在tomcat下的地址
     * request request 请求;
     * @date 2014-7-30 下午03:08:52
     * @author sean
     */
    public static String getWebPath(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(true);
        String path = session.getServletContext().getRealPath("/").replace(File.separator, "/");
        String[] paths = path.split("/");
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < paths.length; i++) {
            buff.append(paths[i] + "/");
        }
        String newpath = buff.toString();
        return newpath;
    }
}
