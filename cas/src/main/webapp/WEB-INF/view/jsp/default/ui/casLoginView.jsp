<!DOCTYPE html>
<html lang="en">
<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.include file="includes/top.jsp"/>
<section class="row">
    <label for="请输入你的登录信息">
        <font size="4" color="#478fca">输入您的登录信息</font>
    </label>
    <hr color="#478fca">
</section>
<c:if test="${not pageContext.request.secure}">
    <%--    <div id="msg" class="errors">
        <h2><spring:message code="screen.nonsecure.title" /></h2>
        <p><spring:message code="screen.nonsecure.message" /></p>
    </div>  --%>
</c:if>
<div id="cookiesDisabled" class="errors" style="display: none;">
    <h2>
        <spring:message code="screen.cookies.disabled.title"/>
    </h2>

    <p>
        <spring:message code="screen.cookies.disabled.message"/>
    </p>
</div>
<div id="login">
    <form:form method="post" id="fm1" commandName="${commandName}" htmlEscape="true">
        <form:errors path="*" id="msg" cssClass="errors" element="div" htmlEscape="false"/>
        <section class="row">
                <%--  <label for="username"><spring:message code="screen.welcome.label.netid" /></label> --%>
            <c:choose>
                <c:when test="${not empty sessionScope.openIdLocalId}">
                    <strong><c:out value="${sessionScope.openIdLocalId}"/></strong>
                    <input type="hidden" id="username" name="username"
                           value="<c:out value="${sessionScope.openIdLocalId}" />"/>
                </c:when>
                <c:otherwise>
                    <spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey"/>
                    <form:input cssClass="required" cssErrorClass="error" id="username" size="25" tabindex="1"
                                placeholder="用户名" accesskey="${userNameAccessKey}" path="username" autocomplete="off"
                                htmlEscape="true"/>
                </c:otherwise>
            </c:choose>
        </section>
        <section class="row">
                <%--  <label for="password"><spring:message code="screen.welcome.label.password" /></label> --%>
            <spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey"/>
            <form:password cssClass="required" cssErrorClass="error" id="password" size="25" tabindex="2"
                           path="password" placeholder="密码" accesskey="${passwordAccessKey}" htmlEscape="true"
                           autocomplete="off"/>
			<span id="capslock-on" style="display: none;">
				<p>
                    <img src="images/warning.png" valign="top">
                    <spring:message code="screen.capslock.on"/>
                </p>
			</span>
        </section>
        <section class="row">
            <spring:message code="screen.welcome.label.captcha.accesskey" var="captchaAccessKey"/>
            <spring:message code="screen.welcome.label.captcha" var="captchaHolder"/>
            <form:input cssClass="required" cssErrorClass="error" id="captcha" size="10" tabindex="3" path="captcha"
                        placeholder="验证码" accesskey="${captchaAccessKey}" autocomplete="off" htmlEscape="true"/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <img alt="${captchaHolder }" src="captcha.jpg" onclick="this.src='captcha.jpg?'+Math.random();"
                 style="width: 65px; height: 30px; vertical-align: middle;">
        </section>
        <section class="row btn-row">
            <input type="hidden" name="lt" value="${loginTicket}"/>
            <input type="hidden" name="execution" value="${flowExecutionKey}"/>
            <input type="hidden" name="_eventId" value="submit"/>
            <input class="btn-submit" name="submit" accesskey="l"
                   value="<spring:message code="screen.welcome.button.login" /> " tabindex="6" type="submit"/>
            <input class="btn-reset" name="reset" accesskey="c"
                   value="<spring:message code="screen.welcome.button.clear" />" tabindex="7" type="reset"/>
        </section>
    </form:form>
</div>
<%-- <jsp:directive.include file="includes/bottom.jsp" />  --%>