<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>SUI</title>
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
    <div class="content">
        <header class="bar bar-nav">
            <a class="button button-link button-nav pull-left" href="list.html?bookId=${entity.bookId}" data-no-cache="true">
                <span class="icon icon-left"></span>
                章节列表
            </a>
            <a class="button button-link button-nav pull-right" data-no-cache="true" href="info.html?bookId=${entity.bookId}&no=${entity.no + 1}">
                下一章
                <span class="icon icon-right"></span>
            </a>
            <h1 class="title">${entity.title}</h1>
        </header>
        <div class="content infinite-scroll" data-distance="100">
            ${entity.content}
        </div>
    </div>
</div>

</div>
</body>
<script>
    $.config = {router: false}
    $.init();
</script>