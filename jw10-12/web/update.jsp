<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ page language="java" contentType="text/html; charset=UTF-8    pageEncoding="UTF-8"%>--%>
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>

    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>修改用户</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="${pageContext.request.contextPath}/js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</head>
<body>
<div class="container" style="width: 400px;">
    <h3 style="text-align: center;">修改联系人</h3>
    <form action="${pageContext.request.contextPath}/update" method="get">
        <div  style="display: none;">
            <input type="text"  id="id" name="id" value="${pageContext.request.getParameter("id")}"/>
        </div>
        <div class="form-group">
            <label for="name">姓名：</label>
            <input type="text" class="form-control" id="name" name="name"   placeholder="请输入姓名" value="${pageContext.request.getParameter("name")}"/>
        </div>

        <div class="form-group">
            <label>性别：</label>
            <input type="radio" name="sex" value="男"  />男
            <input type="radio" name="sex" value="女"  />女
        </div>

        <div class="form-group">
            <label for="age">年龄：</label>
            <input type="text" class="form-control" id="age"  name="age" placeholder="请输入年龄" value="${pageContext.request.getParameter("age")}"/>
        </div>

        <div class="form-group">
            <label for="address">籍贯：</label>
            <select name="address" class="form-control" id="address">
                <c:forEach items="${addresses}" var="address" varStatus="s">
                    <option value="${address.provinceName}" class="option">${address.provinceName}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="qq">QQ：</label>
            <input id="qq" type="text" class="form-control" name="qq" placeholder="请输入QQ号码" value="${pageContext.request.getParameter("qq")}"/>
        </div>

        <div class="form-group">
            <label for="email">Email：</label>
            <input id="email" type="text" class="form-control" name="email" placeholder="请输入邮箱地址" value="${pageContext.request.getParameter("email")}"/>
        </div>

        <div class="form-group" style="text-align: center">
            <input class="btn btn-primary" type="submit" value="提交" />
            <input class="btn btn-default" type="reset" value="重置" id="load"/>
            <input class="btn btn-default" type="button" value="返回" id="back" onClick="javascript :history.back(-1);"/>
        </div>
    </form>

</div>
<script type="text/javascript">

    $("#load").click(function () {
        window.location.reload()
    })
    $(function () {
        $("input[name='sex']").each(function () {
            if (this.value=="${pageContext.request.getParameter("gender")}") {
                this.checked=true;
                return false
            }
        })
        $("option").each(function () {
            if(this.value=="${pageContext.request.getParameter("address")}"){
                this.selected=true;
                return false
            }
        })
    })



</script>
</body>
</html>
