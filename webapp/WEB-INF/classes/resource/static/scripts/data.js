var sort;
var order;

function pagination(page){
	if(!page){
		page = 1;
	}
	var loadi;
	var url =$(".ltable").attr("url");
	if(!url){
		return false;
	}
	jQuery.ajax({
		url : url,
		data : $('#search_form').serialize()+'&page='+page+'&rows='+getCookie('rows')+'&sort='+(sort||'')+'&order='+(order||''),
		type : "POST",
		beforeSend: function(){
			loadi = layer.load(1);
			$('#tablelist').html('');
			$(".pagelist").html('');
		},
		success : function(result) {
			layer.close(loadi);
			 if(result.totalCount>0){
				 $(".pagelist").getLinkStr({totalPage:result.totalPage,totalCount:result.totalCount,page:result.page,pageSize:result.pageSize});
				 $('#tablelist').html(template('tableContent', result));
			 }else{
				 norecord();
			 }
		}
	});
}
function norecord(){
	$("#tablelist").html("<tr><td height='40px;' colspan='100'><span>抱歉！没有记录 。。。</span></td></tr>");
}
$(function(){
	order = $(".ltable").attr('order');
	sort = $(".ltable").attr('sort');
	pagination();
	$("#searchBtn").click(function(){
		pagination();
	});
	$("#clearBtn").click(function(){
		$("#search_form")[0].reset();
		location.reload();
	});
	$(".ltable").find('th').each(function(){
		var tSort = $(this).attr('sort');
		if(tSort){
			var trName =$(this).html();
			$(this).attr('class','sort');
			$(this).html('<div class=""><span>'+trName+'</span><span class="sort-icon">&nbsp;</span></div>');
			//绑定事件
			$(this).click(function(){
				sort = tSort;
				var tOrder = $(this).attr('order');
				if(tOrder){
					if('desc' == tOrder){
						order = tOrder;
						$(this).attr('order','asc');
						$(this).find('div').first().attr('class','sort-desc');
					}
					else if('asc' == order){
						ordert = tOrder;
						$(this).attr('order','desc');
						var trName =$(this).html();
						$(this).find('div').first().attr('class','sort-asc');
					}else{
						order = 'asc';
						$(this).attr('order','desc');
						var trName =$(this).html();
						$(this).find('div').first().attr('class','sort-asc');
					}
				}else{
					order = 'asc';
					$(this).attr('order','desc');
					var trName =$(this).html();
					$(this).find('div').first().attr('class','sort-asc');
				}
				clearOther(this);
				pagination();
			});
		}
	});
});

function clearOther(obj){
	$(obj).attr('class','sort order');
	$(".ltable").find('th').each(function(){
		if($(this).attr('sort') && $(this).attr('sort') != $(obj).attr('sort')){
			$(this).attr('class','sort');
			$(this).find('div').first().attr('class','');
		}
	});	
}

function clearSort(){
	$(".ltable").find('th').each(function(){
		if($(this).attr('sort')){
			$(this).attr('class','sort');
			$(this).find('div').first().attr('class','');
		}
	});	
}