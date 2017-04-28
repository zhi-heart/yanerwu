<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>网贷之家数据监控管理中心</title>
    <link type="image/x-icon" href="img/favicon.ico" rel="shortcut icon">

    <!-- 日历框 -->
    <script src="js/My97DatePickerBeta/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

    <!-- dwz -->
    <link href="css/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="css/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="css/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>

    <script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="js/jquery.validate.min.js" type="text/javascript"></script>

    <script src="js/dwz.min.js?v=1.0.0.1" type="text/javascript"></script>
    <script src="js/dwz.regional.zh.js" type="text/javascript"></script>

    <!-- 上传 -->
    <script type="text/javascript" src="js/jquery.form.js"></script>

    <!-- 图标 -->
    <script src="http://echarts.baidu.com/build/dist/echarts-all.js"></script>
    <script type="text/javascript">
        $(function(){
            DWZ.init("css/themes/dwz.frag.xml", {
                //		loginUrl:"login.html", loginTitle:"登录",	// 弹出登录对话框
                loginUrl:"${path}/index.html",	// 跳到登录页面
                statusCode:{ok:200, error:300, timeout:302}, //【可选】
                pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
                keys: {statusCode:"statusCode", message:"message"}, //【可选】
                ui:{hideMode:'offsets'}, //【可选】hideMode:navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
                debug:false,	// 调试模式 【true|false】
                callback:function(){
                    initEnv();
                    $("#themeList").theme({themeBase:"${path}/dwz/themes"}); // themeBase 相对于index页面的主题base路径
                }
            });
        });
        //循环执行，每隔3秒钟执行一次showalert（）
        window.setInterval(showalert, 3000);
        function showalert(){
            //alert("啊啊啊啊");
        }
    </script>
    <!-- dwz end -->
</head>
<body>
<div id="layout">
    <div id="header">
        <div class="headerNav">
            <a class="logo" href="${path}">标志</a>
            <ul class="nav">
                <li><a target="_blank" href="http://shuju.wdzj.com">数据首页</a></li>
                <li><a target="_blank" href="http://192.168.8.8:8081">数据后台</a></li>
                <li><a target="_blank" href="http://192.168.17.2:8085">版本发布</a></li>
                <li><a target="_blank" href="http://www.wdzj.com/wdzj_admin/wdzjsyslogin">主站后台</a></li>
                <li><a target="_blank" href="http://192.168.8.10:15672/">RabbitMQ</a></li>

                <li><a href="user-info.html" target="dialog">欢迎您,${user.name}</a></li>
                <li><a href="http://192.168.23.95:8060/logout?service=${basePath}/index.html">退出登录</a></li>
            </ul>
            <ul class="themeList" id="themeList">
                <li theme="default"><div class="selected">蓝色</div></li>
                <li theme="green" class="green"><div>绿色</div></li>
                <li theme="red"><div>红色</div></li>
                <li theme="purple"><div>紫色</div></li>
                <li theme="silver"><div>银色</div></li>
                <li theme="azure"><div>天蓝</div></li>
            </ul>
        </div>

        <!-- navMenu -->

    </div>
    <!--顶部结束-->
    <!--菜单-->
    <div id="leftside">
        <div id="sidebar_s">
            <div class="collapse">
                <div class="toggleCollapse"><div></div></div>
            </div>
        </div>
        <div id="sidebar">
            <div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>
            <div class="accordion" fillSpace="sidebar">
                <div class="accordionHeader">
                    <h2><span>Folder</span>主菜单</h2>
                </div>
                <div class="accordionContent">
                    <ul class="tree treeFolder">
                        <c:forEach var="item_dir" items="${user.powers}">
                            <c:if test="${item_dir.id eq item_dir.parentUPowerId && item_dir.powerType eq 0}">
                                <util:authPower powerPath="${item_dir.powerPath}">
                                    <li><a>${item_dir.name }</a>
                                </util:authPower>
                                <c:forEach var="item_power" items="${user.powers}">
                                    <c:if test="${item_power.id != item_power.parentUPowerId && item_dir.id eq item_power.parentUPowerId && item_power.powerType eq 1}">
                                        <ul>
                                            <util:authPower powerPath="${item_power.powerPath}">
                                                <li><a href="${path}${item_power.powerPath}" target="navTab" rel="${item_power.id}">${item_power.name}</a></li>
                                            </util:authPower>
                                        </ul>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${item_dir.id eq item_dir.parentUPowerId && item_dir.powerType eq 0}">
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
                        <li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
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
                    <iframe width="100%" height="1000px" class="share_self"  frameborder="0" scrolling="no" src="summary.html" name="rightFrame" id="rightFrame"></iframe>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>