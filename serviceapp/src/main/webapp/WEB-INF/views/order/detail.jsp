<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>订单管理</title>
</head>
<body>
<style>
    .order-detail table td {
        padding: 5px 0px 5px 0px;
    }

    .order-detail table tr td:first-child {
        width: 70px;
    }

    .order-detail table {
        width: 96%;
        margin-left: 10px;
    }

    .order-detail .timeline-container {
        width: 96%;
    }

    .order-detail .widget-main .col-xs-9 {
        padding: 0px;
    }

    .order-detail .buttom-button {
        width: 95%;
        text-align: center;
        margin-top: 35px;
    }

    .order-detail .star {
        font-size: 1.5em;
    }

		.selectbtn {
		    display: inline-block;
		    margin-bottom: 0;
		    font-weight: normal;
		    text-align: center;
		    vertical-align: middle;
		    touch-action: manipulation;
		    cursor: pointer;
		    background-image: none;
		    border: 1px solid transparent;
		    white-space: nowrap;
		    padding: 6px 2px;
		    font-size: 14px;
		    line-height: 1.42857143;
		    border-radius: 0px;
		    -webkit-user-select: none;
		    -moz-user-select: none;
		    -ms-user-select: none;
		    user-select: none;
		}
	
    /*#js_multiselect_to_1{
        background-color:rgb(189, 171, 195);
    }
    #js_multiselect_to_1 option{
        color:#f0f6e4;
    }*/
</style>
<div class="page-header">
    <h1>
        <a href="${ctx}/order/list">订单管理</a>
        <small><i class="ace-icon fa fa-angle-double-right"></i> 详情
        </small>
    </h1>
</div>
<c:if test="${not empty message}">
    <div id="message" class="alert alert-success">
        <button data-dismiss="alert" class="close">×</button>
            ${message}</div>
