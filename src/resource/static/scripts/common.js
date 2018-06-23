$(function(){
	if($(".js-example-basic-single").length > 0){
		$(".js-example-basic-single").each(function(){
			if($(this).attr("search")=='-1'){
				$(this).select2({
					minimumResultsForSearch: -1,
					language: "zh-CN"
				});
			}else{
				$(this).select2({
					language: "zh-CN"
				});
			}
		});
	}
});
$.ajaxSetup({
	dataType:"JSON",
    complete:function(XMLHttpRequest,textStatus){
        var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus");
        if(sessionstatus=="timeout"){
	        var ctx = XMLHttpRequest.getResponseHeader("ctx");
        	top.location.href= ctx+'/admin/console';
        }
    },
    error: function(XMLHttpRequest, textStatus, errorMsg){
    	if(XMLHttpRequest.status == '404'){
			popStatus(4, '抱歉，您访问的页面没有找到。。。', 2);
         	return false;
    	}else if(XMLHttpRequest.status == '500'){
			popStatus(4, '出错了！服务器忙，请稍后。。。', 3);
         	return false;
    	}else{
			popStatus(4, '系统忙，请联系管理员！！！ ', 2);
         	return false;
    	}
    }
});
function addCookie(objName, objValue, objHours) {
	var str = objName + "=" + escape(objValue);
	if (objHours > 0) {
		var date = new Date();
		var ms = objHours * 3600 * 1000;
		date.setTime(date.getTime() + ms);
		str += "; expires=" + date.toGMTString()
	}
	document.cookie = str
}
function getCookie(objName) {
	var arrStr = document.cookie.split("; ");
	for (var i = 0; i < arrStr.length; i++) {
		var temp = arrStr[i].split("=");
		if (temp[0] == objName) {
			return unescape(temp[1])
		}
	}
	return ""
}

var Public = Public || {};
Public.isIE6 = !window.XMLHttpRequest;	//ie6
/*获取URL参数值*/
Public.getRequest = Public.urlParam = function() {
   var param, url = location.search, theRequest = {};
   if (url.indexOf("?") != -1) {
      var str = url.substr(1);
      strs = str.split("&");
      for(var i = 0, len = strs.length; i < len; i ++) {
		 param = strs[i].split("=");
         theRequest[param[0]]=decodeURIComponent(param[1]);
      }
   }
   return theRequest;
};
/*
  通用post请求，返回json
  url:请求地址， params：传递的参数{...}， callback：请求成功回调
*/ 
Public.ajaxPost = function(url, params, callback, errCallback){
	$.ajax({
	   type: "POST",
	   url: url,
	   data: params, 
	   
	   success: function(data, status){  
		   callback(data);  
	   },  
	   error: function(err){  
			errCallback && errCallback(err);
	   }
	});  
};  
Public.ajaxGet = function(url, params, callback, errCallback){    
	$.ajax({  
	   type: "GET",
	   url: url,
	   data: params,    
	   success: function(data, status){  
		   callback(data);  
	   },   
	   error: function(err){  
			errCallback && errCallback(err);
	   }
	});  
};

Public.addTab = function(tabid,text,url) {
	if(parent.tab.isTabItemExist(tabid)){
		parent.tab.selectTabItem(tabid);
		parent.tab.reload(tabid);
	}else{
		parent.tab.addTabItem({tabid:tabid,text:text,url:url,showClose:true});
	}
};

Public.closeTab = function() {
	parent.tab.removeSelectedTabItem();
};

Public.getSelectedTabId = function() {
	return parent.tab.getSelectedTabItemID();
};

Public.reloadTab = function(tabid) {
	parent.tab.reload(tabid);
};

Public.closeDialog = function() {
	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
	parent.layer.close(index); //执行关闭
}

Public.ajaxInsert = function(url){
	var idx = loading('操作中。。。');
	jQuery.ajax({
		url  : url,
		data : $('#data_form').serialize(),
		type : "POST",
		success : function(result) {
			layer.close(idx);
			if (result.code == '0000') {
				parent.pagination();
				parent.popStatus(1, '新增成功！！！', 1);
				Public.closeDialog();
			}else if (result.errMsg) {
				$("span[id='Validform_checktip']").each(function() {
					$(this).html('');
					$(this).attr("class", "Validform_right");
				});
				$.each(result.errMsg, function(tag, msg) {
					$obj = $("#" + tag).next('span');
					$obj.attr("class", "Validform_wrong");
					$obj.html(msg);
				});
			}else{
				popStatus(4, result.msg, 2);
			}
		}
	});
}

Public.optionIFrameFlush = function(url){
	jQuery.ajax({
		url  : url,
		data : $('#data_form').serialize(),
		type : "POST",
		success : function(result) {
			if (result.code == '0000') {
				popStatus(1, result.msg||'操作成功！！！', 1);
				parent.parent.location.reload();
			}else if (result.errMsg) {
				$("span[id='Validform_checktip']").each(function() {
					$(this).html('');
					$(this).attr("class", "Validform_right");
				});
				$.each(result.errMsg, function(tag, msg) {
					$obj = $("#" + tag).next('span');
					$obj.attr("class", "Validform_wrong");
					$obj.html(msg);
				});
			}else{
				popStatus(4, result.msg, 2);
			}
		}
	});
}

