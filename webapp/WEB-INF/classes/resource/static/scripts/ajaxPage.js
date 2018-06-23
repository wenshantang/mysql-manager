function changeRows(){
	var txtPageNum =$("#txtPageNum").val();
	addCookie("rows",txtPageNum,0);
	pagination(1);
}

function doJump(page,toatlPage){
	var jump =$("#jump").val();
	if(jQuery.trim(jump).length == 0||parseInt(jump) < 1){
		$("#jump").focus();
		$("#jump").attr("value",'');
	} else{
		if(jump.search("^-?\\d+$") != 0){
			$("#jump").focus();
			$("#jump").attr("value",'');
		}
		if(jump>toatlPage){
			jump = toatlPage;
		}
		if(page!=jump){
			pagination(jump);
		}
	}
}
(function(jQuery) {
	jQuery.fn.getLinkStr = function(pageInfo) {
		pageInfo = jQuery.extend({
			page:1,//当前页
			totalPage:10,//总页数
			pageSize:10,//总页数
			totalCount:10,//总记录
			breakpage:8,//总页数<=该值时，全部显示
			isShowPage:true,//是否显示总页数，默认显示
			isShowJump:true//是否显示跳到第几页，默认显示
		}, pageInfo);
		var jsfunction = pageInfo.jsfunction;
		$obj = this;
		var createPage = function(num){
			if(pageInfo.page == num){
				return "<span class='current'>"+num+"</span>";
			}
			else{
				return "<a page='"+num+"'>" + num + "</a>";
			}
		};
		
		jQuery(this).html("");
		var page = parseInt(pageInfo.page);
		var totalPage = parseInt(pageInfo.totalPage);
		var htmlPage ='<div class="l-btns">';
			htmlPage +='<span>显示</span>';
			htmlPage +='<input name="txtPageNum" type="text" value="'+pageInfo.pageSize+'" onchange="javascript:changeRows();" id="txtPageNum" class="pagenum" onkeydown="return checkNumber(event);" />';
			htmlPage +='<span>条/页</span>';
			htmlPage +='</div>';
			htmlPage +='<div id="PageContent" class="default">';
			htmlPage +='<span>共'+pageInfo.totalCount+'条记录</span>';
			
		if(pageInfo.page>1){
			htmlPage += "<a page='"+(page-1)+"'>&laquo;上一页</a>";
		}
		
		//当总页数小于等于breakpage值时，页码全部显示
		if(totalPage <= pageInfo.breakpage){
			for(var i=1;i<=totalPage;i++){
				htmlPage += createPage(i);
			}
		}
		else{
			if(page <= 4){
				for(var i=1;i<page + 4;i++){
					htmlPage += createPage(i);
				}
				htmlPage += "<span>...</span>";
				htmlPage += createPage(totalPage);
			}
			else if(page > 4 && page <= totalPage - 4){
				htmlPage += createPage(1);
				htmlPage += "<span>...</span>";
				for(var i=page-2;i<page+3;i++){
					htmlPage += createPage(i);
				}
				htmlPage += "<span>...</span>";
				htmlPage += createPage(totalPage);	
			}
			else{
				htmlPage += createPage(1);
				htmlPage += "<span>...</span>";
				for(var i=page-2;i<=totalPage;i++){
					htmlPage += createPage(i);
				}
			}
		}
		if(pageInfo.page<pageInfo.totalPage){
			htmlPage += "<a page='"+(page+1)+"'>下一页&raquo;</a>";
		}
		
		if(pageInfo.isShowJump && pageInfo.totalPage>20){
			htmlPage +='<span style="border:0px solid #ddd;border-left:1px solid #ddd" >';
			htmlPage +='<input id="jump" type="text" size="3" value="'+page+'" class="pagenum" onKeyDown="javascript:if(event.keyCode==13) doJump('+page+','+pageInfo.totalPage+');"/>';
			htmlPage +='</span>';
			htmlPage += "<button onclick='doJump("+page+","+pageInfo.totalPage+");'>GO</button>";
		}
		htmlPage +='</div>';
		jQuery(this).html(htmlPage);
		$('#PageContent a').click(function(){
			pagination($(this).attr('page'));
		});
	};
})(jQuery);