<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"         uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>用户管理 - 列表</title>
	</head>
	<body>
		<!-- 不要滚动条 -->
		<div class="pageHeader" style="overflow-x:hidden">
		<div class="pageHeader">
			<form id="searchForm" onsubmit="return navTabSearch(this);" action="Uuser/list.html" method="post" rel="pagerForm">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							序号: <input type="text" name="id" class="textInput"  value="${query.id}"/>
						</td>
						<td>
							登录名: <input type="text" name="loginName" class="textInput"  value="${query.loginName}"/>
						</td>
						<td>
							登录密码: <input type="text" name="loginPwd" class="textInput"  value="${query.loginPwd}"/>
						</td>
						<td>
							姓名: <input type="text" name="name" class="textInput"  value="${query.name}"/>
						</td>
						<td>
							手机: <input type="text" name="phone" class="textInput"  value="${query.phone}"/>
						</td>
						<td>
							创建时间: <input type="text" name="createDate" class="textInput"  value="${query.createDate}"/>
						</td>
						<td>
							valid: <input type="text" name="valid" class="textInput"  value="${query.valid}"/>
						</td>
					</tr>
				</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
				</ul>
			</div>
			</div>
			</form>
		</div>
		<div class="pageContent">
			<div class="panelBar">
				<ul class="toolBar">
					<!-- height 26px  -->
                    <!--
					<li><a class="add" href="web/Uuser/add.jsp" target="dialog"><span>添加</span></a></li>
					-->
                    <li><a class="add" href="Uuser/info.html" target="dialog"><span>添加</span></a></li>
					<li><a class="edit" href="Uuser/info.html?id={id}" target="dialog"><span>编辑</span></a></li>
					<!--<li><a class="delete" href="Uuser/delete.html?id={id}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>-->
					<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="items" href="u_user/delete.html" class="delete"><span>批量删除</span></a></li>
					<!--
					<li class="line">line</li>
					<li><a class="icon" href="/u_user/expExcel.html" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
					-->
				</ul>
			</div>
			<table class="table" width="100%" layoutH="138">
				<thead>
					<tr>
						<!-- 不给宽带自适应 -->
						<th align="center"><input type="checkbox" class="checkboxCtrl" group="items" /></th>
						<th align="center">序号</th>
						<th align="center">登录名</th>
						<th align="center">登录密码</th>
						<th align="center">姓名</th>
						<th align="center">手机</th>
						<th align="center">创建时间</th>
						<th align="center">valid</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${page.result}">
						<tr target="id" rel="${item.id}">
				        <td><label><input type="checkbox" name="items" rel="${item.id}" value="${item.id}" /></label></td>
						<td>${item.id}</td>
						<td>${item.loginName}</td>
						<td>${item.loginPwd}</td>
						<td>${item.name}</td>
						<td>${item.phone}</td>
						<td>${item.createDate}</td>
						<td>${item.valid}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<!-- 分页 -->
			<form id="pagerForm" method="post" action="Uuser/list.html" rel="searchForm">
				<input type="hidden" name="pageNum" value="1" /> <!--【必须】value=1可以写死-->
				<input type="hidden" name="numPerPage" value="${page.numPerPage}" /> <!--【可选】每页显示多少条-->
			</form>
            <%@ include file="/dwz/js/page.jsp" %>
		</div>
	</body>
</html>