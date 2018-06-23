var windowWidth,
	windowHeight,
	popstatus,
	//超时时间
	outTime;
/*
* whWindow()	浏览器宽高的获取
*/
function whWindow() {
	windowWidth = $(window).width();
	windowHeight = $(window).height();
}
/*
* windowSize()	窗口监听函数
*/
$(window).resize(function() {
	whWindow();
	popStatuRe();
});
whWindow();
/*
* popStatus()	提示
* status	1正确，2提示，3加载，4失败
* html	提示信息
* timeout	提示时间,单位：秒
* url	是否跳转页面,没有则留空
* bremove	是否使用遮照,不为空时使用
*/
function popStatus(status, html, timeout, url, popremove) {
	//请求超时时间 
	var timeous = 20;
	clearTimeout(popstatus);
	clearTimeout(outTime);
	$('body .pop').remove();
	$('body .pop_remove').remove();
	if (status == 1)
		$('body').append('<div class="pop"><div class="pop_base pop_true"></div><span class="pop_info">'+html+'</span></div>');
	else if (status == 2)
		$('body').append('<div class="pop"><div class="pop_base pop_tip"></div><span class="pop_info">'+html+'</span></div>');
	else if (status == 3)
		$('body').append('<div class="pop"><div class="pop_base pop_loadding"></div><span class="pop_info">'+html+'</span></div>');
	else
		$('body').append('<div class="pop"><div class="pop_base pop_fail"></div><span class="pop_info">'+html+'</span></div>');
	
	popStatuRe();
	//是否使用遮照
	if (popremove)
		$('body').append('<div class="pop_remove"/>');
	if (!url)
		url = 0;
	//抖动
	if (status == 2 || status ==4) {
		var sw = (windowWidth/2)-($('.pop').width()+18)/2;
		var sh = (windowHeight/2)-($('.pop').height()+18)/2;
		$('body .pop').animate({left:sw-15+'px'},100);
		$('body .pop').animate({left:sw+30+'px'},100);
		$('body .pop').animate({left:sw-30+'px'},100);
		$('body .pop').animate({left:sw+30+'px'},100);
		$('body .pop').animate({left:sw+'px'},100);
		$('body .pop').animate({top:sh-15+'px'},100);
		$('body .pop').animate({top:sh+30+'px'},100);
		$('body .pop').animate({top:sh-30+'px'},100);
		$('body .pop').animate({top:sh+30+'px'},100);
		$('body .pop').animate({top:sh+'px'},100);
	}
	popstatus = setTimeout(function() {
		//判断是否有跳转地址
		if (url != 0) 
			if (url == '?')
				reloads();
			else
				location.href = url;
		$('body .pop').remove();
		$('body .pop_remove').eq(-1).remove();
	},timeout*1000);
	//超时时间设置
	if (timeout >= timeous) {
		outTime = setTimeout(function() {
			if ($('body .pop')) {
				clearTimeout(popstatus);
				$('body .pop').remove();
				popStatus(4, '阿哦,请求地址错误或超时,请重试！', 3, '', true);
			}
		},timeout*1000-1000);
	}
}
/*
* popStatuRe()	位置矫正
*/
function popStatuRe() {
	$('body .pop').css({'left':(windowWidth/2)-($('.pop').width()+18)/2+'px','top':(windowHeight/2)-($('.pop').height()+18)/2+'px'});
}

function loading(msg){
	msg = msg||'加载中。。。';
	var pagei = $.layer({
		    type: 1,
		    title: false,
		    border: [0],
		    closeBtn: [0],
		    shade: [0.1],
		    area: ['159px', '40px'],
		    page: {
		        html: '<div id="loading" align="center"><img src="${ctx}/resource-injar/image?t=/loading/loading.gif" width="28" height="28" align="absmiddle"/>'+msg+'</div>'
		    }
		});
	return pagei;
}
