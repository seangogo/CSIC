<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>订单费用分配</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body>
<!-- basic styles -->

<!--[if IE 7]>
<link rel="stylesheet" href="${ctx}/static/bootstrap/css/font-awesome-ie7.min.css"/>
<![endif]-->
<!-- page specific plugin styles -->
<!-- fonts -->
<!--<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />-->
<!-- ace styles -->

<!--[if lte IE 8]>
<link rel="stylesheet" href="${ctx}/static/ace/assets/css/ace-ie.css"/>
<![endif]-->
<!-- inline styles related to this page -->
<!-- ace settings handler -->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="${ctx}/static/ace/assets/js/html5shiv.js"></script>
<script src="${ctx}/static/ace/assets/js/respond.js"></script>
<![endif]-->
<link rel="stylesheet" href="${ctx}/static/css/style.css"/>
<div class="fenpeipage">
    <!--header-->
    <div class="page-content head col-xs-12">
        <div class="page-header">
            <h1>
                订单分配
            </h1>
        </div>
    </div>
    <!--header-->
    <div class="row" style="margin-bottom: 10px; margin-left: 10px; margin-right:10px;">	
		<div class="col-md-12 col-xs-12">
			<div class="btn-group">
				<button data-toggle="dropdown" class="btn btn-primary btn-white dropdown-toggle">点击选择所需分配的订单<i class="ace-icon fa fa-angle-down icon-on-right"></i></button>
				<ul class="dropdown-menu menulist" id="account">
					<c:forEach var="order" items="${orderList}">
                        <li role="presentation" data-orderId="${order.id}"><a href="#">${order.orderNum}</a></li>
                    </c:forEach>
				</ul>
			</div>
		</div>
	</div>
    

    <div class="col-md-12 col-xs-12">
        <!--page-content-->
        <div id="mainContent">
		<span style="display: block;">
		<div class="col-md-7" style="overflow:hidden;padding-bottom: 10px;">
			 <div class="col-xs-12 label label-lg label-success arrowed-in arrowed-right">
                    <b>订单详情</b>
             </div>
			<div class="widget-box rightcontent">
			  <div class="goodsinfo">
			  	<div class="row tablerow1">
			  		<div class="col-xs-12">
			  			<ul class="list-unstyled spaced2 listdetail">
		                    <li>
		                      <i class="ace-icon fa fa-caret-right green"></i>订单编号：<span></span>
		                      
		                    </li>
		                    
		                    <li>
		                       <i class="ace-icon fa fa-caret-right green"></i>总金额：<span></span>
		                    </li>
		                     <li>
		                        <i class="ace-icon fa fa-caret-right green"></i>订单类型：<span></span>
		                    </li>
		                    <li>
		                          <i class="ace-icon fa fa-caret-right green"></i>接单人：<span></span>
		                    </li>
		                    <li>
		                        <i class="ace-icon fa fa-caret-right green"></i>联系人：<span></span>
		                    </li>
		                    <li>
		                         <i class="ace-icon fa fa-caret-right green"></i>
		                        联系方式：<span></span>
		                    </li>
		                     <li>
		                       <i class="ace-icon fa fa-caret-right green"></i>
		                        订单状态：<span></span>
		               		 </li>
		               		<li class="ctime">
		                        <i class="ace-icon fa fa-caret-right green"></i>创建时间：<span></span>
		                    </li> 
		               		 
		                    <li class="address">
		                         <i class="ace-icon fa fa-caret-right green"></i>
		                        联系人地址：<span></span>
		                    </li>
		                    
		                	 <li class="address">
		                           <i class="ace-icon fa fa-caret-right green"></i>
		                        描述：<span></span>
		                  </li>
		                 
		                   </ul> 
		           </div> 
			  	</div>
			  	<div class="row tablerow"style="padding-top:0px;">
					<div class="col-xs-12">
						<div class="tabbable">
							<ul class="nav nav-tabs" id="myTab3">
							</ul>
							<div class="tab-content">
								<div id="home3" class="tab-pane in active">
									<div class="page-content rtable">
										<div class="row">
											<div class="col-xs-12">
												<div class="table-responsive paytable">
													<table id="sample-table-1"
                                                           class="table table-striped table-bordered table-hover">
														<thead>
															<tr>
																<th class="personName">分配人员</th>
																<th>分配金额</th>
															</tr>
														</thead>
														<tbody id="payinput10">
														</tbody>
														<thead>
															<tr>
																<th class="personName">总金额</th>
																<th>保存信息</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td id="sumMoney10" class="personName "></td>
																<td>
																	<button class="btn icon-ok bigger-90 btn-primary"
                                                                            id="btn1" onclick="saveCostToLeft()">&nbsp;&nbsp;保存</button>
																</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			  
			<!-- /row -->
			</div>
		</div>
		</div>
		<div class="col-md-5">
			<div class="col-xs-12 label label-lg label-warning arrowed-in arrowed-right ">
                    <b>支付信息</b>
          </div>
		
		  <div class="payinfo">
		  	<div class="righttitle"></div>
		  	<div class="underpay">

		  </div>
			<button class="btn btn-block btn-warning icon-shopping-cart " id="surePay">&nbsp;&nbsp;确认支付</button>
		  </div>
		</div>
			</div>
		</span>
        </div>
    </div>
