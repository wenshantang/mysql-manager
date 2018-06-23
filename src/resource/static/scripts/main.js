//设置下拉菜单
function setdownArrow() {
    //$(".downArrow").offset().left = $(".tabs li:last").offset().left;
    var lilfet = $(".tabs li:last").offset().left + 1;
    var liwidth = $(".tabs li:last").width();
    $(".downArrow").css("left", (lilfet + liwidth) + "px");
}
function addTab(title, url) {
    if ($('#tabs').tabs('exists', title)) {
        $('#tabs').tabs('select', title);//选中并刷新
        var currTab = $('#tabs').tabs('getSelected');
        var url = $(currTab.panel('options').content).attr('src');
        if (url != undefined && currTab.panel('options').title != '首页') {
            $('#tabs').tabs('update', {
                tab: currTab,
                options: {
                    content: createFrame(url)
                }
            })
        }
    } else {
        var content = createFrame(url);
        $('#tabs').tabs('add', {
            title: title,
            content: content,
            closable: true
        });
    }
    tabClose();
    if (title != '首页') {
        setdownArrow();
    }
}
//设置选中tab
function SelectTable(title, url, topTitle) {
    if ($('#tabs').tabs('exists', title)) {
        var currTab = $('#tabs').tabs('select', title);
    } else {
        addTab1(title, url, $("div[topname='" + topTitle + "']").attr("topvalue"));
    }
}
function addTab1(title, url, argId) {
    var varClosable = true;
    if (title == "首页" ) {
        varClosable = false;
    }
    if ($('#tabs').tabs('exists', title)) {
        $('#tabs').tabs('select', title);//选中并刷新
        var currTab = $('#tabs').tabs('getSelected');
        var urlOld = $(currTab.panel('options').content).attr('src');
        if (urlOld != undefined && currTab.panel('options').title != '首页') {
            $('#tabs').tabs('update', {
                tab: currTab,
                options: {
                    content: createFrame(url)
                }
            });
        }
    } else {
        var content = createFrame(url);
        $('#tabs').tabs('add', {
            title: title,
            content: content,
            closable: varClosable,
            id: argId
        });
    }
    tabClose();
    if (title != '首页') {
        setdownArrow();
    }
}
function childAddTab(title, url, topTitle) {
    addTab1(title, url, $("div[topname='" + topTitle + "']").attr("topvalue"));
}
function createFrame(url) {
    var s = '<iframe frameborder="0"  src="' + url + '" height="100%" width="100%" center no-repeat"></iframe>';
    return s;
}


