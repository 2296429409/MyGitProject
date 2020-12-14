<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="../css/billlist.css">



<div class="right">
       <div class="location">
           <strong>你现在所在的位置是:</strong>
           <span>订单管理页面</span>
       </div>
       <div class="search">
       <form method="get" action="${pageContext.request.contextPath }/bill/bill.do" id="billsearch">
<%--			<input name="method" value="query" class="input-text" type="hidden">--%>
		   <input type="hidden" name="id" value="${userSession.id}">
			<span>商品名称：</span>
<%--			<input name="queryProductName" type="text" value="${queryProductName}">--%>
		   <input list="browsers" name="queryProductName" autocomplete="off" value="${queryProductName}" class="queryProductName">
		   <datalist id="browsers"></datalist>
			<span class="clearsearch">清除搜索记录</span>



			<span>供应商：</span>
		   <select name="queryProviderId">
			   <c:if test="${providerList != null }">
				   <option value="">--请选择--</option>
				   <c:forEach var="provider" items="${providerList}">
					   <option <c:if test="${provider.id == queryProviderId}">selected="selected"</c:if>
							   value="${provider.id}">${provider.proName}</option>
				   </c:forEach>
			   </c:if>
		   </select>

			<span>是否付款：</span>
			<select name="queryIsPayment">
				<option value="">--请选择--</option>
				<option value="1" ${queryIsPayment == 1 ? "selected=\"selected\"":"" }>未付款</option>
				<option value="2" ${queryIsPayment == 2 ? "selected=\"selected\"":"" }>已付款</option>
       		</select>

			 <input	value="查 询" type="submit" id="searchbutton">
		   <a href="${pageContext.request.contextPath }/jsp/billadd.jsp" class="dingdan">添加订单</a>
		   <%--<a href="${pageContext.request.contextPath }/jsp/billadd.jsp">导入</a>--%>
		   <%--<a href="${pageContext.request.contextPath }/jsp/billadd.jsp">导出</a>--%>
		   <!--弹出框-->
            <a href="#" class="btn btn-danger daochu" role="button" onclick="getExcel()">导出Excel</a>
            <input type="file" name="filename" style="display: none;" id="file">
		   <a href="javaScript:openFile()" class="btn btn-success daoru" role="button" data-toggle="popover"
			  data-trigger="focus" title=""
			  data-content="" id="example">导入Excel</a>
		</form>
       </div>
       <!--账单表格 样式和供应商公用-->
      <table class="providerTable" cellpadding="0" cellspacing="0">
          <tr class="firstTr">
              <th width="10%">订单编码</th>
              <th width="20%">商品名称</th>
              <th width="10%">供应商</th>
              <th width="10%">订单金额</th>
              <th width="10%">是否付款</th>
              <th width="10%">创建时间</th>
              <th width="30%">操作</th>
          </tr>
          <c:forEach var="bill" items="${billList}" varStatus="status">
				<tr>
					<td>
					<span>${bill.billCode }</span>
					</td>
					<td>
					<span>${bill.productName }</span>
					</td>
					<td>
					<span>${bill.providerName}</span>
					</td>
					<td>
					<span>${bill.totalPrice}</span>
					</td>
					<td>
					<span>
						<c:if test="${bill.isPayment == 1}">未付款</c:if>
						<c:if test="${bill.isPayment == 2}">已付款</c:if>
					</span>
					</td>
					<td>
					<span>
					<fmt:formatDate value="${bill.creationDate}" pattern="yyyy-MM-dd"/>
					</span>
					</td>
					<td>
					<span><a class="viewBill" href="javascript:;" billid=${bill.id } billcc=${bill.billCode }><img src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
					<span><a class="modifyBill" href="javascript:;" billid=${bill.id } billcc=${bill.billCode }><img src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
					<span><a class="deleteBill" href="javascript:;" billid=${bill.id } billcc=${bill.billCode }><img src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
					</td>
				</tr>
			</c:forEach>
      </table>
	<div class="page-bar">
		<ul class="page-num-ul clearfix">
			<li>共${pageInfo.total}条记录&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 共${pageInfo.pages}页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				当前第${pageInfo.pageNum}页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
			<%--<c:if test="${pageInfo.currentPageNo > 1}">--%>
				<a href="${pageContext.request.contextPath }/bill/bill.do?page=${pageInfo.firstPage}
				&queryProductName=${queryProductName}&queryProviderId=${queryProviderId}&queryIsPayment=${queryIsPayment}">首页</a>

				<c:if test="${!pageInfo.isFirstPage}">
					<a  href="${pageContext.request.contextPath }/bill/bill.do?page=${pageInfo.prePage}
				&queryProductName=${queryProductName}&queryProviderId=${queryProviderId}&queryIsPayment=${queryIsPayment}">上一页</a>
				</c:if>

				<c:if test="${!pageInfo.isLastPage}">
				<a  href="${pageContext.request.contextPath }/bill/bill.do?page=${pageInfo.nextPage}
				&queryProductName=${queryProductName}&queryProviderId=${queryProviderId}&queryIsPayment=${queryIsPayment}" >下一页</a>
				</c:if>
				<a  href="${pageContext.request.contextPath }/bill/bill.do?page=${pageInfo.lastPage}
				&queryProductName=${queryProductName}&queryProviderId=${queryProviderId}&queryIsPayment=${queryIsPayment}">最后一页</a>
			<%--</c:if>--%>
			&nbsp;&nbsp;
		</ul>
		<span class="page-go-form"><label>跳转至</label>
	     <input type="text" name="inputPage" id="inputPage" class="page-key" />页
	     <button type="button" class="page-btn" onclick=jumps_to(document.getElementById("inputPage").value)>GO</button>
		</span>
	</div>
<div><li class="pagination__item"><a href="#0" class="pagination__number">&nbsp;</a></li></div>
    <!--警告框-->
    <div class="alert alert-success alert-dismissible" role="alert" style="display: none" id="warningBox">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <strong>暂无数据!</strong>
    </div>
</div>
</section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeBi">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该订单吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<style>
	.clearsearch{
		text-decoration:underline;
		font-style:italic;
		color: #9d9d9d;
	}
</style>

<script>
	function jumps_to(num) {
        var regexp=/^[1-9]\d*$/;
        if(!regexp.test(num)){
            alert("请输入大于0的正整数！");
            return false;
        }else if((num-${pageInfo.pages}) > 0){
            alert("请输入小于总页数的页码");
            return false;
        }else{
            location.href=
				"${pageContext.request.contextPath }/bill/bill.do?page="+num+"&queryProductName=${queryProductName}&queryProviderId=${queryProviderId}&queryIsPayment=${queryIsPayment}";
		}
    }
</script>
<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/billlist.js"></script>