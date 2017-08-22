package com.easyRepair.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by sean on 2017/1/9.
 */
@Api(value = "/WeiXinPay", description = "微信支付的相关接口")
@RestController
@RequestMapping(value = "/api/weiXinPay")
public class WeiXinPayApiController {

    //*统一预下单*//*
    @RequestMapping("pay/test/user")
    @ApiOperation(notes = "统一预下单", httpMethod = "POST", value = "统一预下单")
    public void payExpenses(HttpServletRequest request,
                            HttpServletResponse response) {

    }


    //*预下单回调*//*
    @RequestMapping("pay/callback/user")
    @ApiOperation(notes = "预下单回调", httpMethod = "POST", value = "预下单回调")
    public void Paycallback(HttpServletRequest request,
                            HttpServletResponse response) { //生成订单
        DataInputStream in;
        String wxNotifyXml = "";
        try {
            in = new DataInputStream(request.getInputStream());
            byte[] dataOrigin = new byte[request.getContentLength()];
            in.readFully(dataOrigin); // 根据长度，将消息实体的内容读入字节数组dataOrigin中
            if (null != in) in.close(); // 关闭数据流
            wxNotifyXml = new String(dataOrigin); // 从字节数组中得到表示实体的字符串
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
