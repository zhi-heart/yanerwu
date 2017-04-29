<%@page import="${basepackage}.model.*" %>
<#include "/macro.include"/> 
<#include "/custom.include"/> 
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<%@ include file="/commons/taglibs.jsp" %>

<rapid:override name="head">
	<title><%=${className}.TABLE_ALIAS%> 维护</title>
	
	<link href="<c:url value="/widgets/simpletable/simpletable.css"/>" type="text/css" rel="stylesheet">
	<script type="text/javascript" src="<c:url value="/widgets/simpletable/simpletable.js"/>"></script>
	
	<script type="text/javascript" >
		$(document).ready(function() {
			// 分页需要依赖的初始化动作
			window.simpleTable = new SimpleTable('queryForm',<@jspEl 'page.thisPageNumber'/>,<@jspEl 'page.pageSize'/>,'<@jspEl 'pageRequest.sortColumns'/>');
		});
	</script>
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
            	<%=${className}.TABLE_ALIAS%> 维护
        </div>
    </div>
	<form id="queryForm" name="queryForm" action="<c:url value="/action/${className}!list.shtml"/>" method="get" style="display: inline;">
	<div class="queryPanel">
		<fieldset>
			<legend>搜索</legend>
			<table>
				<#list table.notPkColumns?chunk(4) as row>
				<tr>	
					<#list row as column>
					<#if !column.htmlHidden>	
					<td class="tdLabel"><%=${className}.ALIAS_${column.constantName}%></td>		
					<td>
						<#if column.isDateTimeColumn>
						<input value="<fmt:formatDate value='<@jspEl "query."+column.columnNameLower+'Begin'/>' pattern='<%=${className}.FORMAT_${column.constantName}%>'/>" onclick="WdatePicker({dateFmt:'<%=${className}.FORMAT_${column.constantName}%>'})" id="${column.columnNameLower}Begin" name="${column.columnNameLower}Begin"   />
						<input value="<fmt:formatDate value='<@jspEl "query."+column.columnNameLower+'End'/>' pattern='<%=${className}.FORMAT_${column.constantName}%>'/>" onclick="WdatePicker({dateFmt:'<%=${className}.FORMAT_${column.constantName}%>'})" id="${column.columnNameLower}End" name="${column.columnNameLower}End"   />
						<#else>
						<input value="<@jspEl "query."+column.columnNameLower/>" id="${column.columnNameLower}" name="${column.columnNameLower}" maxlength="${column.size}"  class="${column.noRequiredValidateString}"/>
						</#if>
					</td>
					</#if>
					</#list>
				</tr>	
				</#list>			
			</table>
		</fieldset>
		<div class="handleControl">
			<input type="submit" class="button" style="width:80px" value="查询" onclick="getReferenceForm(this).action='<@jspEl 'ctx'/>/action/${className}!list.shtml'"/>
			<input type="submit" class="button" style="width:80px" value="新增" onclick="getReferenceForm(this).action='<@jspEl 'ctx'/>/action/${className}!create.shtml'"/>
			<input type="button" class="button" style="width:80px" value="删除" onclick="batchDelete('<@jspEl 'ctx'/>/action/${className}!delete.shtml','items',document.forms.queryForm)"/>
		<div>
	</div>
	
	<div class="gridTable">
	
		<simpletable:pageToolbar page="<@jspEl 'page'/>">
		</simpletable:pageToolbar>
	
		<table width="100%"  border="0" cellspacing="0" class="gridBody">
		  <thead>
			  
			  <tr>
				<th style="width:1px;"> </th>
				<th style="width:1px;"><input type="checkbox" onclick="setAllCheckboxState('items',this.checked)"></th>
				
				<!-- 排序时为th增加sortColumn即可,new SimpleTable('sortColumns')会为tableHeader自动增加排序功能; -->
				<#list table.columns as column>
				<#if !column.htmlHidden>
				<th sortColumn="${column.sqlName}" ><%=${className}.ALIAS_${column.constantName}%></th>
				</#if>
				</#list>
	
				<th>操作</th>
			  </tr>
			  
		  </thead>
		  <tbody>
		  	  <c:forEach items="<@jspEl 'page.result'/>" var="item" varStatus="status">
		  	  
			  <tr class="<@jspEl "status.count % 2 == 0 ? 'odd' : 'even'"/>">
				<td><@jspEl 'page.thisPageFirstElementNumber + status.index'/></td>
				<td><input type="checkbox" name="items" value="<@generateIdQueryString/>"></td>
				
				<#list table.columns as column>
				<#if !column.htmlHidden>
				<td><#rt>
					<#compress>
					<#if column.isDateTimeColumn>
					<c:out value='<@jspEl "item."+column.columnNameLower+"String"/>'/>&nbsp;
					<#else>
					<c:out value='<@jspEl "item."+column.columnNameLower/>'/>&nbsp;
					</#if>
					</#compress>
				<#lt></td>
				</#if>
				</#list>
				<td>
					<a href="<@jspEl 'ctx'/>/action/${className}!show.shtml?<@generateIdQueryString/>">查看</a>&nbsp;&nbsp;&nbsp;
					<a href="<@jspEl 'ctx'/>/action/${className}!edit.shtml?<@generateIdQueryString/>">修改</a>
				</td>
			  </tr>
			  
		  	  </c:forEach>
		  </tbody>
		</table>
	
		<simpletable:pageToolbar page="<@jspEl 'page'/>">
		</simpletable:pageToolbar>
	</div>
	</form>
</rapid:override>

<%-- jsp模板继承,具体使用请查看: http://code.google.com/p/rapid-framework/wiki/rapid_jsp_extends --%>
<%@ include file="base.jsp" %>

<#macro generateIdQueryString>
	<#if table.compositeId>
		<#assign itemPrefix = 'item.id.'>
	<#else>
		<#assign itemPrefix = 'item.'>
	</#if>
<#compress>
		<#list table.compositeIdColumns as column>
			<#t>${column.columnNameLower}=<@jspEl itemPrefix + column.columnNameLower/>&
		</#list>				
</#compress>
</#macro>