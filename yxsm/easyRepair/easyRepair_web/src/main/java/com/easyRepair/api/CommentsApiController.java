package com.easyRepair.api;

/**
 Created by sean on 2017/1/5. */

import com.easyRepair.commons.WebUtil;
import com.easyRepair.entityUtils.ApiBeanUtils;
import com.easyRepair.entityUtils.SessionUtils;
import com.easyRepair.pojo.CommentsMudule;
import com.easyRepair.pojo.UserSessionModul;
import com.easyRepair.service.CommentsImageService;
import com.easyRepair.service.CommentsService;
import com.easyRepair.service.ImageUtilsService;
import com.easyRepair.service.OrderService;
import com.easyRepair.tabEntity.Comments;
import com.easyRepair.tabEntity.CommentsImage;
import com.easyRepair.tabEntity.Order;
import common.core.EASY_ERROR_CODE;
import common.core.bean.Result;
import common.utils.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/11/24. */
@Api(value = "/comments", description = "订单评论的相关接口")
@RestController
@RequestMapping(value = "/api/comments")
public class CommentsApiController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CommentsService commentsService;
    @Autowired
    private CommentsImageService commentsImageService;
    
    /*评论订单{"content":"内容",service_star:"1","technology_star":"2","speed_star":"3"}*/
    @RequestMapping("comments/{id}/order")
    @ApiOperation(notes = "评论订单：用户端", httpMethod = "POST", value = "评论订单")
    public void commentsOrder(HttpServletRequest request,
                              @PathVariable(value = "id") Long id,
                              @ApiParam(name = "commentsStr", value = "评论对象的JSon字符串")
                              @RequestParam(value = "commentsStr") String commentsStr,
                              HttpServletResponse response
    ) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        /*验证参数*/
        Order order = orderService.findByIdAndUser_Id(id, userSessionModul.getId());
        if (order == null || !order.getStatus().equals(4)) {
            WebUtil.printApi(response, new Result(false).msg("数据异常").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        Comments comments = JsonUtil.json2Obj(commentsStr, Comments.class);
        Comments commentsSave = new Comments(comments.getContent(), order, false, new Date(),
                comments.getService_star(), comments.getSpeed_star(), comments.getTechnology_star());
        commentsSave = commentsService.create(commentsSave);
        if(request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> photos = multipartRequest.getFiles("photos");
           /*评论图片*/
            List<Map<String, Object>> imagesList = ImageUtilsService.getImages(request, photos, false);
            for(Map<String, Object> map : imagesList) {
                CommentsImage commentsImage = new CommentsImage(map.get("imgURL").toString(), commentsSave);
                commentsImageService.create(commentsImage);
            }
        }
        /*修改订单状态*/
        order.setStatus(5);
        order.getOrderInfo().setComment(true);//todo 添加积分
        orderService.update(order);
        WebUtil.printApi(response, new Result(true).msg("恭喜评价成功！获取30积分"));//TODO
    }
    
    @RequestMapping("see/{orderId}/comments")
    @ApiOperation(notes = "查看评论", httpMethod = "GET", value = "查看评论")
    public void getCommentByOrderId(@PathVariable(value = "orderId") Long orderId,
                              HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        Comments comments=commentsService.findByOrder_Id(orderId);
        if((userSessionModul.getType().equals("0")&&comments!=null&&comments.getOrder().getUser().getId()!=userSessionModul.getId())||
                (userSessionModul.getType().equals("1")&&comments.getOrder().getRepair().getId()!=userSessionModul.getId())){
            WebUtil.printApi(response,new Result(false).data("数据异常").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        if(comments!=null){
            WebUtil.printApi(response,new Result(false).data(ApiBeanUtils.getCommentsModel(comments)));
        }
    }
    
    
}
