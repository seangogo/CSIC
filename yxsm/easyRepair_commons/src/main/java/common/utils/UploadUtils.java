package common.utils;

public class UploadUtils {

    /**
     * 将二进制流格式的文件转换为File格式的文件
     *
     * @param file
     * @return
     */
   /* public static File changeFile(MultipartFile file) {
        CommonsMultipartFile cf = (CommonsMultipartFile) file;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File files = fi.getStoreLocation();
        return files;
    }

    public static String saveImage(MultipartFile file) {
        if (!FileUtil.isImage(file.getOriginalFilename())) {
            GeneralExceptionHandler.handle("文件格式不正确，请上传图片!");
        }
        FileBo result = null;
        try {
            result = save(file);
        } catch (IOException e) {
            GeneralExceptionHandler.handle("上传失败,服务器繁忙!");
        }
        return Configue.getUploadUrl() + result.getPath();
    }

    public static FileBo save(MultipartFile file) throws IOException {
        FileBo fileBo = save1(file, String.valueOf(System.currentTimeMillis()));
        return fileBo;
    }

    public static FileBo save1(MultipartFile file, String fileName) throws IOException {

        FileBo fileBo = new FileBo();
        InputStream inputStream = file.getInputStream();
        String origFileName = file.getOriginalFilename(); //原始名称,如aa.jpg
        String ext = FileUtil.getFileExt(origFileName); //后缀，如jpg
        String uploadPath = UploadUtil.getImagesUpladPath(); //生成日期目录 image/2014/7/21/
        String foreName = uploadPath + fileName;   //文件名称 image/2014/7/21/221144144554
        String desFilePathName = uploadPath + fileName + ext;//完整文件名称 image/2014/7/21/221144144554.jpg
        File theFile = new File(Configue.getUploadPath(), desFilePathName); //生成的文件对象
        fileBo.setName(fileName);
        fileBo.setForeName(foreName);
        fileBo.setExt(ext);
        fileBo.setPath(desFilePathName);
        fileBo.setFile(theFile);
        FileUtils.copyInputStreamToFile(inputStream, theFile);

        return fileBo;
    }*/

}
