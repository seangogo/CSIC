package com.easyRepair.service;

import com.easyRepair.entityUtils.ImageUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/12/1. */
public class ImageUtilsService {
    public static List<Map<String, Object>> getImages(HttpServletRequest request, List<MultipartFile> photos, boolean isThu) {
        List<Map<String, Object>> imagesList = new ArrayList<Map<String, Object>>();
        for(MultipartFile photo : photos) {
            if(photo != null && photo.getOriginalFilename().toLowerCase().trim().length() != 0) {
                Map<String, Object> imagesMap = null;
                try {
                    imagesMap = ImageUtils.saveImage(request, photo, isThu, "webapp/uploadFiles/");
                    imagesList.add(imagesMap);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return imagesList;
    }
    
    public static Map<String, Object> getImages(HttpServletRequest request, MultipartFile photo, boolean isThu) {
        Map<String, Object> imagesMap = null;
        if(photo != null && photo.getOriginalFilename().toLowerCase().trim().length() != 0) {
            try {
                imagesMap = ImageUtils.saveImage(request, photo, isThu, "uploadFiles");
            } catch(Exception e) {
                e.printStackTrace();
            }
        
        }
        return imagesMap;
    }
}