function CloseTabByTitle(title) {
    $('#tabs').tabs('close', title);
    setdownArrow();
}
function childAddTabAndCloseTab(title, url, topTitle, closeTitle) {
    $('#tabs').tabs('close', closeTitle);
    addTab1(title, url, $("div[topname='" + topTitle + "']").attr("topvalue"));
}
function tabCloseFun(obj) {
    var subtitle = $(obj).parent().children(".tabs-inner").children(".tabs-title").text();
    return;
}
function tabClose() {
    /*单击TAB选项卡*/
    $(".tabs-inner").click(function () {
        var subtitle = $(this).children(".tabs-closable").text();
        if (subtitle != null && subtitle != '') {
            var tab = $('#tabs').tabs('getTab', subtitle);
        }
    });
    /*关闭TAB选项卡*/
    $(".tabs-close").click(function () {
        var subtitle = $(this).parent().find(".tabs-closable").text();
        $('#tabs').tabs('close', subtitle);
        setdownArrow();
        return false;
    });
    /*双击关闭TAB选项卡*/
    $(".tabs-inner").dblclick(function () {
        var subtitle = $(this).children(".tabs-closable").text();
        $('#tabs').tabs('close', subtitle);
        setdownArrow();
    });
}
function UpdateTabFirst() {
    var firstTab = $('#tabs').tabs('getTab', '首页');
    var url = $(firstTab.panel('options').content).attr('src');
    var content = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
    if (url != undefined) {
        $('#tabs').tabs('update', {
            tab: firstTab,
            options: {
                content: content
            }
        });

        var ieset = navigator.userAgent;
        if (ieset.indexOf("MSIE 6.0") > -1 || ieset.indexOf("Chrome") > -1) {
            var currTab1 = $('#tabs').tabs('getTab', '首页');
            setTimeout(function () { refreshTab(currTab1) }, 0);
        }
    }
}
function refreshTab(refresh_tab) {
    if (refresh_tab && refresh_tab.find('iframe').length > 0) {
        var _refresh_ifram = refresh_tab.find('iframe')[0];
        var refresh_url = _refresh_ifram.src;
        _refresh_ifram.contentWindow.location.href = refresh_url;
    }
}
//绑定菜单事件
function tabCloseEven() { 
    //刷新
    $('#mm-tabupdate').click(function () {
        var currTab = $('#tabs').tabs('getSelected');
        var url = $(currTab.panel('options').content).attr('src');
        if (url != undefined) {
            $('#tabs').tabs('update', {
                tab: currTab,
                options: {
                    content: createFrame(url)
                }
            });
        }
    });
    //关闭当前
    $('#mm-tabclose').click(function () {
        var currtab_title = $('#mm').data("currtab");
        $('#tabs').tabs('close', currtab_title);
        setdownArrow();
    });
    //全部关闭
    $('#mm-tabcloseall').click(function () {
        $('.tabs-inner span').each(function (i, n) {
            var t = $(n).text();
            if (t != '首页') {
                $('#tabs').tabs('close', t);
            }
        });
        setdownArrow();
    });
    //关闭除当前之外的TAB
    $('#mm-tabcloseother').click(function () {
        var prevall = $('.tabs-selected').prevAll();
        var nextall = $('.tabs-selected').nextAll();
        if (prevall.length > 0) {
            prevall.each(function (i, n) {
                var t = $('a:eq(0) span', $(n)).text();
                if (t != '首页') {
                    $('#tabs').tabs('close', t);
                }
            });
        }
        if (nextall.length > 0) {
            nextall.each(function (i, n) {
                var t = $('a:eq(0) span', $(n)).text();
                if (t != '首页') {
                    $('#tabs').tabs('close', t);
                }
            });
        }
        setdownArrow();
        return false;
    });
    //关闭当前右侧的TAB
    $('#mm-tabcloseright').click(function () {
        var nextall = $('.tabs-selected').nextAll();
        if (nextall.length == 0) {
            //msgShow('系统提示','后边没有啦~~','error');
            alert('后边没有啦~~');
            return false;
        }
        nextall.each(function (i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            $('#tabs').tabs('close', t);
        });
        return false;
    });
    //关闭当前左侧的TAB
    $('#mm-tabcloseleft').click(function () {
        var prevall = $('.tabs-selected').prevAll();
        if (prevall.length == 0) {
            alert('到头了，前边没有啦~~');
            return false;
        }
        prevall.each(function (i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            $('#tabs').tabs('close', t);
        });
        return false;
    });
}
$(function () {
    //检测IE
    if ('undefined' == typeof(document.body.style.maxHeight)) {
        window.location.href = 'static/ie6update.html';
    }
	eval("");
    tabCloseEven();
    //打开首页
    childAddTab('首页', 'admin/center', '');
    //关闭默认tab
    $('.tabs-inner span').each(function (i, n) {
        var t = $(n).text();
        if (t != '首页') {
            $('#tabs').tabs('close', t);
        }
    });
    loadMenuTree(); //加载管理首页左边导航菜单
});
//点击菜单打开窗口
function OpenWindow(obj) {
    var $this = $(obj);
    var href = $this.attr('src');
    var title = $this.text();
    addTab1(title, href, $this.attr('index'));
}

//加载管理首页左边导航菜单
function loadMenuTree() {
    //初始化导航菜单
    initMenuTree();
    //设置左边导航滚动条
    $("#main-bd").niceScroll({
        touchbehavior: false,
        cursorcolor: "#7C7C7C",
        cursoropacitymax: 0.6,
        cursorwidth: 5
    });
}
//初始化导航菜单