</c:if>
<div class="col-sm-12 order-detail">
    <div class="row">
        <div class="col-sm-4">
            <div class="row">
                <div class="col-xs-11 label label-lg label-info arrowed-in arrowed-right">
                    <b>发单人信息</b>
                </div>
            </div>
            <div>
                <ul class="list-unstyled spaced2">
                    <li>
                        <i class="ace-icon fa fa-caret-right blue"></i>
                        姓名：${order.user.userName}
                    </li>
                    <li>
                        <i class="ace-icon fa fa-caret-right blue"></i>
                        电话：${order.user.loginName}
                    </li>
                </ul>
            </div>
            <div class="row" style="margin-top: 20px;">
                <div class="col-xs-11 label label-lg label-info arrowed-in arrowed-right">
                    <b>联系人信息</b>
                </div>
            </div>
            <div>
                <ul class="list-unstyled spaced2">
                    <li>
                        <i class="ace-icon fa fa-caret-right blue"></i>
                        姓名：${order.contactUser}
                    </li>
                    <li>
                        <i class="ace-icon fa fa-caret-right blue"></i>
                        电话：${order.contactPhone}
                    </li>
                    <li>
                        <i class="ace-icon fa fa-caret-right blue"></i>
                        地址：${order.contactAddress}
                    </li>
                </ul>
            </div>
            <c:if test="${not empty fileList}">
                <div class="row" style="margin-top: 20px;">
                    <div class="col-xs-11 label label-lg label-info arrowed-in arrowed-right">
                        <b>订单附件</b>
                    </div>
                </div>
                <div>
                    <ul class="list-unstyled spaced2">

                        <c:forEach items="${fileList}" var="file">
                            <li>
                                <i class="ace-icon fa fa-paperclip"
                                   style="cursor: pointer;pointer-events: auto;margin-left: 10px;"></i>
                                <span style="width: 100px">${file.docName}</span>
                                <a class="fa fa-download " href="${ctx}/order/upload?path=${file.docPath}"
                                   style="cursor: pointer;pointer-events: auto;margin-left: 10px;"></a>
                            </li>
                        </c:forEach>


                    </ul>
                </div>
            </c:if>
            <div class="row" style="margin-top: 20px;">
                <div class="col-xs-11 label label-lg label-info arrowed-in arrowed-right">
                    <b>订单状态</b>
                </div>
            </div>
            <div>
                <div class="timeline-container">
                    <div class="timeline-items">
                        <!-- /section:pages/timeline.item -->

                        <c:forEach items="${orderStates}" var="orderState">
                            <div class="timeline-item clearfix">
                                <div class="timeline-info">
                                    <img alt="Susan't Avatar"
                                         src="${ctx}/${orderState.creater.userIco}"
                                         onerror="this.src='${ctx}/static/ace/assets/avatars/avatar2.png'">
                                </div>
                                <c:if test="${orderState.msgContent == null }">
                                    <div class="widget-box transparent">
                                        <div class="widget-main clearfix">
                                                ${orderState.msgTypeStr }
                                            <div class=" pull-right">
                                                <i class="ace-icon fa fa-clock-o bigger-110"></i>
                                                <fmt:formatDate value="${orderState.createTime}"
                                                                pattern="yyyy-MM-dd HH:mm"/>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${orderState.msgContent != null}">
                                    <div class="widget-box transparent">
                                        <div class="widget-header widget-header-small">
                                            <h5 class="widget-title smaller">
                                                <span class="grey">${orderState.msgTypeStr}</span>
                                            </h5>
                                            <span class="widget-toolbar no-border"> <i
                                                    class="ace-icon fa fa-clock-o bigger-110"></i> <fmt:formatDate
                                                    value="${orderState.createTime}"
                                                    pattern="yyyy-MM-dd HH:mm"/>
												</span>
                                        </div>

                                        <div class="widget-body">
                                            <c:if test="${orderState.msgType == 3}">
                                                <div class="widget-main" style="padding-bottom: 0px;">
                                                    <i class="star star1 star-off-png "></i> <i
                                                        class="star star2 star-off-png "></i> <i
                                                        class="star star3 star-off-png "></i> <i
                                                        class="star star4 star-off-png "></i> <i
                                                        class="star star5 star-off-png "></i>
                                                    <script>
                                                        var star = '${ orderState.commentStar}';
                                                        for (var i = 1; i <= star; i++) {
                                                            $('.star' + i).removeClass('star-off-png').addClass('star-on-png');
                                                        }
                                                    </script>
                                                </div>
                                            </c:if>
                                            <div class="widget-main">${ orderState.msgContent}</div>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </div>
                    <!-- /.timeline-items -->
                </div>
            </div>
        </div>
        <!-- /.col -->

        <div class="col-sm-8">
            <div class="row">
                <div class="col-xs-11 label label-lg label-success arrowed-in arrowed-right">
                    <b>订单详情</b>
                </div>
            </div>

            <div>
                <ul class="list-unstyled spaced2">
                    <li>
                        <i class="ace-icon fa fa-caret-right green"></i>
                        订单编号：${order.orderNum }
                    </li>
                    <li>
                        <i class="ace-icon fa fa-caret-right green"></i>
                        发布时间：<fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </li>
                    <li>
                        <i class="ace-icon fa fa-caret-right green"></i>
                        预约时间：<fmt:formatDate value="${order.appointmentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </li>
                    <li>
                        <i class="ace-icon fa fa-caret-right green"></i>
                        服务类型：<c:if test="${order.repairType == 0}">设备报修</c:if> <c:if
                            test="${order.repairType == 1}">安装实施</c:if>
                    </li>
                    <c:if test="${order.repairType == 0}">
                        <li><i class="ace-icon fa fa-caret-right green"></i> 台数：${order.qty}
                        </li>
                    </c:if>
                    <li>
                        <i class="ace-icon fa fa-caret-right green"></i>
                        订单状态：<c:if test="${order.orderState == 0}">
                        <span style="color:red">待处理</span>
                    </c:if> <c:if test="${order.orderState == 1}">
                        <span style="color:green">已接单</span>
                    </c:if> <c:if test="${order.orderState == 2}">已完成</c:if> <c:if
                            test="${order.orderState == 3}">
                        <span style="color:#aaaaaa">已取消</span>
                    </c:if> <c:if test="${order.orderState == 4}">
                        <span style="color:orange">已评价</span>
                    </c:if>
                        <c:if test="${order.orderState == 5}">
                            <span style="color:green">已付款</span>
                        </c:if>
                    </li>
                    <c:if test="${not empty order.repair}">
                        <li><i class="ace-icon fa fa-caret-right green"></i> 接单人：
                                ${order.repair.userName}&nbsp;&nbsp;&nbsp;&nbsp;
                                ${order.repair.loginName}
                        </li>
                    </c:if>
                    <%--<c:if test="${not empty order.repair}">--%>
                    <c:if test="${orderEngineer != undefined}">
                        <li><i class="ace-icon fa fa-caret-right green"></i> 相关人：
                            <c:forEach items="${orderEngineer}" var="oeList">
                                ${oeList}
                            </c:forEach>
                        </li>
                    </c:if>
                    <li>
                        <i class="ace-icon fa fa-caret-right green"></i>
                        描述：${order.orderDesc}
                    </li>
                    <c:if test="${order.orderState == 2 || order.orderState == 4}">
                        <li><i class="ace-icon fa fa-caret-right green"></i> 处理说明：${order.orderExplain}
                        </li>
                    </c:if>
                    <c:if test="${not empty order.orderImgsThumb && not empty order.orderImgs}">
                        <li><i class="ace-icon fa fa-caret-right green"></i> 图片：
                            <tags:orderimg imgsThumb="${order.orderImgsThumb}" imgs="${order.orderImgs}"/>
                        </li>
                    </c:if>
                </ul>
                <c:if test="${not empty orderCost}">
                    <div class="row">
                        <div class="col-xs-11 label label-lg label-success arrowed-in arrowed-right">
                            <b>订单费用</b>
                        </div>
                    </div>
                    <c:forEach items="${orderCost}" var="orderCost">
                        <ul class="list-unstyled spaced2">
                            <li>
                                <i class="ace-icon fa fa-caret-right green"></i>
                                    ${orderCost.costType.costName}：${orderCost.cost}
                            </li>
                        </ul>
                    </c:forEach>
                </c:if>
                <div class="buttom-button">
                    <shiro:hasPermission name="order:assign">
                        <c:if test="${not empty currentUser && (currentUser.userType==2 || currentUser.userType==3) && (order.orderState==0 || order.orderState==1)}">
                            <button class="btn btn-primary " onclick="showSelectRepairView()">
                                分配<i class="ace-icon fa fa-arrow-right icon-on-right"></i>
                            </button>
                        </c:if>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="order:accept">
                        <c:if test="${not empty currentUser && (currentUser.userType==1) && (order.orderState==0)}">
                            <button class="btn btn-primary " onclick="acceptOrder()">
                                &nbsp;接单&nbsp;</button>
                        </c:if>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="order:complete">
                        <c:if test="${not empty currentUser && (order.orderState==1) && currentUser.id == order.repair.id }">
                            <button class="btn btn-primary "
                                    onclick="$('#completeOrderView').modal('show');">
                                &nbsp;完成订单&nbsp;</button>
                        </c:if>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="order:comment">
                        <c:if test="${not empty currentUser && (order.orderState== 2) && currentUser.id == order.user.id }">
                            <button class="btn btn-primary "
                                    onclick="$('#commentOrderView').modal('show');">
                                &nbsp;评价订单&nbsp;</button>
                        </c:if>
                    </shiro:hasPermission>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="selectRepairView" tabindex="-1"
         role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <form class="form-horizontal" method="post" action="" role="form">
                    <div class="modal-header">
                        <h4 class="modal-title text-center" id="myModalLabels">分配工程师</h4>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" id="orderId" name="orderId"
                               value="${order.id}"/>
                        <div class="form-group">
                            <label for="repairId" class="col-lg-2 control-label">工程师</label>
                            <div class="col-lg-6">
                                <select class="selectpicker" name="repairId"
                                        id="repairId">
                                </select>
                            </div>
                        </div>
                        <div class="form-group" id="addCModal">
                            <div class="col-md-5">
                                <select id="multiselect" name="from"
                                        class="js-multiselect form-control" size="8" multiple="multiple">
                                    <c:if test="${not empty repairCCList}">
                                        <c:forEach items="${repairCCList}" var="repairCC">
                                            <option value="${repairCC.repairId}">${repairCC.repairName}(${repairCC.repairStateStr})</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                                <span style="color: #92a3ac">ctrl+左键</span>
                            </div>
                            <div class="col-md-2" style="padding-bottom: 10px; overflow: hidden;">
                            	<div class="col-xs-3 col-md-12" style="overflow: hidden; margin-bottom: 10px;">
                                <button type="button" id="js_right_All_1" class="btn btn-block btn-primary selectbtn"><i
                                        class="glyphicon glyphicon-forward hidden-xs"></i>全选
                                </button>
                                </div>
                                <div class="col-xs-3 col-md-12" style="overflow: hidden; margin-bottom: 10px;">
                                <button type="button" id="js_right_Selected_1" class="btn btn-block btn-success selectbtn"><i
                                        class="glyphicon glyphicon-chevron-right hidden-xs"></i>添加
                                </button>
                                </div>
                                <div class="col-xs-3 col-md-12" style="overflow: hidden; margin-bottom: 10px;">
                                <button type="button" id="js_left_Selected_1" class="btn btn-block btn-warning selectbtn"><i
                                        class="glyphicon glyphicon-chevron-left hidden-xs"></i>移出
                                </button>
                                </div>
                                <div class="col-xs-3 col-md-12" style="overflow: hidden; margin-bottom: 10px;">
                                <button type="button" id="js_left_All_1" class="btn btn-block btn-danger selectbtn"><i class="glyphicon glyphicon-backward hidden-xs"></i>清空
                                </button>
                                </div>
                            </div>
                            
                            <div class="col-md-5">
                                <select name="to" id="js_multiselect_to_1" class="form-control"
                                        size="8" multiple="multiple">
                                    <c:if test="${not empty repairCCListTo}">
                                        <c:forEach items="${repairCCListTo}" var="repairCCTo">
                                            <option value="${repairCCTo.repairId}">${repairCCTo.repairName}(${repairCCTo.repairStateStr})</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-info" type="button"
                                onclick="submitSelectRepairs()">
                            <i class="ace-icon fa fa-check bigger-110"></i> 确认
                        </button>
                        <button class="btn" type="reset" data-dismiss="modal">
                            <i class="ace-icon fa fa-undo bigger-110"></i> 返回
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="modal fade" id="completeOrderView" tabindex="-1"
         role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <form class="form-horizontal" method="post" action="" role="form">
                    <div class="modal-header">
                        <h4 class="modal-title text-center" id="myModalLabel">完成订单</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="orderExplain" class="col-lg-2 control-label">处理说明：</label>
                            <div class="col-lg-6">
									<textarea id="orderExplain" name="orderExplain"
                                              style="padding: 5px 4px; width: 440px; height: 140px;"
                                              class="form-control" placeholder="请输入订单处理说明"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-info" type="button"
                                onclick="completeOrder()">
                            <i class="ace-icon fa fa-check bigger-110"></i> 确认
                        </button>
                        <button class="btn" type="reset" data-dismiss="modal">
                            <i class="ace-icon fa fa-undo bigger-110"></i> 返回
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="modal fade" id="commentOrderView" tabindex="-1"
         role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <form class="form-horizontal" method="post" action="" role="form">
                    <div class="modal-header">
                        <h4 class="modal-title text-center" id="myModalLabel">评价订单</h4>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" value="5" id="commentStar"/>
                        <div class="form-group">
                            <label for="star-on-png" class="col-lg-2 control-label">星评：</label>
                            <div class="col-lg-6">
                                <div class="rating inline" style="cursor: pointer;" id="star-on-png">
                                    <i data-alt="1" class="star-1 my-star star-on-png"></i>&nbsp;<i
                                        data-alt="2" class="star-2 my-star star-on-png"></i>&nbsp;<i
                                        data-alt="3" class="star-3 my-star star-on-png"></i>&nbsp;<i
                                        data-alt="4" class="star-4 my-star star-on-png"></i>&nbsp;<i
                                        data-alt="5" class="star-5 my-star star-on-png"></i>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="commentContent" class="col-lg-2 control-label">评论：</label>
                            <div class="col-lg-6">
									<textarea id="commentContent" name="commentContent"
                                              style="padding: 5px 4px; width: 440px; height: 140px;"
                                              class="form-control" placeholder="请输入评论内容"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-info" type="button"
                                onclick="commentOrder()">
                            <i class="ace-icon fa fa-check bigger-110"></i> 确认
                        </button>
                        <button class="btn" type="reset" data-dismiss="modal">
                            <i class="ace-icon fa fa-undo bigger-110"></i> 返回
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>


