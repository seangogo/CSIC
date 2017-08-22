<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta charset="utf-8">
<meta name="description" content="with draggable and editable events">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
<title>IT工程师</title>
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
</style>
<script type="text/javascript">
	$("#toOrder").click(function(){
		$("#page-content").load("order/orderlist");
	})
	var str = sessionStorage.obj; 
	//重新转换为对象 
	var obj = JSON.parse(str);
	//console.log(obj);
	for (var key in obj) {
		if($("#"+key)){
			$("#"+key).html(obj[key]);
		}
		if (key == "orderInfo") {
			$("#orderPhone").html(obj[key].mobile);//电话
			$("#address").html(obj[key].address);//地址
			$("#createTime").html(obj[key].createTime);//发单时间
			$("#remark").html(obj[key].remark);//备注
		}
		if (key == "status") {//订单状态
			var arr=["已下单","已接单","预付款","确认支付","待评价","已完成","已取消"];
			$("#status").html(arr[obj[key]-1]);
		}
		if (key == "serviceType") {
			$("#serviceType").html(obj[key].serviceName);//服务类型
		}
		if (key == "user") {
			$("#userName").html(obj[key].userInfo.nickName);
			$("#userAddress").html(obj[key].userInfo.address);
			$("#userPhone").html(obj[key].loginName);
		}
		if (key == "repair"&&obj[key].userInfo!=null) {
			$("#repairUserName").html(obj[key].userInfo.nickName);
			$("#repairAddress").html(obj[key].userInfo.address);
			$("#repairPhone").html(obj[key].loginName);
		}
		if (key == "OrderImages"&&obj[key].length!=0) {
			for(var i=0;i<obj[key].length;i++){
				$("#photo").append("<li style='margin-left:20px'>"+
				"<img width='200' height='150' src="+obj[key][i].url+"></li>");
			}
		}
	}
	sessionStorage.clear;
</script>
</head>

<body class="no-skin" style="overflow-x: hidden;">
<div class="page-content">
	<div class="page-header">
	    <h1>
	        <a id="toOrder" style="cursor:pointer">订单管理</a>
	        <small style="cursor:default"><i class="ace-icon fa fa-angle-double-right"></i> 详情</small>
	    </h1>
	</div>
	
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
                       		  姓名：<span id="userName"></span>
                    </li>
                    <li>
                        <i class="ace-icon fa fa-caret-right blue"></i>
                      		  电话：<span id="userPhone"></span>
                    </li>
                       <li>
                        <i class="ace-icon fa fa-caret-right blue"></i>
                    		  地址：<span id="userAddress"></span>
                    </li>
                </ul>
            </div>
            
            <div class="row" style="margin-top: 40px;">
                <div class="col-xs-11 label label-lg label-info arrowed-in arrowed-right">
                    <b>接单人信息</b>
                </div>
            </div>
            <div>
                <ul class="list-unstyled spaced2">
                    <li>
                        <i class="ace-icon fa fa-caret-right blue"></i>
                     		   姓名：<span id="repairUserName"></span>
                    </li>
                    <li>
                        <i class="ace-icon fa fa-caret-right blue"></i>
                       		  电话：<span id="repairPhone"></span>
                    </li>
                    <li>
                        <i class="ace-icon fa fa-caret-right blue"></i>
                    		  地址：<span id="repairAddress"></span>
                    </li>
                </ul>
            </div>
            
            <div class="row" style="margin-top: 40px;">
                <div class="col-xs-11 label label-lg label-info arrowed-in arrowed-right">
                    <b>订单状态</b>
                </div>
            </div>
    <!--         <div class="timeline-container">
                <div class="timeline-items">
                      <div class="timeline-item clearfix">
                          <div class="timeline-info"></div>
                          <div class="widget-box transparent">
                              <div class="widget-header widget-header-small">
                                  <h5 class="widget-title smaller">
                                      <span class="grey">指派工程师</span>
                                  </h5>
                                  <span class="widget-toolbar no-border">
                                   	  <i class="ace-icon fa fa-clock-o bigger-110"></i> 2017-02-24 14:50
						  		  </span>
                              </div>
                              <div class="widget-body">
                                  <div class="widget-main">接单人：领导测试</div>
                              </div>
                          </div>
                      </div>
                </div>
            </div> -->
        </div>
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
                  		订单编号：<span id="orderNum"></span>
              </li>
              <li>
                  <i class="ace-icon fa fa-caret-right green"></i>
                		发布时间：<span id="createTime"></span>
              </li>
              <li>
                  <i class="ace-icon fa fa-caret-right green"></i>
                		预约时间：<span id="appointmentTime"></span>
              </li>
              <li>
                  <i class="ace-icon fa fa-caret-right green"></i>
                 		服务类型： <span id="serviceType"></span>
              </li>
              
              <li>
                  <i class="ace-icon fa fa-caret-right green"></i>
                		订单状态：<span id="status"></span>
              </li>
              
              <li>
                  <i class="ace-icon fa fa-caret-right green"></i>
                		订单联系电话：<span id="orderPhone"></span>
              </li>
              
              <li>
                  <i class="ace-icon fa fa-caret-right green"></i>
              		  	备注：<span id="remark"></span>
              </li>
              <li>
                  <i class="ace-icon fa fa-caret-right green"></i>
              		          定位上门地址：<span id="address"></span>
              </li>
              <li>
               	   <i class="ace-icon fa fa-caret-right green"></i>
                  	  	图片：<span id="alt"></span>
		   	  </li>
		</ul>
		<ul class="ace-thumbnails clearfix" id="photo"></ul>
      <div class="row">
          <div class="col-xs-11 label label-lg label-success arrowed-in arrowed-right">
              <b>订单费用</b>
          </div>
      </div>
        <ul class="list-unstyled spaced2">
            <li>
                <i class="ace-icon fa fa-caret-right green"></i>
                  	 上门费：<span id="expenses"></span>
            </li>
        </ul>
        <ul class="list-unstyled spaced2">
            <li>
                <i class="ace-icon fa fa-caret-right green"></i>
                  	 优惠卷抵扣金额：<span id="disCount"></span>
            </li>
        </ul>
        <ul class="list-unstyled spaced2">
            <li>
                <i class="ace-icon fa fa-caret-right green"></i>
                  	 积分抵扣金额：<span id="disScore"></span>
            </li>
        </ul>
          <ul class="list-unstyled spaced2">
            <li>
                <i class="ace-icon fa fa-caret-right green"></i>
                 	 订单应付金额 ：<span id="price"></span>
            </li>
        </ul>
        <ul class="list-unstyled spaced2">
            <li>
                <i class="ace-icon fa fa-caret-right green"></i>
                  	 实付金额：<span id="realpay"></span>
            </li>
        </ul>
      </div>
       </div>
    </div>
	</div>
</div>
</body>
</html>