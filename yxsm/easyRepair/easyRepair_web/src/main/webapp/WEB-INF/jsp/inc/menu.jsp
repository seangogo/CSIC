<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="sidebar" class="sidebar responsive ace-save-state">
    <shiro:user>
        <div class="sidebar-shortcuts" id="sidebar-shortcuts">
            <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                <button class="btn btn-success">
                    <i class="ace-icon fa fa-signal"></i>
                </button>
                <button class="btn btn-info">
                    <i class="ace-icon fa fa-pencil"></i>
                </button>
                <button class="btn btn-warning">
                    <i class="ace-icon fa fa-users"></i>
                </button>

                <button class="btn btn-danger">
                    <i class="ace-icon fa fa-cogs"></i>
                </button>
            </div>
            <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                <span class="btn btn-success"></span> <span class="btn btn-info"></span>
                <span class="btn btn-warning"></span> <span class="btn btn-danger"></span>
            </div>
        </div>
        <!-- /.sidebar-shortcuts -->
        <ul class="nav nav-list">
        </ul>
        <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
            <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state"
               data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right">
            </i>
        </div>
    </shiro:user>
</div>
<div class="main-content" id="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs ace-save-state" id="breadcrumbs">
            <ul class="breadcrumb" id="menu-nav">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="index">Home</a>

                </li>
            </ul>

            <div class="nav-search" id="nav-search">
                <form class="form-search">
                <span class="input-icon">
                    <input type="text" placeholder="输入模块关键字" class="nav-search-input"
                           id="nav-search-input" autocomplete="off"/>
                    <i class="ace-icon fa fa-search nav-search-icon"></i>
                </span>

                </form>

            </div>
        </div>
        <div class="page-content" id="page-content">
            欢迎来到首页！
        </div>
    </div>
