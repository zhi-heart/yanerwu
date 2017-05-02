 
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"         uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>用户管理 - 详细信息</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="Uuser/info.html" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
				<div class="pageFormContent" layoutH="56">
			        <input type="hidden" name="id" value="${uuser.id}" />
					<div class="unit">
						<label>登录名：</label>
                            <input type="text" name="loginName" class="textInput required" value="${entity.loginName}" />
					</div>
					<div class="unit">
						<label>登录密码：</label>
                            <input type="text" name="loginPwd" class="textInput required" value="${entity.loginPwd}" />
					</div>
					<div class="unit">
						<label>姓名：</label>
                            <input type="text" name="name" class="textInput required" value="${entity.name}" />
					</div>
					<div class="unit">
						<label>手机：</label>
                            <input type="text" name="phone" class="textInput " value="${entity.phone}" />
					</div>
					<div class="unit">
						<label>创建时间：</label>
                            <input type="text" name="createDate" class="textInput " value="${entity.createDate}" />
					</div>
					<div class="unit">
						<label>valid：</label>
                            <input type="text" name="valid" class="textInput " value="${entity.valid}" />
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