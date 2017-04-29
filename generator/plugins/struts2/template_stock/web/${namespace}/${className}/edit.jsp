<#include "/macro.include"/> 
<#include "/custom.include"/> 
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign actionExtension = "shtml"> 
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>

<rapid:override name="head">
	<title><%=${className}.TABLE_ALIAS%>编辑</title>
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
            	<%=${className}.TABLE_ALIAS%>编辑
        </div>
    </div>
    <div class="ym_cx_bg">
      <div class="jpxx_bt">
	<s:form action="/action/${className}!update.${actionExtension}" method="post">
		<input class="button" id="submitButton" name="submitButton" type="submit" value="提交" />
		<input class="button" type="button" value="返回列表" onclick="window.location='<@jspEl 'ctx'/>/action/${className}!list.${actionExtension}'"/>
		<input class="button" type="button" value="后退" onclick="history.back();"/>
		
		<table class="txt_center_l"  cellspacing="0" rules="all" border="1" id="GridView1" style="background-color:White;width:100%;border-collapse:collapse;">
		<%@ include file="form_include.jsp" %>
		</table>
	</s:form>
	</div>
	</div>
	<script>
		
		new Validation(document.forms[0],{onSubmit:true,onFormValidate : function(result,form) {
			var finalResult = result;
			
			//在这里添加自定义验证
			
			return disableSubmit(finalResult,'submitButton');
		}});
	</script>
</rapid:override>

<%-- jsp模板继承,具体使用请查看: http://code.google.com/p/rapid-framework/wiki/rapid_jsp_extends --%>
<%@ include file="base.jsp" %>