Public.ajaxUpdate = function(url){
	var idx = loading('操作中。。。');
	jQuery.ajax({
		url  : url,
		data : $('#data_form').serialize(),
		type : "POST",
		success : function(result) {
			layer.close(idx);
			if (result.code == '0000') {
				parent.pagination();
				parent.popStatus(1, '更新成功！！！', 1);
				Public.closeDialog();
			}else if (result.errMsg) {
				$("span[id='Validform_checktip']").each(function() {
					$(this).html('');
					$(this).attr("class", "Validform_right");
				});
				$.each(result.errMsg, function(tag, msg) {
					$obj = $("#" + tag).next('span');
					$obj.attr("class", "Validform_wrong");
					$obj.html(msg);
				});
			}else{
				popStatus(4, result.msg, 2);
			}
		}
	});
}
Pn = {
	version : '1.0'
};
/**
 * 颜色选择器
 */
Pn.ColorPicker = function(input) {
	var obj = this;
	this.isShow = false;
	this.target = $(input);
	this.button = $("<input type='text' tabindex='10000'"
			+ " readonly='readonly' style='background-color:#fff;width:48px;height:29px;"
			+ "border:1px solid #ccc;margin-left:2px;cursor:pointer'/>");
	this.target.after(this.button);
	this.over = function() {
		$(this).css("border", "1px solid #000");
	}
	this.out = function() {
		$(this).css("border", "1px solid #fff");
	}
	//title获取不到改成获取id属性
	this.click = function() {
		var color = $(this).attr("id");
		obj.setColor(color);
		this.isShow = false;
		obj.picker.hide();
	}
	this.createPicker = function() {
		var c = ["#FF8080", "#FFFF80", "#80FF80", "#00FF80", "#80FFFF",
				"#0080FF", "#FF80C0", "#FF80FF", "#FF0000", "#FFFF00",
				"#80FF00", "#00FF40", "#00FFFF", "#0080C0", "#8080C0",
				"#FF00FF", "#804040", "#FF8040", "#00FF00", "#008080",
				"#004080", "#8080FF", "#800040", "#FF0080", "#800000",
				"#FF8000", "#008000", "#008040", "#0000FF", "#0000A0",
				"#800080", "#8000FF", "#400000", "#804000", "#004000",
				"#004040", "#000080", "#000040", "#400040", "#400080",
				"#000000", "#808000", "#808040", "#808080", "#408080",
				"#C0C0C0", "#400040", "#FFFFFF"];
		var s = "<table border='0' cellpadding='0' cellspacing='5' "
				+ "style='border-collapse:separate;border-spacing:5px;display:none;position:absolute;margin-top:0px;border:1px solid #ccc;background-color:#fff;z-index: 102'>"
				+ "<tr height='15'>";
		// 列数
		var col = 8;
		for (var i = 0, len = c.length;i < len; i++) {
			/*
			s += "<td width='15'><div class='c' style='width:13px;height:13px;"
					+ "border:1px solid #fff;cursor:pointer;background-color:"
					+ c[i] + "' title='" + c[i] + "'>&nbsp;</div></td>";
					*/
			s += "<td width='15'><div class='c' id='"+c[i]+"' title='"+c[i]+"' style='width:13px;height:13px;"
				+ "border:1px solid #fff;cursor:pointer;background-color:"
				+ c[i] + "'>&nbsp;</div></td>";
			if ((i + 1) != c.length && (i + 1) % col == 0) {
				s += "</tr><tr height='15'>";
			}
		}
		// s += "</tr><tr><td colspan=" + col
		// + "><div class='c' style='width:153px;height:13px;text-align:center;"
		// + "border:1px solid #fff;cursor:pointer;background-color:#fff'"
		// + " title=''>clear<div></td>";
		s += "</tr></table>";
		var picker = $(s);
		picker.find(".c").each(function() {
			$(this).bind("mouseover", obj.over);
			$(this).bind("mouseout", obj.out);
			$(this).bind("click", obj.click);
		});
		$(document.body).append(picker);
		return picker;
	}
	this.setColor = function(color) {
		obj.target.val(color);
		if (color == "") {
			color = "#fff";
		}
		try {
			obj.button.css( {
				backgroundColor : color
			});
		} catch (e) {
			alert("color error: " + color);
			obj.target.focus().select();
		}
	}
	this.picker = this.createPicker();
	this.showPicker = function() {
		if (!obj.isShow) {
			obj.isShow = true;
			obj.picker.showBy(obj.target);
			$(document).bind("mousedown", function() {
				$(document).unbind("mousedown");
				setTimeout(function() {
					obj.isShow = false;
					obj.picker.hide();
					var val = obj.target.val();
					obj.setColor(obj.target.val());
				}, 200);
			});
		}
	}
	this.button.bind("click", obj.showPicker);
	this.target.bind("click", obj.showPicker);
	this.target.bind("blur", function() {
		obj.setColor(obj.target.val());
	});
	var v = this.target.val();
	if (v != "") {
		this.setColor(v);
	}
};
$.fn.extend( {
	showBy : function(target) {
		var offset = target.offset();
		var top, left;
		var b = $(window).height() + $(document).scrollTop() - offset.top
				- target.outerHeight();
		var t = offset.top - $(document).scrollTop();
		var r = $(window).width() + $(document).scrollLeft() - offset.left;
		var l = offset.left + target.outerWidth() - $(document).scrollLeft();
		if (b - this.outerHeight() < 0 && t > b) {
			top = offset.top - this.outerHeight() - 1;
		} else {
			top = offset.top + target.outerHeight() + 1;
		}
		if (r - this.outerWidth() < 0 && l > r) {
			left = offset.left + target.outerWidth() - this.outerWidth();
		} else {
			left = offset.left;
		}
		this.css("top", top).css("left", left).show();
	}
});
$.fn.extend( {
	colorPicker : function() {
		new Pn.ColorPicker(this);
	}
});