</div>
<script>
    var _menu = {
        v: {
            id: "user",
            result:${userMenu},
            menu : new Array(),
            data : new Array()
        },
        fn: {
            init: function () {
                _menu.fn.menu_init();
            },
            menu_init:function () {
                _menu.v.menu[0] = new Array();
                _menu.v.menu[1] = new Array();
                _menu.v.menu[2] = new Array();
                _menu.v.menu[3] = new Array();
                var result=_menu.v.result;
                for (var i = 0; i < result.length; i++) {
                    _menu.v.menu[result[i].type - 1].push(result[i]);
                    if (result[i].resUrl == '') {
                        for (var j = 0; j < result[i].resources.length; j++) {
                            var obj = result[i].resources[j];
                            var menuObj = {label: obj.name, category: result[i].name, url: obj.resUrl}
                            _menu.v.data.push(menuObj)
                        }
                    } else {
                        var menuFirst = {label: result[i].name, category: result[i].name, url: result[i].resUrl}
                        _menu.v.data.push(menuFirst)
                    }
                }
            }
        }
    }


    $(document).ready(function () {
        _menu.fn.init();
        //搜索模块
        $.widget("custom.catcomplete", $.ui.autocomplete, {
            _create: function () {
                this._super();
                this.widget().menu("option", "items", "> :not(.ui-autocomplete-category)");
            },
            _renderMenu: function (ul, items) {
                var that = this,
                        currentCategory = "";
                $.each(items, function (index, item) {
                    var li;
                    if (item.category != currentCategory) {
                        ul.append("<li class='ui-autocomplete-category'>" + item.category + "</li>");
                        currentCategory = item.category;
                    }
                    li = that._renderItemData(ul, item);
                    if (item.category) {
                        li.attr("aria-label", item.label);
                        li.attr("data-parent", item.category);
                        li.attr("data-url", item.url);
                    }
                });
              //  $("#page-content").load($(".ui-menu-item").data("url"));
            }

        });
        var catcomplete = $("#nav-search-input").catcomplete({
            delay: 0,
            source: _menu.v.data,
            autoFocus: true,
            cacheLength:300,
            autoFill: true,
            change: function (event, ui) {
                if (ui.item == null) { $dh.notify("模块菜单不存在", "error");
                } else {
                    $("#page-content").load(ui.item.url);
                    var nav_html = " <li><i class='ace-icon fa fa-home home-icon'></i><a href='index'>Home</a>" +
                            "</li><li><a href='#'>" + ui.item.category + "</a></li><li class='active'>" + ui.item.value + "</li>";
                    $("#menu-nav").html(nav_html);
                }
            },
            //position : {my : '10', at : '222'}位置调整
        });
        $("body").keydown(function() {
            if (event.keyCode == "13") {//keyCode=13是回车键
                if($('#nav-search-input').is(":focus")){
                        $("#nav-search-input").blur();
                }else{
                    if(!$("#modal-form").css("display")||$("#modal-form").css("display")=="none"){
                        $('#nav-search-input').focus();
                    }
                }
            }
        });
        $("#sidebar-shortcuts-large button").on("click", function () {
            var list = _menu.v.menu[$(this).index()];
            var html = "";
            $.each(list, function (index, item) {
                var resourceList = item.resources;

                html += "<li ><a class='dropdown-toggle' data-url=" + item.resUrl + "><i class='menu-icon fa " + item.iconCls + "'></i>" +
                        "<span class='menu-text'>" + item.name +
                        "</span>";
                if (item.resources.length > 0) {
                    html += "<b class='arrow fa fa-angle-down'></b>";
                }
                html += "</b></a><b class='arrow'></b><ul class='submenu'>";
                for (var i = 0; i < resourceList.length; i++) {
                    html += "<li class='menuLi'><a data-url=" + resourceList[i].resUrl + "><i class='menu-icon fa fa-caret-right'>" +
                            "</i>" + resourceList[i].name;
                    if (resourceList[i].resources.length > 0) {
                        html += "<b class='arrow fa fa-angle-down'></b>";
                    }
                    html += "</a><b class='arrow'></b><ul class='submenu'>";
                    var resources_last = resourceList[i].resources;
                    for (var j = 0; j < resources_last.length; j++) {
                        html += "<li class='menuLi2'><a data-url=" + resources_last[j].resUrl + "><i class='menu-icon fa fa-caret-right'></i>" +
                                resources_last[j].resUrl.name + "<br/>" + resources_last[j].resUrl + "</a><b class='arrow'></b></li>"
                    }
                    html += "</ul></li>";
                }
                html += "</ul></li>"
            })
            $(".nav-list").html(html);
            //菜单点击事件
            $.ajaxSetup({
                cache: false //关闭AJAX相应的缓存
            });
            
            function removeclassname(){
            	 $(this).parent("li").removeClass("open");
            }
            
			$(".nav-list li a").on("click", function () {
            	
                if ($(this).data("url") != "" && $(this).data("url") != undefined) {
                    var url = basePath + $(this).data("url");
                    $("#page-content").load(url);
                    $("#update-password").data("url",url);
                    var nav=$(this).closest("ul").closest("li").find(".menu-text").text();
                    var nav_html =null;
                    if(nav){
                    	nav_html = " <li><i class='ace-icon fa fa-home home-icon'></i><a href='index'>Home</a>" +
                        "</li><li><a href='#'>" + nav +"</a></li><li class='active'>" + $(this).text() + "</li>";
                    }else{
                    	nav_html = " <li><i class='ace-icon fa fa-home home-icon'></i><a href='index'>Home</a>" +
                        "</li><li class='active'>" + $(this).text() + "</li>";
                    }
                    $("#menu-nav").html(nav_html);
                }
                	$(".nav-list li").removeClass("active");
                	$(this).parent("li").addClass("active");
                	if(!$(".open").hasClass("active")){
                		$(".open").addClass("active");
                	}
            });
            
        })
    });
</script>

