$(function () {
    //menu
    $('#side-menu').find("li").each(function () {
        var menu_a = $(this).find("a").eq(0);
        var page_title = $("#page-wrapper .page-header").text();
        if (menu_a.text() == page_title) {
            menu_a.addClass("active");
            var ul = $(this).parent("ul .nav-second-level")
            if (ul.length > 0) {
                ul.addClass("in")
                ul.parent("li").addClass("active")
            }

        }
    })
    //小提示框
    $('.img_tooltip').tooltip({
        selector: "[data-toggle=tooltip]",
        container: "body"
    })
    $(".btn-outline").hover(
        function () {
            $(this).toggleClass("btn-lg");
        },
        function () {
            $(this).toggleClass("btn-lg");
        }
    );
})

var $dh = {
    v: {
        ajaxOption: {
            method: 'get',
            dataType: 'json',
            async: true,
            arr: []
        },
        notifyMethod: null,
        dataTableL: {
            "language": {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            }
        }
    },
    navigation: function (item, attrs) {
        if ($dh.isArray(attrs)) {
            if (attrs.length > 1) {
                for (var i = 0; i < attrs.length; i++) {
                    var o = item[attrs[i]];
                    $dh.v.arr = attrs.slice(i + 1);
                    //console.log("o: "+JSON.stringify(o)+"   |||   "+"i: "+i+"      |||     "+"attrs: "+attrs);
                    return $dh.navigation(o, $dh.v.arr);
                }
            } else {
                return item[attrs[0]];
            }
        } else {
            return item[attrs];
        }
    },
    isArray: function (o) {
        return Object.prototype.toString.call(o) == '[object Array]';
    },
    notify: function (msg, status) {
        var option = {
            position: "top center",
            autoHideDelay: 2000,
            className: status,
            arrowSize: 10
        }
        $.notify(msg, option);
    },
    optNotify: function (method, title, button) {
        if ($("#notifyjs-foo-alert-option").length > 0) {
            return false;
        }

        if (title === undefined) {
            title = '确认删除么？删除后不可恢复！';
        }
        if (button === undefined) {
            button = '删除';
        }

        $.notify({
            title: title,
            button: button
        }, {
            style: 'foo',
            autoHide: false,
            clickToHide: false,
            position: "top center"
        });
        if (method != undefined) {
            $dh.v.notifyMethod = method;
        }
    },
    uiform: function () {
//		jQuery('tbody input:checkbox').click(function() {
//			if(jQuery(this).is(':checked')) {
//				jQuery(this).parent().addClass('checked');
//				jQuery(this).parents('tr').css("background-color", "#ccde8d");
//			} else {
//				jQuery(this).parent().removeClass('checked');
//				jQuery(this).parents('tr').removeAttr("style");
//			}
//		});
    },
    cutText: function (sub, length, less) {
        var str = "";
        if (sub.length > length) {
            str = sub.substr(0, length);
        } else {
            str = sub;
        }
        if (less) {
            str = str + less;
        }
        return str;
    },
    checkAll: function (obj) {
        var parentTable = jQuery(obj).parents('table');
        var ch = parentTable.find('tbody input[type=checkbox]');
        if (jQuery(obj).is(':checked')) {
            ch.each(function () {
                jQuery(this).prop('checked', true);
                jQuery(this).parent().addClass('checked');
             //   jQuery(this).parents('tr').css("background-color", "#ccde8d");
            });
        } else {
            ch.each(function () {
                jQuery(this).removeAttr('checked')
                jQuery(this).parent().removeClass('checked');
                jQuery(this).parents('tr').removeAttr("style");
            });
        }
    },

    ajax: function (url, data, callbackFun, option) {
        if (option == null || option == undefined) {
            option = $dh.v.ajaxOption;
        } else {
            if (option.method == null || option.method == undefined) {
                option.method = $dh.v.ajaxOption.method;
            }
            if (option.dataType == null || option.dataType == undefined) {
                option.dataType = $dh.v.ajaxOption.dataType;
            }
            if (option.async == null || option.async == undefined) {
                option.async = $dh.v.ajaxOption.async;
            }
        }
        jQuery.ajax({
            dataType: option.dataType,
            url: url,
            data: data,
            async: option.async,
            success: function (data) {
                callbackFun(data);
            },
            statusCode: {
                401: function () {
                },
                403: function () {
                },
                500: function () {
                }
            }
        });
    },
    turnArray: function (arr) {
        var newArr = [];
        for (var i = arr.length - 1; i >= 0; i--) {
            newArr.push(arr[i])
        }
        return newArr;
    },
    /**
     * 清理表单参数
     * @param form
     * @param option boolean类型，为true清理select插件
     */
    clearForm: function (form) {
        form.find("select").val(0);
        form.find("input").not("[type='radio']").val("");
        form.find("input[type=checkbox]").removeAttr("checked");
        //清空图像
        form.validator("cleanUp");
    },
    dataTable: function (obj, option) {
        return obj.DataTable($.extend($dh.v.dataTableL, option))
    },
    tableTools:function(myTable, buttonId) {
    $.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';
    var toolname = [];
    var customButton = [];
    $.each(buttonId, function (index, value) {
        switch (value) {

            case "colvis":
                toolname.push({
                    "extend": "colvis",
                    "text": "<i class='fa fa-search bigger-110 blue'></i> <span class='hidden'>显示/隐藏栏目</span>",
                    "className": "btn btn-white btn-primary btn-bold",
                    columns: ':not(:first):not(:last)'
                });
                break;

            case "copy":
                toolname.push({
                    "extend": "copy",
                    "text": "<i class='fa fa-copy bigger-110 pink'></i> <span class='hidden'>复制到剪切板</span>",
                    "className": "btn btn-white btn-primary btn-bold"
                });
                break;

            case "csv":
                toolname.push({
                    "extend": "csv",
                    "text": "<i class='fa fa-database bigger-110 orange'></i> <span class='hidden'>导出CSV文件</span>",
                    "className": "btn btn-white btn-primary btn-bold"
                });
                break;

            case "excel":
                toolname.push({
                    "extend": "excel",
                    "text": "<i class='fa fa-file-excel-o bigger-110 green'></i> <span class='hidden'>导出Excel文件</span>",
                    "className": "btn btn-white btn-primary btn-bold"
                });
                break;

            case "pdf":
                toolname.push({
                    "extend": "pdf",
                    "text": "<i class='fa fa-file-pdf-o bigger-110 red'></i> <span class='hidden'>导出PDF格式文件</span>",
                    "className": "btn btn-white btn-primary btn-bold"
                });
                break;

            case "print":
                toolname.push({
                    "extend": "print",
                    "text": "<i class='fa fa-print bigger-110 grey'></i> <span class='hidden'>打印</span>",
                    "className": "btn btn-white btn-primary btn-bold",
                    autoPrint: false,
                    message: 'This print was produced using the Print button for DataTables'

                });
                break;

            case "search":
                customButton.push({
                    "text": "查询",
                    "id": "searchTableBtn",
                    "className": "fa-search",
                    "color": "orange"
                });
                break;

            case "create":

                customButton.push({
                    "text": "新增",
                    "id": "create",
                    "className": "fa-plus",
                    "color": "green"
                });
                break;

            case "batchDel":

                customButton.push({
                    "text": "批量删除",
                    "id": "batchDel",
                    "className": "fa-trash-o",
                    "color": "red"
                });
                break;

            default:
                break;
        }

    })


    //原生工具组的初始化

    new $.fn.dataTable.Buttons(myTable, {

        buttons: toolname //改变原生的button时改变此json变量

    });

    //额外扩展按钮如增加、删除整合到原生工具组中

    var html = '';

    $.each(customButton, function (name, value) {

        html = html + '<a class="dt-button btn btn-white btn-primary btn-bold" tabindex="0" aria-controls="dataTables" data-original-title="" title="" id="' + value['id'] + '" ><span><i class="fa ' + value['className'] + ' bigger-110 ' + value['color'] + '"></i> <span class="hidden">' + value['text'] + '</span></span></a>';

    })

    $(html).appendTo($(".tableTools-container"));

    //	将导出、打印等功能整合到工具组
    myTable.buttons().container().appendTo($('.tableTools-container'));

    //style the message box
    var defaultCopyAction = myTable.button(1).action();

    myTable.button(1).action(function (e, dt, button, config) {
        defaultCopyAction(e, dt, button, config);
        $('.dt-button-info').addClass('gritter-item-wrapper gritter-info gritter-center white');
    });

    var defaultColvisAction = myTable.button(0).action();
    myTable.button(0).action(function (e, dt, button, config) {

        defaultColvisAction(e, dt, button, config);

        if ($('.dt-button-collection > .dropdown-menu').length == 0) {
            $('.dt-button-collection')
                .wrapInner('<ul class="dropdown-menu dropdown-light dropdown-caret dropdown-caret" />')
                .find('a').attr('href', '#').wrap("<li />")
        }
        $('.dt-button-collection').appendTo('.tableTools-container .dt-buttons')
    });

    setTimeout(function () {
        $($('.tableTools-container')).find('a.dt-button').each(function () {
            var div = $(this).find(' > div').first();
            if (div.length == 1) div.tooltip({
                container: 'body',
                title: div.parent().text()
            });
            else $(this).tooltip({
                container: 'body',
                title: $(this).text()
            });
        });
    }, 500);


    //全选反选

    myTable.on('select', function (e, dt, type, index) {
        if (type === 'row') {
            $(myTable.row(index).node()).find('input:checkbox').prop('checked', true);
        }
    });
    myTable.on('deselect', function (e, dt, type, index) {
        if (type === 'row') {
            $(myTable.row(index).node()).find('input:checkbox').prop('checked', false);
        }
    });


    $('#dataTables > thead > tr > th input[type=checkbox], #dataTables_wrapper input[type=checkbox]').eq(0).on('click', function () {

        var th_checked = this.checked; //checkbox inside "TH" table header

        $('#dataTables').find('tbody > tr').each(function () {
            var row = this;

            if (th_checked) myTable.row(row).select();
            else myTable.row(row).deselect();
        });
    });


//单选表格显示记录，背景加深

    $('#dataTables').on('click', 'td input[type=checkbox]', function () {
       //取消选择其中一条数据，全选按钮不勾选
    	$('tbody input[type=checkbox]').each(function(i,e){
        	if(e.checked==false){
        		$('thead input[type=checkbox]').attr('checked',false);
        		return false;
        	}
        })
    	var row = $(this).closest('tr').get(0);
        if (this.checked) myTable.row(row).select();
        else myTable.row(row).deselect();
    });


    //全选反选结束
},
    tableSearch:function(datatable) {
    var a = datatable.table().ajax.params().columns;//属性名称
    var b = datatable.table().header();//内容
  //  var c=datatable.tables.data();//第一条数据
//  将表格的标题写入.tabletitlesel下拉框的选项里
    var selectHtml = '';
    $(b).find("th").each(function (index, item) {
        if (index != 0 && index != ($(b).find("th").size() - 1)) {
            selectHtml += '<option value="' + $.trim($(this).text()) + '"  data-name="' + a[index].data + '">' + $.trim($(this).text()) + '</option>';
        }
    });
    selectHtml = '<select  class="ui-widget-content ui-corner-all">' + selectHtml + '</select>';

    // $(".tabletitlesel").append(selectHtml);

    //  将表格的标题写入.tabletitlesel下拉框的选项里结束

    $("#searchTableBtn").click(function () {
        $(".ui-widget-overlay").show();
        $("#searchmodfbox_grid-table").show();
    })

    $(".ui-widget-overlay").click(function () {
        $(this).hide();
        $("#searchmodfbox_grid-table").hide();
    })

    $("#closebtn").click(function () {
        $(".ui-widget-overlay").hide();
        $("#searchmodfbox_grid-table").hide();
    })
var html = '<tr><td class="columns tabletitlesel">' + selectHtml + '</td><td class="operators"><select class="selectopts ui-widget-content ui-corner-all">' +
	            '<option value="EQ" selected="selected">等于</option>' +
                '<option value="NEQ">不等于</option>' +
	            '<option value="BEG">之前</option>' +
                '<option value="AFT">之后</option>' +
	            '<option value="LIKE">包含关键字</option>' +
                '<option value="NL">不包含关键字</option>' +
	            '<option value="NU">是空</option>' +
                '<option value="NN">是非空</option>' +
	            '<option value="GT">大于</option><option value="GTE">大于等于</option>' +
	            '<option value="LT">小于</option><option value="LTE">小于等于</option>' +
	            '</select></td><td class="data">' +
	            '<input type="text" class="input-elm ui-widget-content ui-corner-all" name="search_EQ_'+a[1].data+'" style="width: 96%;"><select style="display: none;"><option value="1"></option><option value="2"></option><option value="3"></option><option value="4"></option></select></td><td>' +
	            '<input type="button" value="X" title="删除这一条" class="delete-rule ui-del ui-state-default ui-corner-all deletelinebtn"></td></tr>';
 			
 			$("#searchbox").append(html);
    //点增加按钮，增加一行查询
        $("#searchbox").on("click", "#addbtn", function () {

           $("#searchbox").append(html);

        })
        $("#searchbox").on("change"," .tabletitlesel", function () {
            var opts = $(this).closest("tr").find(".selectopts option:selected").val();
            console.log(opts)
            console.log($("option:selected", this).data("name"))
            $(this).closest("tr").find(".input-elm").attr("name", "search_" + opts + "_" + $("option:selected", this).data("name"));
        });
        $("#searchbox").on("change",".selectopts" ,function () {
            var dataName = $(this).closest("tr").find(".tabletitlesel option:selected").data("name");
            if ($(this).val()=="NU"||$(this).val()=="NN"){
                $(this).next("td").hide();
            }else {
                $(this).next("td").show();
            }
            $(this).closest("tr").find(".input-elm").attr("name", "search_" + $(this).val() + "_" + dataName);
        })

    //点删除按钮，删除对应的一行查询
    $("#searchbox").on("click", ".deletelinebtn", function () {
        $(this).closest("tr").remove();
    })


    //点击重置按钮，重置#searchBox的内容

    $(".ui-reset").click(function () {
        /*$("#searchbox").html('<tr><th colspan="5" align="left">' +
            '<input type="button" id="addbtn" value="+" title="增加查询" class="add-rule ui-add ui-state-default ui-corner-all btn btn-xs btn-primary">' +
            '</th></tr>' +
            '<tr><td class="columns tabletitlesel"  style="width: 30%;">' + selectHtml + '</td>' +
            '<td class="operators" style="width: 25%;"><select class="selectopts ui-widget-content ui-corner-all">' +
            '<option value="eq" selected="selected">等于</option><option value="ne">不等于</option><option value="bw">包含</option>' +
            '<option value="bn">不包含</option><option value="nu">是空</option><option value="nn">是非空</option><option value="in">在...里面</option>' +
            '<option value="ni">不在...里面</option></select></td><td class="data" style="width:45%"><input type="text" class="input-elm ui-widget-content ui-corner-all" style="width: 96%;"></td><td>' +
            '<input type="button" value="X" title="删除这一条" class="delete-rule ui-del ui-state-default ui-corner-all deletelinebtn"></td></tr>');*/
        $("#searchbox tr:not(':first')").remove();
    })


}
}