</div>

<script src="${ctx}/static/jquery/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common.js"></script>
<script type="text/javascript">
    var this_ = {
        v: {
            id: "this_",
            list: [],
            dTable: null,
            dataIndex: 0,
            totalMoney: 0,
            orderId: 0,
            orderTypes: ""
        },
        fn: {
            init: function () {
                //给每个订单号绑定点击事件
                $("#account li").on("click", function () {
                    this_.fn.ajaxOrderDetail($(this).data("orderid"))
                });
                $("#surePay").on("click", function () {
                    this_.fn.saveCost();
                });
                //页面加载时默认选中第一个(必须放在最后,等前面的事件加载完以后)
                $("#account li :first").trigger("click");
            },
            ajaxOrderDetail: function (id) {
                var id = id;
                this_.v.orderId = id;
                $.ajax({
                    type: 'post', url: "/serviceapp/order/ajaxOrderDetail", data: {orderId: id},
                    dataType: "json",
                    success: function (result) {
                        if (result.c == "1") {
                            //console.log(result);
                            //var orderNum = result.o.orderNum;//订单编号
                           // var createTime = result.o.createTime;//创建时间
							var ajaxOrderObj=result.o//订单对象
                            var totalPrice = 0;
                            var navHtml = '';//费用导航
                            $.each(result.o.orderCostList, function (index, item) {
                                navHtml += "<li data-cost=" + item.cost + " ><a data-toggle='tab' href='#home3'><i class='blue icon-dollar bigger-110'></i>" + item.costType.costName + "</a></li>";
                                totalPrice += item.cost;
                            });
                            this_.v.totalMoney = totalPrice;
                            var payUser = "";
                            /*相关联系人*/
                            $("#payinput10").html("");
                            $.each(result.o.orderEngineerList, function (index, item) {
                                $("#payinput10").append("<tr><td class='personName'>" + item[0] + "</td><td><input type='text' value='0'/><p>.00&nbsp;元</p><span class='text'></span></td></tr>");
                                payUser += "<li><i class='ace-icon fa fa-caret-right blue' data-userId=" + item[1] + "></i>支付" + item[0] + ":<span class='payMoneyData' data-userId=" + item[1] + ">0</span>元</li>"
                            });
                            /*支付信息*/
                            $(".underpay").html('');
                            $.each(result.o.orderCostList, function (index, item) {
                                var html = "<ul><li ><b>" + item.costType.costName + "：" + item.cost + "元</b></li><li><ul class='list-unstyled' data-costType=" + item.costType.id + ">" + payUser + "</ul></li></ul>";
                                $(".underpay").append(html)
                            });
                            $("#mainContent h5:eq(0)").text(ajaxOrderObj.orderNum + ":信息");
                             $(".tablerow1:first span:eq(0)").text(ajaxOrderObj.orderNum);
                            $(".payinfo div:first").html("订单编号:" + ajaxOrderObj.orderNum + "<br/>订单总金额：" + totalPrice + "元");
                            $(".tablerow1:first span:eq(1)").text(totalPrice);
                            $(".tablerow1:first span:eq(2)").text(ajaxOrderObj.orderExplain);
							$(".tablerow1:first span:eq(3)").text(ajaxOrderObj.commentContent);

							$(".tablerow1:first span:eq(4)").text(ajaxOrderObj.contactUser);
							$(".tablerow1:first span:eq(5)").text(ajaxOrderObj.contactPhone);
							$(".tablerow1:first span:eq(6)").text(ajaxOrderObj.ccIds);
							$(".tablerow1:first span:eq(7)").text(ajaxOrderObj.createTime);

							$(".tablerow1:first span:eq(8)").text(ajaxOrderObj.contactAddress);
							$(".tablerow1:first span:eq(9)").text(ajaxOrderObj.orderDesc);
							
							
							
                            $("#myTab3").html(navHtml);
                            $("#myTab3 li").on("click", function () {
                                $(this).addClass("active").siblings().removeClass("active");
                                this_.v.dataIndex = $(this).index();
                                /*if($("#btn1").hasClass("btn-primary")){
                                 //this_.fn.btnToggle();
                                 }*/
                                $("#sumMoney10").text($(this).data("cost"));
								$.each($("#payinput10 input"), function (index, item) {
									/*从右边的提交栏拿数据*/
									$(item).val($(".underpay").find(".list-unstyled").eq(this_.v.dataIndex).find(".payMoneyData").eq(index).text());
								});
                                $("#payinput10 input").on("blur", function () { //失去光标
                                    if (!/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test($(this).val())) {
                                        $(this).next('p').next('span').text('*请输入正确金额');
										$(this).val(0);
                                        return false
                                    } else {
                                        $(this).next('p').next('span').text('');
                                    }//验证金额
                                    var totalMoney = $("#sumMoney10").text();//总金额
                                    var surplusMoney = 0;//用户输入的金额
                                    var isAllZero = 0;//是否有为0的input
                                    $.each($("#payinput10 input"), function (index, item) {
                                        surplusMoney += $(item).val() - 0;
                                        if ($(item).val() == 0) {
                                            isAllZero++;
                                        }
                                    });
                                    if (surplusMoney > totalMoney || (surplusMoney < totalMoney && isAllZero == 0)) {//超出金额
                                        $(this).val(totalMoney - (surplusMoney - $(this).val()));
                                    }
                                    if (surplusMoney < totalMoney && isAllZero > 0) {
                                        $.each($("#payinput10 input"), function (index, item) {
                                            if ($(item).val() == 0) {
                                                $(item).val(totalMoney - surplusMoney);
                                                return false;
                                            }
                                        })
                                    }
                                    if (surplusMoney == totalMoney) {
                                        $("#payinput10 input").next("p").next("span").text('');
                                    }
                                })
                            });
                            $("#myTab3 li:eq(0)").trigger("click");
                        };
                    }
                });
            },
            btnToggle: function () {
                $("#btn1").toggleClass("btn-primary").toggleClass("disabled");
            },
            saveCost: function () {
                var orderId = this_.v.orderId;
                var orderTypes = "";
                var repairIds = "";
                var costs = "";
                var surePayCost = 0;
                $.each($(".list-unstyled"), function (index, item) {
                    orderTypes += $(item).data("costtype") + ",";
                })//费用类型
                $.each($(".underpay ul :eq(0) .payMoneyData"), function (index, item) {
                    repairIds += $(item).prev().data("userid") + ","
                });
                $.each($(".payMoneyData"), function (index, item) {
                    surePayCost += $(item).text() - 0;
                    costs += $(item).text() + ","
                })//费用
                if (surePayCost == this_.v.totalMoney) {
                    //提交数据
                    $.ajax({
                        type: 'post', url: "/serviceapp/order/saveCostEngineer", data: {
                            orderId: orderId, orderTypes: orderTypes, repairIds: repairIds, costs: costs
                        },
                        dataType: "json",
                        success: function (result) {
                            if (result.c == "1") {
                                location.reload();
								layer.alert(result.m);
                            }
                        }
                    })
                } else {
					layer.alert("金额还未分配完,请继续分配", {icon: 2, title: '警告'});
                    return false;
                }
            }
        }
	};;;
	function saveCostToLeft() {
        var indexBtn = this_.v.dataIndex;
        /*验证总金额*/
        var thisTotalMoney = 0;
        $.each($("#payinput10 input"), function (index, item) {
            thisTotalMoney += $(item).val() - 0;
        });
        if (thisTotalMoney != $("#sumMoney10").text()) {
			layer.alert("总价有误！", { icon : 2, title : '警告' });
            $("#payinput10 input").next("p").next("span").text("");
            return false;
        }
        $.each($("#payinput10 input"), function (index, item) {
            $(".underpay").find(".list-unstyled").eq(indexBtn).find(".payMoneyData").eq(index).text($(item).val());
        })
    }
    $(document).ready(function () {
        this_.fn.init();
    })
</script>
</body>
</html>