<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>


<div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>供应商管理页面</span>
        </div>
        <div class="search">
        	<form method="get" action="${pageContext.request.contextPath }/provider/likeQuery.do" id="providersearch">
<%--				<input name="method" value="query" type="hidden">--%>
				<input type="hidden" id="userid" name="id" value="${userSession.id}">
				<span>供应商编码：</span>
				<%--<input name="queryProCode" type="text" value="${queryProCode }">--%>
				<input list="browsers1" name="queryProCode" autocomplete="off" value="${stringList[0]}" class="queryProCode">
				<datalist id="browsers1"></datalist>
				<span class="clearsearch1">清除搜索记录</span>

				<span>供应商名称：</span>
				<%--<input name="queryProName" type="text" value="${queryProName }">--%>
				<input list="browsers2" name="queryProName" autocomplete="off" value="${stringList[1]}" class="queryProName">
				<datalist id="browsers2"></datalist>
				<span class="clearsearch2">清除搜索记录</span>

				<c:if test="${isdelete1==0}">
					<input value="查 询" type="submit" id="searchbutton">
					<input name="isdelete" id="isdelete" type="hidden" value="0">
				</c:if>
				<c:if test="${isdelete1!=0}">
					<input value="查 询" type="submit" id="searchbutton">
					<input name="isdelete" id="isdelete" type="hidden">
				</c:if>
				<input name="pageIndex" id="pageIndex" type="hidden">

				<a href="${pageContext.request.contextPath }/provider/getProvince.do">添加供应商</a>
				<a href="javascript:getHistory(document.forms[0],0,1);">历史记录</a>
				<a href="javascript:getHistory(document.forms[0],1,1);">退出历史记录</a>
				<%--<a href="javascript:page_nav(document.forms[0],1);">退出历史记录</a>--%>
			</form>
        </div>
        <!--供应商操作表格-->
        <table class="providerTable" cellpadding="0" cellspacing="0">
            <tr class="firstTr">
                <th width="10%">供应商编码</th>
                <th width="20%">供应商名称</th>
                <th width="10%">联系人</th>
                <th width="10%">联系电话</th>
                <th width="10%">传真</th>
                <th width="10%">创建时间</th>
                <th width="30%">操作</th>
            </tr>
            <c:forEach var="provider" items="${paramProvider.objects}" varStatus="status">
				<tr>
					<td>
					<span>${provider.proCode }</span>
					</td>
					<td>
					<span>${provider.proName }</span>
					</td>
					<td>
					<span>${provider.proContact}</span>
					</td>
					<td>
					<span>${provider.proPhone}</span>
					</td>
					<td>
					<span>${provider.proFax}</span>
					</td>
					<td>
					<span>
					<fmt:formatDate value="${provider.creationDate}" pattern="yyyy-MM-dd"/>
					</span>
					</td>
					<td>
					<span><a class="viewProvider" href="javascript:;" proid=${provider.id } proname=${provider.proName }><img src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
					<span><a class="modifyProvider" href="javascript:;" proid=${provider.id } proname=${provider.proName }><img src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
					<c:if test="${provider.isdelete != 0}">
						<span><a class="deleteProvider" href="javascript:;" proid=${provider.id } proname=${provider.proName }><img src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
					</c:if>
					<c:if test="${provider.isdelete == 0}">
						<span><a class="recovery" href="javascript:;" proid=${provider.id } proname=${provider.proName }><img src="${pageContext.request.contextPath }/images/recovery.png" alt="恢复" title="恢复"/></a></span>
					</c:if>
					</td>
				</tr>
			</c:forEach>
        </table>
	<div class="page-bar">
		<input type="hidden" name="totalPageCount" id="totalPageCount" value="${paramProvider.totalPageCount}">
		<ul class="page-num-ul clearfix">
			<li>共${paramProvider.totalCount}条记录&nbsp;&nbsp; ${paramProvider.currentPageNo}/${paramProvider.totalPageCount }页</li>
			<c:if test="${paramProvider.currentPageNo > 1}">
				<c:if test="${isdelete1 != 0}">
					<a href="javascript:page_nav(document.forms[0],1);">首页</a>
				</c:if>
				<c:if test="${isdelete1 == 0}">
					<a href="javascript:getHistory(document.forms[0],0,1);">首页</a>
				</c:if>

				<c:if test="${isdelete1 != 0}">
					<a href="javascript:page_nav(document.forms[0],${paramProvider.currentPageNo}-1);">上一页</a>
				</c:if>
				<c:if test="${isdelete1 == 0}">
					<a href="javascript:getHistory(document.forms[0],0,${paramProvider.currentPageNo}-1);">上一页</a>
				</c:if>
			</c:if>


			<c:if test="${paramProvider.currentPageNo < paramProvider.totalPageCount }">

				<c:if test="${isdelete1 != 0}">
					<a href="javascript:page_nav(document.forms[0],${paramProvider.currentPageNo}+1);">下一页</a>
				</c:if>
				<c:if test="${isdelete1 == 0}">
					<a href="javascript:getHistory(document.forms[0],0,${paramProvider.currentPageNo}+1);">下一页</a>
				</c:if>

				<c:if test="${isdelete1 != 0}">
					<a href="javascript:page_nav(document.forms[0],${paramProvider.totalPageCount}+0);">最后一页</a>
				</c:if>
				<c:if test="${isdelete1 == 0}">
					<a href="javascript:getHistory(document.forms[0],0,${paramProvider.totalPageCount}+0);">最后一页</a>
				</c:if>

			</c:if>

			&nbsp;&nbsp;
		</ul>
		<span class="page-go-form"><label>跳转至</label>
	     <input type="text" name="pageIndex" id="inputPage" class="page-key" />页
			<c:if test="${isdelete1 != 0}">
				<button type="button" class="page-btn" onClick='jump_to(document.forms[0],document.getElementById("inputPage").value)'>GO</button>

			</c:if>
			<c:if test="${isdelete1 == 0}">
				<button type="button" class="page-btn" onClick='hist(document.forms[0],0,document.getElementById("inputPage").value)'>GO</button>

			</c:if>
		</span>
	</div>
</div>
</section>

<style>
	.clearsearch1,.clearsearch2{
		text-decoration:underline;
		font-style:italic;
		color: #9d9d9d;
	}
</style>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeProv">
   <div class="removerChid">
       <h2>提示</h2>
       <div class="removeMain" >
           <p>你确定要删除该供应商吗？</p>
           <a href="#" id="yes">确定</a>
           <a href="#" id="no">取消</a>
       </div>
   </div>
</div>

<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/rollpage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/providerlist.js"></script>
