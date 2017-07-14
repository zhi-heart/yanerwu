<#include "/macro.include"/> <#include "/custom.include"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign actionExtension = "shtml">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"         uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>${table.tableAlias} - 详细信息</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="${className}/info.html" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
				<div class="pageFormContent" layoutH="56">
					<#list table.columns as column>
					<#if column.pk>
			        <input type="hidden" name="${column.columnNameLower}" value="${r"$"}{${classNameLower}.${column.columnNameLower}}" />
			        </#if>
					<#if !column.pk>
					<div class="unit">
						<label><#if column.remarks != ''>${column.remarks}<#else>${column.columnNameLower}</#if>：</label>
                        <#if column.isDateTimeColumn>
                            <input type="text" name="${column.columnNameLower}" class="date <#if column.nullable><#else>required</#if>" readonly="readonly" value="${r"$"}{entity.${column.columnNameLower}}" />
                        <#elseif column.isNumberColumn>
                            <input type="text" name="${column.columnNameLower}" class="textInput number <#if column.nullable><#else>required</#if>" value="${r"$"}{entity.${column.columnNameLower}}" />
                        <#else>
                            <input type="text" name="${column.columnNameLower}" class="textInput <#if column.nullable><#else>required</#if>" value="${r"$"}{entity.${column.columnNameLower}}" />
                        </#if>
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