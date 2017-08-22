package com.easyRepair.api;

import com.alipay.api.AlipayApiException;
import com.easyRepair.api.ali.OrderInfoUtil2_0;
import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.OrderService;
import common.core.bean.Result;
import common.utils.ConfigUtil;
import common.utils.JsonUtil;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间：2016年11月2日 下午4:16:32
 *
 * @author andy
 * @version 2.2
 */
@Api(value = "/alipay", description = "支付宝支付接口")
@RestController
@RequestMapping("/api/alipay")
public class AliApiController {
    
    private static final Logger LOG = Logger.getLogger(AliApiController.class);
    @Autowired
    private OrderService orderService;
    /**
     * 支付下订单
     *
     * @param response
     *  支付金额
     *   商品id
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public void orderPay(HttpServletResponse response,
                         @RequestParam(required = false, defaultValue = "") String authInfoMapStr) throws AlipayApiException {
        Map<String, String> authInfoMap =JsonUtil.json2Obj(authInfoMapStr,HashMap.class);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, ConfigUtil.getString("alipay.privateKey"));
        final String authInfo = info + "&" + sign;
        HashMap<String,String> resultMap=new HashMap<>();
        resultMap.put("result",authInfo);
        WebUtil.printApi(response,new Result(true).data(resultMap));
    }
    
    
    
    /**
     * 订单支付微信服务器异步通知
     *
     * @param request
     */
    @RequestMapping(value = "/pay/notify", method = RequestMethod.POST)
    public String orderPayNotify(HttpServletRequest request) {
        try {
            return orderService.aliPayNotify(request)?"success":"failure";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}