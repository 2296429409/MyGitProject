<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>订单管理页面</span>
    </div>
    <div class="search">
        <form method="get" action="${pageContext.request.contextPath }/usermanage/userList.do" id="usersearch">
            <input type="hidden" id="userid" name="id" value="${userSession.id}">
            <input name="pageIndex" value="1" type="hidden">
            <span>姓名：</span>
            <input list="browsers" name="queryUserName" autocomplete="off" value="${queryUserName}" class="queryUserName">
            <datalist id="browsers"></datalist>
                <span class="clearsearch">清除搜索记录</span>
            <span>岗位：</span>
            <select name="queryUserRole">
                <c:if test="${roleList != null }">
                    <option value="0">--请选择--</option>
                    <c:forEach var="role" items="${roleList}">
                        <option value="${role.id}"
                                <c:if test="${queryUserRole==role.id}"> selected="selected" </c:if>
                            >${role.roleName}
                        </option>
                    </c:forEach>
                </c:if>
            </select>

            <input	value="查 询" type="submit" id="searchbutton">
            <a href="${pageContext.request.contextPath }/jsp/useradd.jsp">添加用户</a>
        </form>
    </div>
    <!--账单表格 样式和供应商公用-->
    <table class="providerTable" cellpadding="0" cellspacing="0">
        <tr class="firstTr">
            <th width="15%">用户名</th>
            <th width="10%">姓名</th>
            <th width="10%">性别</th>
            <th width="10%">年龄</th>
            <th width="15%">电话</th>
            <th width="10%">岗位</th>
            <th width="30%">操作</th>
        </tr>
        <c:forEach var="user" items="${userlist}" varStatus="status">
            <tr>
                <td>
                    <span>${user.userCode}</span>
                </td>
                <td>
                    <span>${user.userName}</span>
                </td>
                <td>
                    <span>
						<c:if test="${user.gender == 1}">男</c:if>
						<c:if test="${user.gender == 2}">女</c:if>
					</span>
                </td>
                <td>
                    <span>${user.age}</span>
                </td>
                <td>
                    <span>${user.phone}</span>
                </td>
                <td>
					<span>${user.userRoleName}</span>
                </td>
                <td>
                    <span><a class="viewUser" href="javascript:;" userid=${user.id } username=${user.userCode }><img src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
                    <span><a class="modifyUser" href="javascript:;" userid=${user.id } username=${user.userCode }><img src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
                    <span><a class="deleteUser" href="javascript:;" userid=${user.id } username=${user.userCode }><img src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div class="page-bar">
        <ul class="page-num-ul clearfix">
            <li>共${ptest.totalCount }条记录&nbsp;&nbsp; ${ptest.currentPageNo }/${ptest.totalPageCount }页</li>
            <c:if test="${ptest.currentPageNo > 1}">
                <a href="javascript:page_nav(document.forms[0],1);">首页</a>
                <a href="javascript:page_nav(document.forms[0],${ptest.currentPageNo}-1)">上一页</a>
            </c:if>
            <c:if test="${ptest.currentPageNo < ptest.totalPageCount }">
                <a href="javascript:page_nav(document.forms[0],${ptest.currentPageNo}+1 )">下一页</a>
                <a href="javascript:page_nav(document.forms[0],${ptest.totalPageCount}+0)">最后一页</a>
            </c:if>
            &nbsp;&nbsp;
        </ul>
        <span class="page-go-form"><label>跳转至</label>
	     <input type="text" name="inputPage" id="totalPageCount" class="page-key" />页
	     <button type="button" class="page-btn" onClick='jump_to(document.forms[0],document.getElementById("totalPageCount").value)'>GO</button>
		</span>
    </div>
</div>
</section>


<style>
    .clearsearch{
        text-decoration:underline;
        font-style:italic;
        color: #9d9d9d;
    }
</style>




<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeUse">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该用户吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/userlist.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/rollpage.js"></script>