package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.ImageUtilsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sean on 2017/2/17.
 */
@Controller
@RequestMapping("/utils")
public class UtilsController {
    /*上传图片返回服务器地址*/
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    @ResponseBody
    public void saveImage(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> imageMap=new HashMap();
        try {
            if(request instanceof MultipartHttpServletRequest) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                MultipartFile multipartFile = multipartRequest.getFile("file");
                if (multipartFile != null) {
                    imageMap.put("url",ImageUtilsService.getImages(request,multipartFile,false).get("imgURL").toString());
                     WebUtil.printJson(response,imageMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
