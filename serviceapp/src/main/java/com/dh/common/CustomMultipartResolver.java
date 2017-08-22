package com.dh.common;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import com.dh.web.listener.FileUploadProgressListener;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * Created by Coolkid on 2016/10/8.
 */
public class CustomMultipartResolver extends CommonsMultipartResolver{
        @Autowired
        private FileUploadProgressListener progressListener;

        public void setFileUploadProgressListener(FileUploadProgressListener progressListener){
            this.progressListener = progressListener;
        }

        public MultipartParsingResult parsingResult(HttpServletRequest request){
            String encoding = determineEncoding(request);
            FileUpload fileUpload = prepareFileUpload(encoding);
            progressListener.setSession(request.getSession());
            fileUpload.setProgressListener(progressListener);
            try{
                List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
                return parseFileItems(fileItems, encoding);
            }catch(FileUploadBase.SizeLimitExceededException ex){
                throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
            }catch (FileUploadException e) {
                throw new MultipartException("异常",e);
            }
        }

}
