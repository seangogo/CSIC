<%--
  Created by IntelliJ IDEA.
  User: Coolkid
  Date: 2016/11/14
  Time: 上午 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>Home</title>
    <meta name="description" content="home"/>
    <%@ include file="../inc/meta.jsp" %>
    <%--<link rel="stylesheet" type="text/css" href="http://cdn.datatables.net/1.10.13/css/jquery.dataTables.css">--%>
</head>

<body class="no-skin">
<%@ include file="../inc/nav.jsp" %>

<div class="main-container ace-save-state" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.loadState('main-container')
        } catch (e) {
        }
    </script>
    <%@ include file="../inc/menu.jsp" %>

    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="/index">Home</a>
                    </li>
                    <li class="active">管理员列表</li>
                </ul><!-- /.breadcrumb -->

                <div class="nav-search" id="nav-search">
                    <form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input"
                                           id="nav-search-input" autocomplete="off"/>
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
                    </form>
                </div><!-- /.nav-search -->
            </div>
            <div class="page-content">
                <div class="page-header">
                    <h1>
                        表
                        <small>
                            <i class="ace-icon fa fa-angle-double-right"></i>
                            Static &amp; Dynamic Tables
                        </small>
                    </h1>
                </div><!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">
                        <div class="row">
                            <div class="col-xs-12">
                                <h3 class="header smaller lighter blue">jQuery dataTables</h3>
                                

                                <!-- div.table-responsive -->
                                <!-- div.dataTables_borderWrap -->
                                <div>
                                    <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <div class="dataTables_length" id="dynamic-table_length"><label>Display
                                                    <select name="dynamic-table_length" aria-controls="dynamic-table"
                                                            class="form-control input-sm">
                                                        <option value="10">10</option>
                                                        <option value="25">25</option>
                                                        <option value="50">50</option>
                                                        <option value="100">100</option>
                                                    </select> records</label></div>
                                            </div>
                                            <div class="col-xs-6">
                                                <div id="dynamic-table_filter" class="dataTables_filter">
                                                    <label>Search:<input type="search" class="form-control input-sm"
                                                                         placeholder=""
                                                                         aria-controls="dynamic-table"></label></div>
                                            </div>
                                        </div>
                                        <form id="userForm">
                                            <table id="dynamic-table"
                                                   class="table table-striped table-bordered table-hover dataTable no-footer"
                                                   role="grid" aria-describedby="dynamic-table_info">
                                                <thead>
                                                <tr role="row">
                                                    <th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                                                        <label class="pos-rel">
                                                            <input type="checkbox" class="ace">
                                                            <span class="lbl"></span>
                                                        </label>
                                                    </th>
                                                    <th class="sorting" tabindex="0" aria-controls="dynamic-table"
                                                        rowspan="1" colspan="1"
                                                        aria-label="Domain: activate to sort column ascending">登录名
                                                    </th>
                                                    <th class="sorting" tabindex="0" aria-controls="dynamic-table"
                                                        rowspan="1" colspan="1"
                                                        aria-label="Price: activate to sort column ascending">用户名
                                                    </th>
                                                    <th class="hidden-480 sorting" tabindex="0"
                                                        aria-controls="dynamic-table" rowspan="1" colspan="1"
                                                        aria-label="Clicks: activate to sort column ascending">身份
                                                    </th>

                                                    <th class="hidden-480 sorting" tabindex="0"
                                                        aria-controls="dynamic-table" rowspan="1" colspan="1"
                                                        aria-label="Clicks: activate to sort column ascending">公司
                                                    </th>
                                                    <th class="hidden-480 sorting" tabindex="0"
                                                        aria-controls="dynamic-table" rowspan="1" colspan="1"
                                                        aria-label="Clicks: activate to sort column ascending">邮箱
                                                    </th>
                                                    <th class="hidden-480 sorting" tabindex="0"
                                                        aria-controls="dynamic-table" rowspan="1" colspan="1"
                                                        aria-label="Clicks: activate to sort column ascending">在线状态
                                                    </th>
                                                    <th class="hidden-480 sorting" tabindex="0"
                                                        aria-controls="dynamic-table" rowspan="1" colspan="1"
                                                        aria-label="Clicks: activate to sort column ascending">注册时间
                                                    </th>
                                                    <th class="hidden-480 sorting" tabindex="0"
                                                        aria-controls="dynamic-table" rowspan="1" colspan="1"
                                                        aria-label="Clicks: activate to sort column ascending">管理
                                                    </th>
                                                    <%--<th class="sorting" tabindex="0" aria-controls="dynamic-table"
                                                        rowspan="1" colspan="1" aria-label="

                                                                Update
                                                            : activate to sort column ascending">
                                                        <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
                                                        公司
                                                    </th>
                                                    <th class="hidden-480 sorting_desc" tabindex="0"
                                                        aria-controls="dynamic-table" rowspan="1" colspan="1"
                                                        aria-label="Status: activate to sort column ascending"
                                                        aria-sort="descending">邮箱
                                                    </th>
                                                    <th class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>--%>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                    <tr role="row" class="odd">
                                                        <input type="hidden" value="" class="userId">
                                                        <td class="center">
                                                            <label class="pos-rel">
                                                                <input type="checkbox" class="ace">
                                                                <span class="lbl"></span>
                                                            </label>
                                                        </td>
                                                        <td class="">
                                                            <a href="#"></a>
                                                        </td>
                                                        <td class=""></td>
                                                        <td class=""></td>
                                                        <td class=""></td>
                                                        <td class=""></td>
                                                        <td class=""></td>

                                                            <%--<td class="hidden-480">1,400</td>
                                                            <td class="">Apr 04</td>
                                                            <td class="hidden-480 sorting_1">
                                                                <span class="label label-sm label-info arrowed arrowed-righ">Sold</span>
                                                            </td>--%>
                                                        <td class=""></td>
                                                        <td>
                                                            <div class="hidden-sm hidden-xs action-buttons">
                                                                <a class="blue" href="#"<%-- onclick="showInfo()--%>">
                                                                <i class="ace-icon fa fa-search-plus bigger-130"></i>
                                                                </a>

                                                                <a class="green" href="#">
                                                                    <i class="ace-icon fa fa-pencil bigger-130"></i>
                                                                </a>

                                                                <a class="red" href="#">
                                                                    <i class="ace-icon fa fa-trash-o bigger-130"></i>
                                                                </a>
                                                            </div>

                                                            <div class="hidden-md hidden-lg">
                                                                <div class="inline pos-rel">
                                                                    <button class="btn btn-minier btn-yellow dropdown-toggle"
                                                                            data-toggle="dropdown" data-position="auto">
                                                                        <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                                                                    </button>

                                                                    <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                                                        <li>
                                                                            <a href="#" class="tooltip-info" data-rel="tooltip"
                                                                               title="View">
                                                                                        <span class="blue">
                                                                                            <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                                                        </span>
                                                                            </a>
                                                                        </li>

                                                                        <li>
                                                                            <a href="#" class="tooltip-success"
                                                                               data-rel="tooltip" title="Edit">
                                                                                        <span class="green">
                                                                                            <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                                                        </span>
                                                                            </a>
                                                                        </li>

                                                                        <li>
                                                                            <a href="#" class="tooltip-error" data-rel="tooltip"
                                                                               title="Delete">
                                                                                        <span class="red">
                                                                                            <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                                                        </span>
                                                                            </a>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>

                                                </tbody>
                                            </table>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%--<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modal-table">
                            弹框
                        </button>--%>
                        <!-- 弹框（Modal） -->
                        <div id="modal-table"  class="modal fade" tabindex="-1">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header no-padding">
                                        <div class="table-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                <span class="white">×</span>
                                            </button>
                                            麂皮鞋
                                        </div>
                                    </div>

                                    <div class="modal-body no-padding">
                                        <table class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
                                            <thead>
                                            <tr>
                                                <th>用户姓名</th>
                                                <th>用户手机</th>
                                                <th>用户类型</th>
                                                <th>活跃时间</th>
                                                <th>用户性别</th>
                                                <th>用户邮箱</th>
                                                <th>公司名称</th>
                                                <th>联系地址</th>
                                                <%--<th>
                                                    <i class="ace-icon fa fa-clock-o bigger-110"></i>
                                                    活跃时间
                                                </th>--%>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td>
                                                    <a href="#"></a>
                                                </td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                            <%--<tr>
                                                <td>
                                                    <a href="#">base.com</a>
                                                </td>
                                                <td>$35</td>
                                                <td>2,595</td>
                                                <td>Feb 18</td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <a href="#">max.com</a>
                                                </td>
                                                <td>$60</td>
                                                <td>4,400</td>
                                                <td>Mar 11</td>
                                            </tr>

                                            <tr>
                                                <td>
                                                    <a href="#">best.com</a>
                                                </td>
                                                <td>$75</td>
                                                <td>6,500</td>
                                                <td>Apr 03</td>
                                            </tr>

                                            <tr>
                                                <td>
                                                    <a href="#">pro.com</a>
                                                </td>
                                                <td>$55</td>
                                                <td>4,250</td>
                                                <td>Jan 21</td>
                                            </tr>--%>
                                            </tbody>
                                        </table>
                                    </div>

                                    <div class="modal-footer no-margin-top">
                                        <button class="btn btn-sm btn-danger pull-left" data-dismiss="modal">
                                            <i class="ace-icon fa fa-times"></i>
                                            Close
                                        </button>
                                        <ul class="pagination pull-right no-margin">
                                            <li class="prev disabled">
                                                <a href="#">
                                                    <i class="ace-icon fa fa-angle-double-left"></i>
                                                </a>
                                            </li>

                                            <li class="active">
                                                <a href="#">1</a>
                                            </li>

                                            <li>
                                                <a href="#">2</a>
                                            </li>

                                            <li>
                                                <a href="#">3</a>
                                            </li>

                                            <li class="next">
                                                <a href="#">
                                                    <i class="ace-icon fa fa-angle-double-right"></i>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div><!-- /.modal-content -->
                            </div><!-- /.modal-dialog -->
                        </div>

                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div>
        </div>
    </div><!-- /.main-content -->
    <%@ include file="../inc/footer.jsp" %>
</div><!-- /.main-container -->
<script type="text/javascript">
var model = new Array();
$(function(){

    var dataTable=  $('#dynamic-table').DataTable({
    ajax: {//类似jquery的ajax参数，基本都可以用。
        "data":[{
            "loginName":"胡尧",
            "nickName":"胡尧",
            "type":"胡尧",
            "photo":"胡尧",
            "email":"胡尧",
            "login":"胡尧",
            "createTime":"胡尧"
        }] ,
        //type: "get",//后台指定了方式，默认get，外加datatable默认构造的参数很长，有可能超过get的最大长度。
        //url: "/manager/listData",
        // 请求到的数据
        dataSrc: function (data) {
            return data;
        },
        dataType: 'json',
        columns: [
            { "data": "loginName" },
            { "data": "nickName" },
            { "data": "type" },
            { "data": "photo" },
            { "data": "email" },
            { "data": "login" },
            { "data": "createTime" }
        ]
    },
    columns: [//对应上面thead里面的序列
       { "data": "loginName" },//字段名字和返回的json序列的key对应
       { "data": "nickName" },
       { "data": "type" },
       { "data": "photo" },
       { "data": "email" },
       { "data": "login" },
       { "data": "createTime" }
    ]
    });
});

</script>
</body>
</html>
