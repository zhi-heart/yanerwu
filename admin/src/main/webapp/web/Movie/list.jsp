<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>电影列表 - 列表</title>
</head>
<body>
<!-- 不要滚动条 -->
<div class="pageHeader" style="overflow-x:hidden">
    <div class="pageHeader">
        <form id="searchForm" onsubmit="return navTabSearch(this);" action="movie-search.html" method="post"
              rel="pagerForm">
            <div class="searchBar">
                <table class="searchContent">
                    <tr>
                        <td>
                            关键字: <input type="text" name="searchKey" class="textInput" value="${searchKey}"/>
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li>
                            <div class="buttonActive">
                                <div class="buttonContent">
                                    <button type="submit">检索</button>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div class="panelBar">
            <ul class="toolBar">
                <li><a class="add" href="Upower/info.html" target="dialog"><span>添加</span></a></li>
            </ul>
        </div>
        <table class="table" width="100%" layoutH="138">
            <thead>
            <tr>
                <!-- 不给宽带自适应 -->
                <th align="center"><input type="checkbox" class="checkboxCtrl" group="items" /></th>
                <th align="center">片名</th>
                <th align="center">状态</th>
                <th align="center">分类</th>
                <th align="center">地区</th>
                <th align="center">主演</th>
                <th align="center" style='max-width:100px;'>概要</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${page.result}">
                <tr target="id" rel="${item.id}">
                    <td><label><input type="checkbox" name="items" rel="${item.id}" value="${item.id}" /></label></td>
                    <td>${item.name}</td>
                    <td>${item.status}</td>
                    <td>${item.type}</td>
                    <td>${item.area}</td>
                    <td>${item.actor}</td>
                    <td style='max-width:100px;'>${item.summary}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <!-- 分页 -->
        <form id="pagerForm" method="post" action="movie-search.html" rel="searchForm">
            <input type="hidden" name="pageNum" value="1"/> <!--【必须】value=1可以写死-->
            <input type="hidden" name="numPerPage" value="${page.numPerPage}"/> <!--【可选】每页显示多少条-->
        </form>
        <%@ include file="/dwz/js/page.jsp" %>
    </div>
</body>
</html>