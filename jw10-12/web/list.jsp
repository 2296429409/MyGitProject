<%--<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <h3 style="text-align: center">用户信息列表</h3>

    <div style="float: left;">

        <form class="form-inline" action="userall" method="get">
            <div class="form-group">
                <label for="exampleInputName2">姓名</label>
                <input type="text" class="form-control" id="exampleInputName2" name="name" value="${pageContext.request.getParameter('name')}">
            </div>

            <div class="form-group">
                <label for="exampleInputName3">籍贯</label>
                <select name="address" id="exampleInputName3" class="form-control">
                    <option value="">请选择</option>

                <c:forEach items="${addresses}" var="address" varStatus="s">
                    <option value="${address.provinceName}" class="option">${address.provinceName}</option>
                </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="exampleInputEmail2">邮箱</label>
                <input type="email" class="form-control" id="exampleInputEmail2" name="email" value="${pageContext.request.getParameter('email')}">
            </div>
            <button type="submit" class="btn btn-default">查询</button>
        </form>

    </div>

    <div style="float: right;margin: 5px;">

        <a class="btn btn-primary" href="add.jsp">添加联系人</a>
        <a class="btn btn-primary" id="deletes" href="" onclick="a()">删除选中</a>
        <a class="btn btn-primary" id="showdeletes" href="${pageContext.request.contextPath}/setdelete">恢复数据</a>

    </div>

    <table border="1" class="table table-bordered table-hover" id="table1">
        <tr class="success">
            <th><input type="checkbox" id="cbox"></th>
            <th>编号</th>
            <th>姓名</th>
            <th>性别</th>
            <th>年龄</th>
            <th>籍贯</th>
            <th>QQ</th>
            <th>邮箱</th>
            <th>操作</th>
        </tr>

        <c:forEach items="${users}" var="user" varStatus="s">
            <tr>
                <td><input type="checkbox" class="checkbox" value="${user.id}"></td>
                <td>${s.count}</td>
                <td>${user.name}</td>
                <td>${user.gender}</td>
                <td>${user.age}</td>
                <td>${user.address}</td>
                <td>${user.qq}</td>
                <td>${user.email}</td>
                <td><a class="btn btn-default btn-sm"
                       href="update.jsp?id=${user.id}&name=${user.name}&gender=${user.gender}&age=${user.age}&address=${user.address}&qq=${user.qq}&email=${user.email}"
                >修改</a>&nbsp;<a class="btn btn-default btn-sm delete" href="#" onclick="b(${user.id})">删除</a></td>
            </tr>
        </c:forEach>


    </table>
    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <li>
                    <a href="#" aria-label="Previous" id="sp">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <c:forEach begin="1" end="${pageContext.request.getParameter('i')/4+1}" step="1" var="x" varStatus="s">
                    <c:if test="${pageContext.request.getParameter('page')==s.count}">
                        <li class="active"><a href="#" class="ali">${s.count}</a></li>
                    </c:if>
                    <c:if test="${pageContext.request.getParameter('page')!=s.count}">
                        <li><a href="${pageContext.request.contextPath}/userall?page=${s.count}&name=${pageContext.request.getParameter("name")}&address=${pageContext.request.getParameter("address")}&email=${pageContext.request.getParameter("email")}" class="ali">${s.count}</a></li>
                    </c:if>
                </c:forEach>
                <li>
                    <a href="#" aria-label="Next" id="xp">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                <span style="font-size: 25px;margin-left: 5px;">
                    共${pageContext.request.getParameter("i")}条记录，共
                    <c:if test="${pageContext.request.getParameter('i')%4==0}">
                        ${pageContext.request.getParameter('i')/4}
                    </c:if>
                    <c:if test="${pageContext.request.getParameter('i')%4!=0}">
                        ${(pageContext.request.getParameter('i')-pageContext.request.getParameter('i')%4)/4+1}
                    </c:if>
                    页
                </span>
            </ul>
        </nav>


    </div>
</div>

<script language="javascript">
    $(function () {
        $("#sp").click(function () {
            <c:if test="${pageContext.request.getParameter('page')!=1}">
            $("#sp").attr("href","${pageContext.request.contextPath}/userall?page=${pageContext.request.getParameter('page')-1}&name=${pageContext.request.getParameter('name')}&address=${pageContext.request.getParameter('address')}&email=${pageContext.request.getParameter('email')}")
            </c:if>
        })
        $("#xp").click(function () {
            <c:if test="${pageContext.request.getParameter('page')<pageContext.request.getParameter('i')/4}">
            $("#xp").attr("href","${pageContext.request.contextPath}/userall?page=${pageContext.request.getParameter('page')+1}&name=${pageContext.request.getParameter('name')}&address=${pageContext.request.getParameter('address')}&email=${pageContext.request.getParameter('email')}")
            </c:if>
        })
        <c:if test="${pageContext.request.getParameter('i')%4==0}">
        $(".ali:last").remove();
        </c:if>
        $(".option").each(function () {
            if(this.value=="${pageContext.request.getParameter("address")}"){
                this.selected=true
            }
        })
    })

    $("#cbox").click(function () {
        $(".checkbox").prop("checked",$("#cbox").prop("checked"))
    })
    function a(){
        if (confirm("是否确认删除")){
            ds=[];
            $(".checkbox").each(function (i,e) {
                if (e.checked==true) {
                    ds.push(e.value)
                }
            })
            $("#deletes").attr("href","${pageContext.request.contextPath}/delete?id="+ds)
        }
    }
    function b(id) {
        if (confirm("是否确认删除")){
            $(".delete").attr("href","${pageContext.request.contextPath}/delete?id="+id)
        }
    }
</script>

</body>
</html>
