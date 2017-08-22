<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>

	<head>
		<title>Home</title>
	</head>

	<body>

		<div class="page-header">
			<h1>
			首页<small></small>
		</h1>
		</div>
		<div>
			<div class="col-xs-12" style="padding-bottom: 30px;">
				<div class="infobox infobox-green">
					<div class="infobox-icon">
						<i class="ace-icon fa fa-bell"></i>
					</div>

					<div class="infobox-data">
						<span>您有<a class="infobox-data-number" href="javascript:void(0)">
							${newOrderNum}</a>个新订单</span>
						<div class="infobox-content">
							<a href="javascript:void(0)">订单提醒</a>
						</div>
					</div>
				</div>

			</div>

			<div id="engineerSelfSale" class="engineerSelfSale"></div>
			<div id="companySale" class="companySale"></div>
		</div>
		<script src="${ctx}/static/js/echarts-all.js"></script>
		<script src="${ctx}/static/js/timelineOption.js"></script>
		<script type="text/javascript">
			var date=new Date;
			var month=date.getMonth()+1;
			var  year = date.getFullYear();
			var mydate = (year.toString()+(month<10 ? "0"+month:month).toString());
            var  orderCountList=${orderCountList};
            //获取今天是全年第几周
			var dateArray=new Array();
			var countArray01=new Array();
			var countArray02=new Array();
			for (var i=0;i<6;i++){
				dateArray.push((month-i)+"月");
				if (orderCountList[mydate-i]){
					countArray01.push(orderCountList[mydate-i])
				}else {
					countArray01.push(0)
				}
				if (orderCountList[mydate-100-i]){
					countArray02.push(orderCountList[mydate-100-i])
				}else {
					countArray02.push(0)
				}
			}
			var repairCountList=${repairCountList};
			console.log(repairCountList)
			var nameDate=new Array();
			var sum01=new Array();
			var sum02=new Array();
			var sum03=new Array();
			var sum04=new Array();
			for(var i = 0; i < repairCountList.length; i++) {
				nameDate.push(repairCountList[i].name);
				sum01.push(repairCountList[i].count);
				sum02.push(repairCountList[i].count1);
				sum03.push(repairCountList[i].count2);
				sum04.push(repairCountList[i].count3);
			}
			//公司报表
			var companySale = echarts.init(document.getElementById('companySale'));
			var companySaleInfo = {
				title: {
					text: '近半年订单月度统计对比',
					subtext: ''
				},
				tooltip: {
					x: 'right',
					y: 'top',
					'show': true,
					orient: 'vertical',
					trigger: 'axis'
				},
				legend: {
					data: ['2016年', '2017年'],
					x: 'right',
					y: '40',
					'show': true,
					//orient: 'vertical',
				},
				toolbox: {
					show: true,
					feature: {
						dataView: {
							show: true,
							readOnly: false
						},
						          magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
						          restore : {show: true},
						saveAsImage: {
							show: true
						}
					}
				},
				calculable: false,
				grid: {
					'x':45,
					'y':130,
					'y2': 100,
					'width':'75%'
				},
				xAxis: [{
					type: 'category',
					boundaryGap: false,
					data: dateArray
				}],
				yAxis: [{
					type: 'value',
					min: 0,
					max: 600
				}],
				series: [{
					name: '2017年',
					type: 'line',
					smooth: true,
					itemStyle: {
						normal: {
							areaStyle: {
								type: 'default'
							}
						}
					},
					data: countArray01
				}, {
					name: '2016年',
					type: 'line',
					smooth: true,
					itemStyle: {
						normal: {
							areaStyle: {
								type: 'default'
							}
						}
					},
					data: countArray02
				}]
			};
			companySale.setOption(companySaleInfo);

			//工程师报表
			var engineerSelfSale = document.getElementById("engineerSelfSale");
			var myChart = echarts.init(engineerSelfSale);
			var date=new Date;
			var month=date.getMonth()+1;
			var s1=[month-3,month-2,month-1,month];
			option = {
				timeline: {
					data: [
						'1', '2', '3', '4'
					],
					label: {
						formatter: function(s) {
							return s1[s-1]<10 ? "0"+s1[s-1]+'月':s1[s-1]+'月';
						}
					},
					currentIndex: 1,
					autoPlay: false,
					playInterval: 1000
				},
				options: [{
					title: {
						'text': s1[0]+'月份工程师接单量',
						'subtext': ''
					},
					tooltip: {
						'trigger': 'axis'
					},
					legend: {
						x: 'right',
						y: '40',
						'show': true,
					//	orient: 'vertical',
						'data': ['接单数'],
						'selected': {
							'接单数': true
						}
					},
					toolbox: {
						'show': true,
						//orient: 'vertical',
						x: 'right',
						y: 'top',
						feature: {
							dataView: {
								show: true,
								readOnly: false
							},
							magicType : {show: true, type: ['line', 'bar']},
							restore : {show: true},
							saveAsImage: {
								show: true
							}
						}
					},
					calculable: false,
					grid: {
						'x':45,
						'y': 80,
						'y2': 100,
						'width':'75%'
					},
					xAxis: [{
							'type': 'value',
							'name': '单',
							'max': 100
						}, {
							'type': 'value',
							'name': '其他（亿元）'
						}

					],
					yAxis: [{
						'type': 'category',
						'axisLabel': {
							'interval': 0
						},
						'data': nameDate
					}],
					series: [{
						'name': '接单数',
						'type': 'bar',
						itemStyle: {
										normal: {
											color: '#6fb3e0'
										}
									},
						'data': sum01
					}]
				}, {
					title: {
						'text': s1[1]+'月份工程师接单量'
					},
					series: [{
						'data': sum02
					},
					]
				},
                    {
                        title: {
                            'text': s1[2]+'月份工程师接单量'
                        },
                        series: [{
                            'data': sum03
                        },
                        ]
                    },
                    {
                        title: {
                            'text': s1[3]+'月份工程师接单量'
                        },
                        series: [{
                            'data': sum04
                        },
                        ]
                    }]
			};
			myChart.setOption(option);

            function theWeek() {
                var totalDays = 0;
                now = new Date();
                years = now.getYear()
                if (years < 1000)
                    years += 1900
                var days = new Array(12);
                days[0] = 31;
                days[2] = 31;
                days[3] = 30;
                days[4] = 31;
                days[5] = 30;
                days[6] = 31;
                days[7] = 31;
                days[8] = 30;
                days[9] = 31;
                days[10] = 30;
                days[11] = 31;

                //判断是否为闰年，针对2月的天数进行计算
                if (Math.round(now.getYear() / 4) == now.getYear() / 4) {
                    days[1] = 29
                } else {
                    days[1] = 28
                }

                if (now.getMonth() == 0) {
                    totalDays = totalDays + now.getDate();
                } else {
                    var curMonth = now.getMonth();
                    for (var count = 1; count <= curMonth; count++) {
                        totalDays = totalDays + days[count - 1];
                    }
                    totalDays = totalDays + now.getDate();
                }
                //得到第几周
                var week = Math.round(totalDays / 7);
                return week;
            }
		</script>

	</body>

</html>