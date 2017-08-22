	$().ready(function(){
//			tabs
			var oInput10=$("#payinput10").children().children().children("input");
			var sumMoney10=$("#sumMoney10").html();
			var btn1=$("#btn1");
			var oInput11=$("#payinput11").children().children().children("input");
			var sumMoney11=$("#sumMoney11").html();
			var btn2=$("#btn2");
			
			
			var oInput20=$("#payinput20").children().children().children("input");
			var sumMoney20=$("#sumMoney20").html();
			var btn3=$("#btn3");
			var oInput21=$("#payinput21").children().children().children("input");
			var sumMoney21=$("#sumMoney21").html();
			var btn4=$("btn4");
			function paytab(oInput,sumMoney,btn) {//
				var len = oInput.length;
				var sumNumMoney1 = parseFloat(sumMoney);
				var sumInput = 0;
				var count = 0;
				var leftInput = 0;
				var otherInput = 0;
				/*oInput.blur(function(){
				 sumInput=0;
				 count=0;
				 $(this).next('p').next('span').text('');
				 //					btn.addClass("btn-primary");
				 //					btn.removeClass("disabled");
				 oInput.each(function(){
				 if($(this).val()!=0&&!isNaN($(this).val())){
				 sumInput=sumInput+parseFloat($(this).val());
				 count=count+1;
				 }
				 if(isNaN($(this).val())){
				 $(this).next('p').next('span').text('*请输入数字');
				 $(this).val('0');
				 btn.addClass("disabled");
				 btn.removeClass("btn-primary");
				 }
				 else if($(this).val()<0){
				 $(this).next('p').next('span').text('*金额必须大于0');
				 $(this).val('0');
				 btn.addClass("disabled");
				 btn.removeClass("btn-primary");
				 }
				 else if(sumInput>sumNumMoney1){
				 $(this).next('p').next('span').text('*总金额必须等于'+sumNumMoney1);
				 btn.addClass("disabled");
				 btn.removeClass("btn-primary");
				 }
				 else if(sumInput==sumNumMoney1){
				 $(this).next('p').next('span').text('');
				 btn.addClass("btn-primary");
				 btn.removeClass("disabled");
				 }
				 })
				 })
				 oInput.focus(function(){
				 leftInput=sumNumMoney1-sumInput;
				 if(count==len-1&&$(this).val()==0){
				 $(this).val(leftInput);

				 }
				 if(sumInput!=sumNumMoney1&&count==3){
				 otherInput=sumInput-parseFloat($(this).val());
				 $(this).val(sumNumMoney1-otherInput);

				 }
				 })
				 }*/
				/*paytab(oInput10,sumMoney10,btn1);
				 paytab(oInput11,sumMoney11,btn2);
				 paytab(oInput20,sumMoney20,btn3);
				 paytab(oInput21,sumMoney21,btn4);*/
//			tabs

//			左边菜单切换
				var aLi=$("#account").children("li");//导航标签
				 var aSpan=$("#mainContent").children("span");//被切换的标签
				 function leftmenu(aLi,aSpan){
				 var oIndex=0;
				 $(aLi).click(function(){
				 oIndex=$(this).index();
				 $(this).siblings("li").removeClass("active");
				 $(this).addClass("active");
				 aSpan.css("display","none");
				 aSpan.eq(oIndex).css("display","block");
				 })
				 }
				 leftmenu(aLi,aSpan);
			}
//			左边菜单切换	
		
//          scrollables
				$('.slim-scroll').each(function () {
					var $this = $(this);
					$this.slimScroll({
						height: $this.data('height') || 440,
						railVisible:true
					});
				});
				
				$('.slim-scroll1').each(function () {
					var $this = $(this);
					$this.slimScroll({
						height: $this.data('height') || 310,
						railVisible:true
					});
				});
			
	});
//          scrollables

