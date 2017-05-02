<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"         uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>权限管理 - 列表</title>
	</head>
	<body>
		<!-- 不要滚动条 -->
		<div class="pageHeader" style="overflow-x:hidden">
		<div class="pageHeader">
			<form id="searchForm" onsubmit="return navTabSearch(this);" action="Upower/list.html" method="post" rel="pagerForm">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							序号: <input type="text" name="id" class="textInput"  value="${query.id}"/>
						</td>
						<td>
							权限序号: <input type="text" name="powerId" class="textInput"  value="${query.powerId}"/>
						</td>
						<td>
							权限名: <input type="text" name="name" class="textInput"  value="${query.name}"/>
						</td>
						<td>
							权限路径: <input type="text" name="powerPath" class="textInput"  value="${query.powerPath}"/>
						</td>
						<td>
							上级权限id: <input type="text" name="parentUpowerId" class="textInput"  value="${query.parentUpowerId}"/>
						</td>
						<td>
							类别,0:目录,1:权限,2:子权限: <input type="text" name="powerType" class="textInput"  value="${query.powerType}"/>
						</td>
						<td>
							备注: <input type="text" name="remark" class="textInput"  value="${query.remark}"/>
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
					<li><a class="add" href="web/Upower/add.jsp" target="dialog"><span>添加</span></a></li>
					-->
                    <li><a class="add" href="Upower/info.html" target="dialog"><span>添加</span></a></li>
					<li><a class="edit" href="Upower/info.html?id={id}" target="dialog"><span>编辑</span></a></li>
					<!--<li><a class="delete" href="Upower/delete.html?id={id}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>-->
					<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="items" href="u_power/delete.html" class="delete"><span>批量删除</span></a></li>
					<!--
					<li class="line">line</li>
					<li><a class="icon" href="/u_power/expExcel.html" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
					-->
				</ul>
			</div>
			<table class="table" width="100%" layoutH="138">
				<thead>
					<tr>
						<!-- 不给宽带自适应 -->
						<th align="center"><input type="checkbox" class="checkboxCtrl" group="items" /></th>
						<th align="center">序号</th>
						<th align="center">权限序号</th>
						<th align="center">权限名</th>
						<th align="center">权限路径</th>
						<th align="center">上级权限id</th>
						<th align="center">类别,0:目录,1:权限,2:子权限</th>
						<th align="center">备注</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${page.result}">
						<tr target="id" rel="${item.id}">
				        <td><label><input type="checkbox" name="items" rel="${item.id}" value="${item.id}" /></label></td>
						<td>${item.id}</td>
						<td>${item.powerId}</td>
						<td>${item.name}</td>
						<td>${item.powerPath}</td>
						<td>${item.parentUpowerId}</td>
						<td>${item.powerType}</td>
						<td>${item.remark}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<!-- 分页 -->
			<form id="pagerForm" method="post" action="Upower/list.html" rel="searchForm">
				<input type="hidden" name="pageNum" value="1" /> <!--【必须】value=1可以写死-->
				<input type="hidden" name="numPerPage" value="${page.numPerPage}" /> <!--【可选】每页显示多少条-->
			</form>
            <%@ include file="/dwz/js/page.jsp" %>
		</div>
	</body>
</html>