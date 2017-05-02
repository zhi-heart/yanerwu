 
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"         uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>权限管理 - 详细信息</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="Upower/info.html" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
				<div class="pageFormContent" layoutH="56">
			        <input type="hidden" name="id" value="${upower.id}" />
					<div class="unit">
						<label>权限序号：</label>
                            <input type="text" name="powerId" class="textInput number required" value="${entity.powerId}" />
					</div>
					<div class="unit">
						<label>权限名：</label>
                            <input type="text" name="name" class="textInput required" value="${entity.name}" />
					</div>
					<div class="unit">
						<label>权限路径：</label>
                            <input type="text" name="powerPath" class="textInput required" value="${entity.powerPath}" />
					</div>
					<div class="unit">
						<label>上级权限id：</label>
                            <input type="text" name="parentUpowerId" class="textInput number required" value="${entity.parentUpowerId}" />
					</div>
					<div class="unit">
						<label>类别,0:目录,1:权限,2:子权限：</label>
                            <input type="text" name="powerType" class="textInput number required" value="${entity.powerType}" />
					</div>
					<div class="unit">
						<label>备注：</label>
                            <input type="text" name="remark" class="textInput " value="${entity.remark}" />
					</div>
				</div>

				<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
					</ul>
				</div>
			</form>
		</div>
	</body>
</html>