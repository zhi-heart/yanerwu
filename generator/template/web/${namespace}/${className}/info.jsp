<#include "/macro.include"/> <#include "/custom.include"/> <#assign
className = table.className> <#assign classNameLower =
className?uncap_first> <#assign actionExtension = "shtml">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"         uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>${table.remarks}详细</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="${table.sqlName}/info.html" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
				<div class="pageFormContent" layoutH="56">
					<#list table.columns as column>
					<#if column.pk>
			        <input type="hidden" name="${column.columnNameLower}" value="${r"$"}{${classNameLower}.${column.columnNameLower}}"/ >
			        </#if>
					<#if !column.pk>
					<div class="unit">
						<label><#if column.remarks != ''>${column.remarks}<#else>${column.columnNameLower}</#if>：</label>
						<input type="text" name="${column.columnNameLower}" id="${classNameLower}.${column.columnNameLower}" class="<#if column.isDateTimeColumn>date<#else>textInput</#if><#if column.nullable><#else> required</#if>" <#if column.isDateTimeColumn>readonly="readonly"</#if> value="${r"$"}{${classNameLower}.${column.columnNameLower}}" />
					</div>
					</#if>
					</#list>
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