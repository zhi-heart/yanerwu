<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="util"	uri="/WEB-INF/tld/util"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>哇哦</title>

    <link href="dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
    <link href="dwz/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
    <!--[if IE]>
    <link href="dwz/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
    <![endif]-->

    <!--[if lt IE 9]>
    <script src="dwz/js/speedup.js" type="text/javascript"></script>
    <script src="dwz/js/jquery-1.11.3.min.js" type="text/javascript"></script><![endif]-->
    <!--[if gte IE 9]><!-->
    <script src="dwz/js/jquery-2.1.4.min.js" type="text/javascript"></script><!--<![endif]-->

    <script src="dwz/js/jquery.cookie.js" type="text/javascript"></script>
    <script src="dwz/js/jquery.validate.js" type="text/javascript"></script>
    <script src="dwz/js/jquery.bgiframe.js" type="text/javascript"></script>
    <script src="dwz/xheditor/xheditor-1.2.2.min.js" type="text/javascript"></script>
    <script src="dwz/xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
    <script src="dwz/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>

    <!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
    <script type="text/javascript" src="dwz/chart/raphael.js"></script>
    <script type="text/javascript" src="dwz/chart/g.raphael.js"></script>
    <script type="text/javascript" src="dwz/chart/g.bar.js"></script>
    <script type="text/javascript" src="dwz/chart/g.line.js"></script>
    <script type="text/javascript" src="dwz/chart/g.pie.js"></script>
    <script type="text/javascript" src="dwz/chart/g.dot.js"></script>

    <!--
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=6PYkS1eDz5pMnyfO0jvBNE0F"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>
    -->

    <script src="dwz/js/dwz.core.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.util.date.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.validate.method.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.barDrag.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.drag.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.tree.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.accordion.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.ui.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.theme.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.switchEnv.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.alertMsg.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.contextmenu.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.navTab.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.tab.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.resize.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.dialog.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.dialogDrag.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.sortDrag.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.cssTable.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.stable.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.taskBar.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.ajax.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.pagination.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.database.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.datepicker.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.effects.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.panel.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.checkbox.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.history.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.combox.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.file.js" type="text/javascript"></script>
    <script src="dwz/js/dwz.print.js" type="text/javascript"></script>

    <!-- 可以用dwz.min.js替换前面全部dwz.*.js (注意：替换时下面dwz.regional.zh.js还需要引入)
    <script src="bin/dwz.min.js" type="text/javascript"></script>
    -->
    <script src="dwz/js/dwz.regional.zh.js" type="text/javascript"></script>

    <script type="text/javascript">
        $(function () {
            DWZ.init("dwz/dwz.frag.xml", {
//		loginUrl:"login_dialog.html", loginTitle:"登录",	// 弹出登录对话框
                loginUrl: "login.html",	// 跳到登录页面
                statusCode: {ok: 200, error: 300, timeout: 301}, //【可选】
                pageInfo: {
                    pageNum: "pageNum",
                    numPerPage: "numPerPage",
                    orderField: "orderField",
                    orderDirection: "orderDirection"
                }, //【可选】
                keys: {statusCode: "statusCode", message: "message"}, //【可选】
                ui: {hideMode: 'offsets'}, //【可选】hideMode:navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
                debug: false,	// 调试模式 【true|false】
                callback: function () {
                    initEnv();
                    $("#themeList").theme({themeBase: "dwz/themes"}); // themeBase 相对于index页面的主题base路径
                }
            });
        });
    </script>
</head>
<body>
<div id="layout">
    <div id="header">
        <div class="headerNav">
            <a class="logo" href="http://j-ui.com">标志</a>
            <ul class="nav">
                <li id="switchEnvBox"><a href="javascript:">（<span>北京</span>）切换城市</a>
                    <ul>
                        <li><a href="sidebar_1.html">北京</a></li>
                        <li><a href="sidebar_2.html">上海</a></li>
                        <li><a href="sidebar_2.html">南京</a></li>
                        <li><a href="sidebar_2.html">深圳</a></li>
                        <li><a href="sidebar_2.html">广州</a></li>
                        <li><a href="sidebar_2.html">天津</a></li>
                        <li><a href="sidebar_2.html">杭州</a></li>
                    </ul>
                </li>
                <li><a href="changepwd.html" target="dialog" width="600">设置</a></li>
                <li><a href="http://weibo.com/dwzui" target="_blank">微博</a></li>
                <li><a href="http://cas.wdzj.com:8080/logout?service=http://admin.wdzj.com:9191">退出</a></li>
            </ul>
            <ul class="themeList" id="themeList">
                <li theme="default">
                    <div class="selected">蓝色</div>
                </li>
                <li theme="green">
                    <div>绿色</div>
                </li>
                <!--<li theme="red"><div>红色</div></li>-->
                <li theme="purple">
                    <div>紫色</div>
                </li>
                <li theme="silver">
                    <div>银色</div>
                </li>
                <li theme="azure">
                    <div>天蓝</div>
                </li>
            </ul>
        </div>

    </div>

    <!--菜单-->
    <div id="leftside">
        <div id="sidebar_s">
            <div class="collapse">
                <div class="toggleCollapse">
                    <div></div>
                </div>
            </div>
        </div>
        <div id="sidebar">
            <div class="toggleCollapse"><h2>主菜单</h2>

                <div>收缩</div>
            </div>
            <div class="accordion" fillSpace="sidebar">
                <div class="accordionHeader">
                    <h2><span>Folder</span>主菜单</h2>
                </div>
                <div class="accordionContent">
                    <ul class="tree treeFolder">
                        <c:forEach var="item_dir" items="${user.upowers}">
                            <c:if test="${item_dir.id eq item_dir.parentUpowerId && item_dir.powerType eq 0}">
                                <util:authPower powerPath="${item_dir.powerPath}">
                                    <li><a>${item_dir.name }</a>
                                </util:authPower>
                                <c:forEach var="item_power" items="${user.upowers}">
                                    <c:if test="${item_power.id != item_power.parentUpowerId && item_dir.id eq item_power.parentUpowerId && item_power.powerType eq 1}">
                                        <ul>
                                            <util:authPower powerPath="${item_power.powerPath}">
                                                <li><a href="http://www.baidu.com${item_power.powerPath}"
                                                       target="navTab"
                                                       rel="${item_power.id}">${item_power.name}</a></li>
                                            </util:authPower>
                                        </ul>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${item_dir.id eq item_dir.parentUpowerId && item_dir.powerType eq 0}">
                                <util:authPower powerPath="${item_dir.powerPath}">
                                    </li>
                                </util:authPower>
                            </c:if>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>


    <!--菜单右边的iframe页面-->
    <div id="container">
        <div id="navTab" class="tabsPage">
            <div class="tabsPageHeader">
                <div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
                    <ul class="navTab-tab">
                        <li tabid="main" class="main"><a href="javascript:;"><span><span
                                class="home_icon">我的主页</span></span></a></li>
                    </ul>
                </div>
                <div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
                <div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
                <div class="tabsMore">more</div>
            </div>
            <ul class="tabsMoreList">
                <li><a href="javascript:;">我的主页</a></li>
            </ul>
            <div class="navTab-panel tabsPageContent layoutBox">
                <div class="page unitBox" style="text-align: center;">
                    <iframe width="100%" height="1000px" class="share_self" frameborder="0" scrolling="no"
                            src="summary.html" name="rightFrame" id="rightFrame"></iframe>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>