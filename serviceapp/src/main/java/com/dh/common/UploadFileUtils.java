package com.dh.common;

import com.dh.entity.FileBo;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Coolkid on 2016/10/8.
 */
public class UploadFileUtils {
    private static final String TYPE_IMAGES="wordOrExcel";
    /**
     * 取得项目的根目录
     */
    private static String ROOT_PATH =  UploadImgButil.class.getResource("/").getPath();

    /*通过配置文件属性获得路径*/
    public static String  getPathProperties(String config,String keyTo){
        // 获得资源包
        ResourceBundle rb = ResourceBundle.getBundle(config.trim());
        // 通过资源包拿到所有的key
        Enumeration<String> allKey = rb.getKeys();
        /*// 遍历key 得到 value
        Map<String,String> valMap = new HashMap<String, String>();*/
        String result="";
        while (allKey.hasMoreElements()) {
            String key = allKey.nextElement();
            if (keyTo.equals(key)) {
                result= rb.getString(key);
            }
        }
        return result;
    }
    public static String getFileExt(String fileName) {

            int index = fileName.lastIndexOf('.');
            if (index == -1) {
                return "";
            }
            String ext = fileName.substring(index);
            return ext;
        }


        public static boolean isImage(String fileName) {
            return fileName.matches("(?i).+?\\.(jpg|gif|bmp|jpeg|png)");
        }

        /*
        * 简单说明下，files就是你需要的那个指定类型文件的file对象list，
        * files：不可为null
        * suffixs:文件后缀，注意全是小写，不包含点，比如：rar，text等，支持* 或者空list
        * path：任意路径
        */
        public static List<File> getAllFiles(List<File> files,
                                             List<String> suffixs, String path) {
            File fileT = new File(path);
            if (fileT.exists()) {
                if (fileT.isDirectory()) {
                    for (File f : fileT.listFiles()) {
                        files = getAllFiles(files, suffixs, f.getAbsolutePath());
                    }
                } else {
                    //被没后缀的文件坑过，所以加了无后缀文件判断
                    if (suffixs.size() == 0
                            || suffixs.contains("*")
                            || (fileT.getName().indexOf(".") != -1 && suffixs
                            .contains(fileT.getName().split("\\.")[1]
                                    .toLowerCase()))) {
                        files.add(fileT);
                    }
                }
            }
            return files;

        }