</div>
<script src="${ctx}/static/ace/assets/js/jquery.colorbox.js"></script>
<script src="${ctx}/static/bootstrap/select/multiselect.min.js"></script>
<script type="text/javascript">
    jQuery(function ($) {
        var $overflow = '';
        var colorbox_params = {
            rel: 'colorbox',
            reposition: true,
            scalePhotos: true,
            scrolling: false,
            previous: '<i class="ace-icon fa fa-arrow-left"></i>',
            next: '<i class="ace-icon fa fa-arrow-right"></i>',
            close: '&times;',
            current: '{current} of {total}',
            maxWidth: '100%',
            maxHeight: '100%',
            onOpen: function () {
                $overflow = document.body.style.overflow;
                document.body.style.overflow = 'hidden';
            },
            onClosed: function () {
                document.body.style.overflow = $overflow;
            },
            onComplete: function () {
                $.colorbox.resize();
            }
        };

        $('.ace-thumbnails [data-rel="colorbox"]')
                .colorbox(colorbox_params);
        $("#cboxLoadingGraphic").html(
                "<i class='ace-icon fa fa-spinner orange fa-spin'></i>");//let's add a custom loading icon

        $(document).one('ajaxloadstart.page', function (e) {
            $('#colorbox, #cboxOverlay').remove();
        });


        //评星鼠标移入事件
        $('.my-star').mouseover(function () {
            var star = $(this).attr('data-alt');
            for (var i = 1; i <= 5; i++) {
                if (i <= star) {
                    $('.star-' + i).addClass('star-on-png').removeClass('star-off-png');
                } else {
                    $('.star-' + i).addClass('star-off-png').removeClass('star-on-png');
                }
            }
            $('#commentStar').val(star);
        });

        //评星点击事件
        $('.my-star').click(function () {
            var star = $(this).attr('data-alt');
            for (var i = 1; i <= 5; i++) {
                if (i <= star) {
                    $('.star-' + i).addClass('star-on-png').removeClass('star-off-png');
                } else {
                    $('.star-' + i).addClass('star-off-png').removeClass('star-on-png');
                }
            }
            $('#commentStar').val(star);
        });


    })
