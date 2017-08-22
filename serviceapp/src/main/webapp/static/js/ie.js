$(function(){
	if(!placeholderSupport()){ // 判断浏览器是否支持 placeholder
		$('input').focus(function() {
			$(this).next().remove();
	   }).blur(function() {
			var input = $(this);
			input.attr("position","relative");
				if (input.val() == '') {
					input.after("<span class='sp' style='position: absolute;z-index: 9;color:#999;'>"+input.attr('placeholder')+"</span>");
				}
	   }).blur();
	};
})
function placeholderSupport() {
	return 'placeholder' in document.createElement('input');
}