function initMenuTree() {
    //先清空NAV菜单内容
    $("#nav").html('');
    $("#sidebar-nav .list-box .list-group").each(function(i) {
        //添加菜单导航
        var navHtml = $('<li><i class="icon-' + i + '"></i><span>' + $(this).attr("name") + '</span></li>').appendTo($("#nav"));
        //默认选中第一项
        if (i == 0) {
            $(this).show();
            navHtml.addClass("selected");
        }
        //为菜单添加事件
        navHtml.click(function() {
            $("#nav li").removeClass("selected");
            $(this).addClass("selected");
            $("#sidebar-nav .list-box .list-group").hide();
            $("#sidebar-nav .list-box .list-group").eq($("#nav li").index($(this))).show();
        });
        //为H2添加事件
        $(this).children("h2").click(function() {
            if ($(this).next("ul").css('display') != 'none') {
                return false;
            }
            $(this).siblings("ul").slideUp(300);
            $(this).next("ul").slideDown(300);
        });

        //首先隐藏所有的UL
        $(this).find("ul").hide();
        //绑定树菜单事件.开始
        $(this).find("ul").each(function(j) { //遍历所有的UL
            //遍历UL第一层LI
            $(this).children("li").each(function() {
                liObj = $(this);
                //插入选中的三角
                var spanObj = liObj.children("a").children("span");
                $('<div class="arrow"></div>').insertBefore(spanObj); //插入到span前面
                //判断是否有子菜单和设置距左距离
                var parentExpandableLen = liObj.parent().parent().children("a").children(".expandable").length; //父节点的左距离
                if (liObj.children("ul").length > 0) { //如果有下级菜单
                    //删除链接，防止跳转
                    liObj.children("a").removeAttr("href");
                    //更改样式
                    liObj.children("a").addClass("pack");
                    //设置左距离
                    var lastExpandableObj;
                    for (var n = 0; n <= parentExpandableLen; n++) { //注意<=
                        lastExpandableObj = $('<div class="expandable"></div>').insertBefore(spanObj); //插入到span前面
                    }
                    //设置最后一个为闭合+号
                    lastExpandableObj.addClass("close");
                    //创建和设置文件夹图标
                    $('<div class="folder close"></div>').insertBefore(spanObj); //插入到span前面
                    //隐藏下级的UL
                    liObj.children("ul").hide();
                    //绑定单击事件
                    liObj.children("a").click(function() {
                        //搜索所有同级LI且有子菜单的左距离图标为+号及隐藏子菜单
                        $(this).parent().siblings().each(function() {
                            if ($(this).children("ul").length > 0) {
                                //设置自身的左距离图标为+号
                                $(this).children("a").children(".expandable").last().removeClass("open");
                                $(this).children("a").children(".expandable").last().addClass("close");
                                //隐藏自身子菜单
                                $(this).children("ul").slideUp(300);
                            }
                        });

                        //设置自身的左距离图标为-号
                        $(this).children(".expandable").last().removeClass("close");
                        $(this).children(".expandable").last().addClass("open");
                        //显示自身父节点的UL子菜单
                        $(this).parent().children("ul").slideDown(300);
                    });
                } else {
                    //设置左距离
                    for (var n = 0; n < parentExpandableLen; n++) {
                        $('<div class="expandable"></div>').insertBefore(spanObj); //插入到span前面
                    }
                    //创建和设置文件夹图标
                    $('<div class="folder open"></div>').insertBefore(spanObj); //插入到span前面
                    //绑定单击事件
                    liObj.children("a").click(function() {
                        //删除所有的选中样式
                        $("#sidebar-nav .list-box .list-group ul li a").removeClass("selected");
                        //自身添加样式
                        $(this).addClass("selected");
                    });
                }
            });
            //显示第一个UL
            if (j == 0) {
                $(this).show();
                //展开第一个菜单
                if ($(this).children("li").first().children("ul").length > 0) {
                    $(this).children("li").first().children("a").children(".expandable").last().removeClass("close");
                    $(this).children("li").first().children("a").children(".expandable").last().addClass("open");
                    $(this).children("li").first().children("ul").show();
                }
            }
        });
    });
}
