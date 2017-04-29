<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户列表</title>
</head>
<body>
<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="user/list.html" method="post" id="searchForm" rel="pagerForm">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td style="padding: 0 0 0 10px;">
                        手机号码: <input type="text" name="mobile" id="mobile" class="textInput" value="${mobile}"/>
                    </td>
                </tr>
            </table>
            <div class="subBar">
                <ul>
                    <li>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="submit">检索</button>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </form>
</div>
<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <!-- height 26px  -->
            <li><a class="add" href="web/user-save.jsp" target="dialog"><span>添加用户</span></a></li>
            <li><a class="edit" href="user-power.html?id={id}" target="dialog" width="800" height="500" max="false"
                   mixable="true"><span>权限配置</span></a></li>
            <li><a class="delete" href="user-delete.html?id={id}" target="ajaxTodo" width="520"
                   height="252"><span>置为无效</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="90">
        <thead>
        <tr>
            <th width="10%" align="center">id</th>
            <th width="10%" align="center">登录名</th>
            <th width="10%" align="center">登录密码</th>
            <th width="10%" align="center">姓名</th>
            <th width="10%" align="center">手机</th>
            <th width="10%" align="center">创建时间</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${result}">
            <tr target="id" rel="${item.id}">
                <td>${item.id}</td>
                <td>${item.loginName}</td>
                <td>${item.loginPwd}</td>
                <td>${item.name}</td>
                <td>${item.phone}</td>
                <td>${item.createDate}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <!-- 分页 -->
    <form id="pagerForm" method="post" action="user/list.html" rel="searchForm">
        <input type="hidden" name="pageNum" value="1"/> <!--【必须】value=1可以写死-->
        <input type="hidden" name="numPerPage" value="${page.numPerPage}"/> <!--【可选】每页显示多少条-->
    </form>
    <div class="panelBar">
        <div class="pages">
            <span>显示</span>
            <select class="combox" name="page.numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
                <option value="500" ${'500' == page.numPerPage ? 'selected="selected"' : ''}>500</option>
                <option value="1000" ${'1000' == page.numPerPage ? 'selected="selected"' : ''}>1000</option>
                <option value="3000" ${'3000' == page.numPerPage ? 'selected="selected"' : ''}>3000</option>
                <option value="9999" ${'9999' == page.numPerPage ? 'selected="selected"' : ''}>9999</option>
            </select>
            <span>条，共${page.totalCount}条</span>
        </div>
        <div class="pagination" targetType="navTab" totalCount="${page.totalCount }" numPerPage="${page.numPerPage }"
             pageNumShown="10" currentPage="${page.currentPage }"></div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    function checkCrd() {
        if (typeof($("#mobile").val()) == "undefined" || $("#mobile").val() == "") {
            alertMsg.error("手机号不能为空!");
            return false;
        }
    }
</script>
