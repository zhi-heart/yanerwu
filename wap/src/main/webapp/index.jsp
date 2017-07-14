<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>首页</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="/js/sm.css">
    <script type='text/javascript' src='/js/zepto.js' charset='utf-8'></script>
    <script type='text/javascript' src='/js/sm.js' charset='utf-8'></script>
</head>
<body>
<div class="page-group">
    <div id="page-infinite-scroll-bottom" class="page">
        <form method="post" action="index.html">
            <div class="bar bar-header-secondary" style="top: 0rem;">
                <div class="searchbar">
                    <a class="searchbar-cancel">取消</a>

                    <div class="search-input">
                        <label class="icon icon-search" for="search"></label>
                        <input type="search" name="name" id='search' value="${name}" placeholder='输入关键字...'/>
                    </div>
                </div>
            </div>
        </form>
        <div class="content" style="top: 2.2rem;">
            <div class="list-block media-list" style="margin: 0;">
                <ul>
                    <c:forEach var="item" items="${page.result}">
                        <li>
                            <a href="BookDetail/list.html?bookId=${item.id}"  class="external item-link item-content">
                                <div class="item-media"><img
                                        src="http://qidian.qpic.cn/qdbimg/349573/${item.qidianId}/150"
                                        width="80"></div>
                                <div class="item-inner">
                                    <div class="item-title-row">
                                        <div class="item-title">${item.name}</div>
                                        <div class="item-after">${item.author}</div>
                                    </div>
                                    <div class="item-subtitle">${item.type}</div>
                                    <div class="item-text">${item.summary}</div>
                                </div>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    //无限滚动
    $(document).on("pageInit", "#page-infinite-scroll-bottom", function (e, id, page) {
        var loading = false;
        // 每页多少条
        var numPerPage = 20;
        // 最多可加载的条目
        var maxItems = ${page.totalCount};
        var pageNum = $('.list-container li').length;

        function addItems(number, lastIndex) {
            // 生成新条目的HTML
            var html = '';
            $.getJSON("json-list.html?numPerPage=20&pageNum=" + lastIndex, function (data) {
                for (var i in data) {
                    html += '<li class="item-content"><div class="item-inner"><div class="item-title">' + data[i].title + '</div></div></li>';
                }
                // 添加新条目
                $('.infinite-scroll .list-container').append(html);
                loading = false;
            });
        }

        $(page).on('infinite', function () {
            // 如果正在加载，则退出
            if (loading) {
                return;
            }
            // 设置flag
            loading = true;

            // 更新最后加载的序号
            pageNum = $('.list-container li').length;
            if (pageNum >= maxItems) {
                // 加载完毕，则注销无限加载事件，以防不必要的加载
                $.detachInfiniteScroll($('.infinite-scroll'));
                // 删除加载提示符
                $('.infinite-scroll-preloader').remove();
                return;
            }
            addItems(numPerPage, pageNum / numPerPage + 1);
            $.refreshScroller();
        });
    });
    $.config = {router: false}
    $.init();
</script>