</script>

<script type="text/javascript">
    var allCCList =${repairCCListAll};
    jQuery(document).ready(function ($) {
        $('#multiselect').multiselect({
            right: '#js_multiselect_to_1',
            rightAll: '#js_right_All_1',
            rightSelected: '#js_right_Selected_1',
            leftSelected: '#js_left_Selected_1',
            leftAll: '#js_left_All_1'
        });
    });
    //评价订单
    function commentOrder() {
        var commentContent = $('#commentContent').val();
        if (commentContent.length > 300) {
            layer.alert("评论字数不能超过300", {
                icon: 2,
                title: '提示'
            });
            return;
        }
        var commentStar = $('#commentStar').val();
        layer.confirm("确认评价吗？", {
            icon: 3,
            title: '提示'
        }, function (index) {
            $.post('${ctx}/order/commentOrder', {
                orderId: "${order.id}",
                commentStar: commentStar,
                commentContent: commentContent
            }, function (data) {
                if (data) {
                    layer.alert(data, {
                        icon: 2,
                        title: '提示'
                    }, function (index) {
                        window.location.reload();
                        layer.close(index);
                    });
                } else {
                    window.location.reload();
                }
            });
            layer.close(index);
        });
    }
    //完成订单
    function completeOrder() {
        var orderExplain = $('#orderExplain').val();
        if (orderExplain.length > 300) {
            layer.alert("说明字数不能超过300", {
                icon: 2,
                title: '提示'
            });
            return;
        }
        layer.confirm("确认完成吗？", {
            icon: 3,
            title: '提示'
        }, function (index) {
            $.post('${ctx}/order/completeOrder', {
                orderId: "${order.id}",
                orderExplain: orderExplain
            }, function (data) {
                if (data) {
                    layer.alert(data, {
                        icon: 2,
                        title: '提示'
                    }, function (index) {
                        window.location.reload();
                        layer.close(index);
                    });
                } else {
                    window.location.reload();
                }
            });
            layer.close(index);
        });
    }
    //接单
    function acceptOrder() {
        layer.confirm("确认接单吗？", {
            icon: 3,
            title: '提示'
        }, function (index) {
            $.post('${ctx}/order/acceptOrder', {
                orderId: "${order.id}"
            }, function (data) {
                if (data) {
                    layer.alert(data, {
                        icon: 2,
                        title: '提示'
                    }, function (index) {
                        window.location.reload();
                        layer.close(index);
                    });
                } else {
                    window.location.reload();
                }
            });
            layer.close(index);
        });
    }
    //工程师列表--分配按钮
    function showSelectRepairView() {
        var repairList = ${repairList};
        var orderRepairId = '${order.repair.id}';
        var orderId = $("#orderId");
        var repairListHtml = "<option value='0'><- -请选择工程师- -></option>";
        for (var i = 0; i < repairList.length; i++) {
            var repair = repairList[i];
            repairListHtml += "<option value='" + repair['userId'] + "'>"
                    + repair['userName'] + "</option>"
        }
        /*修改输入框背景颜色*/
        $(".btn-default").css("cssText", "background-color:#BDABC3!important");
        $("#repairId").html(repairListHtml);
        $("#repairId").selectpicker("refresh");
        $("#repairId").selectpicker("val", orderRepairId)
        /*弹出框*/
        $('#selectRepairView').modal();
        /*绑定切换事件*/
        $("#repairId").on("change", function () {
            var html = "";
            if ($(this).val() != 0) {
                for (var i = 0; i < allCCList.length; i++) {
                    if (allCCList[i].repairId != $(this).val()) {
                        html += "<option value='" + allCCList[i].repairId + "'>" + allCCList[i].repairName + "(" + allCCList[i].repairStateStr + ")</option>";
                    }
                }
                $("#multiselect").html(html);
            } else {
                $("#multiselect").empty();
            }
            $("#js_multiselect_to_1").empty();
        })

      //  $("#repairId").trigger("change")
    }
    //相关人和工程师提交
    function submitSelectRepairs() {
        var orderId = $("#orderId").val();//订单ID
        var repairId = $("#repairId").val();//已选择工程师ID
        var repairIds = "";//相关人ID
        $.each($("#js_multiselect_to_1 option"), function (index, item) {
            repairIds += $(item).val() + ",";
        })
        if(repairId<=0){
            layer.alert("请选择工程师！");
            return false;
        }
        $.post('${ctx}/order/assignRepairs', {
            orderId: orderId,
            repairId: repairId,
            repairIds: repairIds
        }, function (data) {
            if (data) {
                layer.alert(data, {
                    icon: 2,
                    title: '提示'
                }, function (index) {
                    window.location.reload();
                    layer.close(index);
                });
            } else {
                window.location.reload();
            }

        });
    }
</script>
</body>
</html>