        public static String readFileByChars(String fileName) {
            File file = new File(fileName);
            Reader reader = null;
            try {
                // 一次读一个字符
                reader = new InputStreamReader(new FileInputStream(file));

                StringBuffer sb = new StringBuffer();

                int tempchar;
                while ((tempchar = reader.read()) != -1) {
                    // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                    // 但如果这两个字符分开显示时，会换两次行。
                    // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                    if (((char) tempchar) != '\r' && ((char) tempchar) != '\n') {
                        sb.append(String.valueOf((char) tempchar));
                    }
                }
                reader.close();
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
            }
            return null;
        }

        /**
         * 获取指定文件夹下所有文件路径
         *
         * @param path
         * @return
         */
        public static List getFileList(String path) {
            List list = new ArrayList();
            try {
                File file = new File(path);
                if (file == null) {
                    return null;
                }
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    list.add(path + "/" + filelist[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

        /**
         * 获取指定文件夹下所有文件名
         *
         * @param path
         * @return
         */
        public static List getFileListName(String path) {
            List list = new ArrayList();
            try {
                File file = new File(path);
                if (file == null) {
                    return null;
                }
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    list.add(filelist[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

        /**
         * 获取指定文件夹下指定文件名路径
         *
         * @param path
         * @param name
         * @return
         */
        public static List getFileList(String path, String name) {
            List list = new ArrayList();
            try {
                File file = new File(path);
                if (file == null) {
                    return null;
                }
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    if (filelist[i].startsWith(name)) {//对比文件名
                        list.add(path + "/" + filelist[i]);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

        /**
         * 获取指定文件夹下指定文件名(包含)路径
         *
         * @param path
         * @param name
         * @return
         */
        public static List getFileListByFileName(String path, String name) {
            List list = new ArrayList();
            try {
                File file = new File(path);
                if (file == null) {
                    return null;
                }
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    if (filelist[i].contains(name)) {//对比文件名
                        list.add(path + "/" + filelist[i]);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

        /**
         * 获取指定文件夹下指定文件名文件名
         *
         * @param path
         * @param name
         * @return
         */
        public static List getFileListName(String path, String name) {
            List list = new ArrayList();
            try {
                File file = new File(path);
                if (file == null) {
                    return null;
                }
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    if (filelist[i].startsWith(name)) {//对比文件名
                        list.add(filelist[i]);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

        /**
         * 获取指定文件夹下除去指定文件所有文件路径
         *
         * @param path
         * @param name
         * @return
         */
        public static List getFileListDel(String path, String name) {
            List list = new ArrayList();
            try {
                File file = new File(path);
                if (file == null) {
                    return null;
                }
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    if (!filelist[i].startsWith(name)) {//除去此文件文件名
                        list.add(path + "/" + filelist[i]);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

        /**
         * 获取指定文件夹下除去指定文件名(包含)路径
         *
         * @param path
         * @param name
         * @return
         */
        public static List getFileListByFileNameDel(String path, String name) {
            List list = new ArrayList();
            try {
                File file = new File(path);
                if (file == null) {
                    return null;
                }
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    if (!filelist[i].contains(name)) {//对比文件名
                        list.add(path + "/" + filelist[i]);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

        /**
         * 获取指定文件夹下除去指定文件所有文件名
         *
         * @param path
         * @param name
         * @return
         */
        public static List getFileListDelName(String path, String name) {
            List list = new ArrayList();
            try {
                File file = new File(path);
                if (file == null) {
                    return null;
                }
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    if (!filelist[i].startsWith(name)) {//除去此文件文件名
                        list.add(path + "/" + filelist[i]);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

        public static void deleteFiles(String path) {
            File file = new File(getPathProperties("config","file.path")+path);
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int j = 0; j < filelist.length; j++) {
                    File filessFile = new File(path + "/" + filelist[j]);
                    if (!filessFile.isDirectory()) {
                        filessFile.delete();
                    } else if (filessFile.isDirectory()) {
                        deleteFiles(path + "/" + filelist[j]);
                    }
                }
                file.delete();
            }
        }
        /*判断是否是WORD和excel格式*/
        public static boolean isWordOrExcel(String fileName) {
            return fileName.matches("(?i).+?\\.(XLS|XLSX|doc|docx|dot|dotx|docm|dotm|.xml|XLA|XLB|XLC|XLD|XLK|XLL|XLM|XLSHTML|XLT|XLSMHTML)");
        }

    public static FileBo saveWordOdExcel(MultipartFile file, String fileName) throws IOException {
        FileBo fileBo = new FileBo();
        if (!isWordOrExcel(file.getOriginalFilename())){
            return fileBo;
        }
        InputStream inputStream = file.getInputStream();
        String origFileName = file.getOriginalFilename(); //原始名称,如aa.jpg
        String ext = FileUtil.getFileExt(origFileName); //后缀，如jpg
        String uploadPath = getImagesUpladPath(); //生成日期目录 image/2014/7/21/
        String foreName = uploadPath + fileName;   //文件名称 image/2014/7/21/221144144554
        String desFilePathName = uploadPath + fileName + ext;//完整文件名称 image/2014/7/21/221144144554.jpg
        String filePath=getPathProperties("config","file.path");
        File theFile = new File(filePath,desFilePathName); //生成的文件对象
        fileBo.setName(fileName);//新名称
        fileBo.setForeName(foreName);//全名称
        fileBo.setExt(ext);//类型
        fileBo.setPath(desFilePathName);//全路径
        fileBo.setFile(theFile);//文件
        FileUtils.copyInputStreamToFile(inputStream, theFile);
        return fileBo;
    }

    public static String getImagesUpladPath(){
        Calendar now = Calendar.getInstance();
        int year=now.get(Calendar.YEAR);
        int month=now.get(Calendar.MONTH)+1;
        String uploadFilePath=TYPE_IMAGES+"/"+year+"/"+month+"/";
        return uploadFilePath;
    }
    public static String GetFileSize(String Path){
        return GetFileSize(new File(Path));
    }


    public static String GetFileSize(File file){
        String size = "";
        if(file.exists() && file.isFile()){
            long fileS = file.length();
            DecimalFormat df = new DecimalFormat("#.00");
            if (fileS < 1024) {
                size = df.format((double) fileS) + "BT";
            } else if (fileS < 1048576) {
                size = df.format((double) fileS / 1024) + "KB";
            } else if (fileS < 1073741824) {
                size = df.format((double) fileS / 1048576) + "MB";
            } else {
                size = df.format((double) fileS / 1073741824) +"GB";
            }
        }else if(file.exists() && file.isDirectory()){
            size = "";
        }else{
            size = "0BT";
        }
        return size;
    }

}