$.notify.addStyle('foo', {
    html: "<div id='notifyjs-foo-alert-option'>" +
    "<div class='clearfix'>" +
    "<div class='title' data-notify-html='title'/>" +
    "<div class='buttons'>" +
    "<button class='no'>取消</button>" +
    "<button class='yes' data-notify-text='button'></button>" +
    "</div>" +
    "</div>" +
    "</div>"
});

$(document).on('click', '.notifyjs-foo-base .no', function () {
    $(this).trigger('notify-hide');
});
$(document).on('click', '.notifyjs-foo-base .yes', function () {
    if ($dh.v.notifyMethod != null) {
        eval("$dh.v.notifyMethod()");
    }
    $(this).trigger('notify-hide');
});

;
(function ($) {
    $.fn.getInputId = function (sigle) {
        var checkIds = [];

        this.each(function () {
            checkIds.push($(this).val())
        });

        if (sigle) {
            if (checkIds.length > 1) {
                $dh.notify('只能选择一条记录！', 'error');
                return false;
            } else if (checkIds.length == 0) {
                $dh.notify('请选择一条记录操作！', 'error');
                return false;
            } else {
                return checkIds[0];
            }
        } else {
            if (checkIds.length == 0) {
                $dh.notify('请选择至少一条记录操作！', 'error');
                return false;
            } else {
                return checkIds;
            }

        }
    };
})(jQuery);

