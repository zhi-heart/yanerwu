<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${bookName} - 燕儿坞</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
    <script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>

</head>
<body>

<div class="page-group">
    <div id="page-infinite-scroll-bottom-${bookId}" class="page page-current">
        <input type="hidden" id="bookId" name="bookId" value="${bookId}"/>
        <header class="bar bar-nav">
            <a class="button button-link button-nav pull-left external" data-no-cache="true" href="/index.html">
                <span class="icon icon-left"></span>
                主页
            </a>
            <a class="button button-link button-nav pull-right external"
               data-no-cache="true"
               href="/book/${bookId}.html?orderField=no&orderDirection=asc">
                升序
                <span class="icon icon-right"></span>
            </a>
            <h1 class="title">${bookName}</h1>
        </header>
        <div class="content infinite-scroll" data-distance="100">
            <div class="list-block" style="margin: 0;">
                <ul class="list-container">
                    <c:forEach var="item" items="${page.result}">
                        <a href="/book/${bookId}/${item.no}.html" class="item-link external" data-no-cache="true">
                            <li class="item-content">
                                <div class="item-inner">
                                    <div class="item-title">${item.title}</div>
                                </div>
                            </li>
                        </a>
                    </c:forEach>
                </ul>
            </div>
            <!-- 加载提示符 -->
            <div class="infinite-scroll-preloader">
                <div class="preloader">
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<script>
    //无限滚动
    $(document).on("pageInit", "#page-infinite-scroll-bottom-${bookId}", function (e, id, page) {
        var loading = false;
        // 每页多少条
        var numPerPage = 20;
        // 最多可加载的条目
        var maxItems = ${page.totalCount};
        var pageNum = $('.list-container li').length;
        var orderField = "${orderField}";
        var orderDirection = "${orderDirection}";

        function addItems(number, lastIndex) {
            // 生成新条目的HTML
            var html = '';
            var bookId = $("#bookId").val()

            $.post('json-list.html', {
                bookId: bookId,
                numPerPage: 20,
                pageNum: lastIndex,
                orderField: orderField,
                orderDirection: orderDirection
            }, function (dataStr) {
                var data = $.parseJSON(dataStr);
                for (var i in data) {
                    html += '<a href="/book/'+bookId+'/'+data[i].no+'.html" class="item-link external" data-no-cache="true"> <li class="item-content"> <div class="item-inner"> <div class="item-title">' + data[i].title + '</div> </div> </li> </a>';
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
<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?5e399d2db9d02e69dd72ebaa874fddc6";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>

