<%@page import="${basepackage}.model.*" %>
<#include "/macro.include"/> 
<#include "/custom.include"/> 
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign actionExtension = "shtml"> 
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>

<rapid:override name="head">
	<title><%=${className}.TABLE_ALIAS%>信息</title>
</rapid:override>

<rapid:override name="content">
<!--页面背景头部标题-->
    <div class="ym_bg_head">
        <ul>
            <li class="bg_head_01"></li>
            <li class="bg_head_02"></li>
            <li class="bg_head_05"></li>
        </ul>
    </div>
    <!--页面背景间隔-->
    <div class="ym_bg_jg">
    </div>
    <!--页面背景副标题-->
    <div class="ym_bg_f">
        <div class="bg_f">
            	<%=${className}.TABLE_ALIAS%>
        </div>
    </div>
    
	<s:form action="/action/${className}!list.${actionExtension}" method="get" theme="simple">
		<input class="button" type="button" value="返回列表" onclick="window.location='<@jspEl 'ctx'/>/action/${className}!list.${actionExtension}'"/>
		<input class="button" type="button" value="后退" onclick="history.back();"/>
		
	<#list table.columns as column>
	<#if column.pk>
		<s:hidden name="${column.columnNameLower}" id="${column.columnNameLower}" value="%{model.${column.columnNameLower}}"/>
	</#if>
	</#list>
	<div class="ym_cx_bg">
      <div class="jpxx_bt">
		<table class="formTable"  cellspacing="0" rules="all" border="1" id="GridView1" style="background-color:White;width:100%;border-collapse:collapse;">
		<#list table.columns as column>
		<#if !column.htmlHidden>
			<tr>	
				<td class="tdLabel"><%=${className}.ALIAS_${column.constantName}%></td>	
				<td><#rt>
				<#compress>
				<#if column.isDateTimeColumn>
				<s:property value="%{model.${column.columnNameLower}String}" />
				<#else>
				<s:property value="%{model.${column.columnNameLower}}" />
				</#if>
				</#compress>
				<#lt></td>
			</tr>
		</#if>
		</#list>
		</table>
		</div>
		</div>
	</s:form>
</rapid:override>

<%-- jsp模板继承,具体使用请查看: http://code.google.com/p/rapid-framework/wiki/rapid_jsp_extends --%>
<%@ include file="base.jsp" %>