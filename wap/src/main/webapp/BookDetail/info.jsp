<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${entity.title} - ${bookName} - 燕儿坞</title>
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
    <div class="content">
        <header class="bar bar-nav">
            <a class="button button-link button-nav pull-left external" href="/book/${entity.bookId}.html" data-no-cache="true">
                <span class="icon icon-left"></span>
                章节列表
            </a>
            <a class="button button-link button-nav pull-right external" data-no-cache="true" href="/book/${entity.bookId}/${entity.no + 1}.html">
                下一章
                <span class="icon icon-right"></span>
            </a>
            <h1 class="title">${entity.title}</h1>
        </header>
        <div class="content infinite-scroll" data-distance="100" style="margin: .5rem;">
            ${entity.content}
            <c:if test="${'' != entity.comment}">
                <p style="color: #FFBD9D">精彩评论</p>
                ${entity.comment}
            </c:if>
        </div>
    </div>
</div>

</div>
</body>
<script>
    $.config = {router: false}
</script>
<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?aa4ea6870943ce2e6c5ede5e9291ae72";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>
