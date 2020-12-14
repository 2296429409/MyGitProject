var userObj;

//用户管理页面上点击删除按钮弹出删除框(userlist.jsp)
function deleteUser(obj){
	$.ajax({
		type:"GET",
		url:"/usermanage/deleteuser",
		data:{uid:obj.attr("userid")},
		dataType:"json",
		success:function(data){
			console.log(data)
			if(data == 1){//删除成功：移除删除行
				cancleBtn();
				obj.parents("tr").remove();
			}else if(data == -1){//删除失败
				//alert("对不起，删除用户【"+obj.attr("username")+"】失败");
				changeDLGContent("对不起，删除用户【"+obj.attr("username")+"】失败");
			}else if(data == 0){
				//alert("对不起，用户【"+obj.attr("username")+"】不存在");
				changeDLGContent("对不起，用户【"+obj.attr("username")+"】不存在");
			}
		},
		error:function(data){
			//alert("对不起，删除失败");
			changeDLGContent("对不起，删除失败");
		}
	});
}

function openYesOrNoDLG(){
	$('.zhezhao').css('display', 'block');
	$('#removeUse').fadeIn();
}

function cancleBtn(){
	$('.zhezhao').css('display', 'none');
	$('#removeUse').fadeOut();
}
function changeDLGContent(contentStr){
	var p = $(".removeMain").find("p");
	p.html(contentStr);
}

$(function(){
	//清除记录
	$('.clearsearch').click(function () {
		$("#browsers").empty();
		var id = $("input[name='id']");
		$.ajax({
			type:"GET",
			url:"/search/clearsearch",
			data:{key:"user"+id.val()}
		});
	});

	//获得记录
	$('.queryUserName').mouseenter(function () {
		$("#browsers").empty();
		var id = $("input[name='id']");
		if ($(".queryUserName").val()==""){
			$.ajax({
				type:"GET",
				url:"/search/getsearch",
				data:{key:"user"+id.val()},
				dataType:"json",
				success:function(data){
					for(i in data){
						var option = $("<option>");
						option.val(data[i])
						$("#browsers").append(option);
					}
				}
			});
		}
	});


	$(".queryUserName").bind('input propertychange', function() {
		$("#browsers").empty();
		if ($(".queryUserName").val()!=""){
			$.ajax({
				type:"GET",
				url:"/usermanage/getdata",
				data:{queryUserName:$(".queryUserName").val()},
				dataType:"json",
				success:function(data){
					for(i in data){
						var option = $("<option>");
						option.val(data[i])
						$("#browsers").append(option);
					}
				}
			});
		}
	})

	//自动提交
	$('.queryUserName').change(function() {
		$('#usersearch').submit();
	});




	//通过jquery的class选择器（数组）
	//对每个class为viewUser的元素进行动作绑定（click）
	/**
	 * bind、live、delegate
	 * on
	 */
	$(".viewUser").on("click",function(){
		//将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
		var obj = $(this);
		window.location.href=path+"/usermanage/userview?uid="+ obj.attr("userid");
	});
	
	$(".modifyUser").on("click",function(){
		var obj = $(this);
		window.location.href=path+"/usermanage/usermodify?uid="+ obj.attr("userid");
	});

	$('#no').click(function () {
		cancleBtn();
	});
	
	$('#yes').click(function () {
		deleteUser(userObj);
	});

	$(".deleteUser").on("click",function(){
		userObj = $(this);
		changeDLGContent("你确定要删除用户【"+userObj.attr("username")+"】吗？");
		openYesOrNoDLG();
	});
	
	/*$(".deleteUser").on("click",function(){
		var obj = $(this);
		if(confirm("你确定要删除用户【"+obj.attr("username")+"】吗？")){
			$.ajax({
				type:"GET",
				url:path+"/jsp/user.do",
				data:{method:"deluser",uid:obj.attr("userid")},
				dataType:"json",
				success:function(data){
					if(data.delResult == "true"){//删除成功：移除删除行
						alert("删除成功");
						obj.parents("tr").remove();
					}else if(data.delResult == "false"){//删除失败
						alert("对不起，删除用户【"+obj.attr("username")+"】失败");
					}else if(data.delResult == "notexist"){
						alert("对不起，用户【"+obj.attr("username")+"】不存在");
					}
				},
				error:function(data){
					alert("对不起，删除失败");
				}
			});
		}
	});*/
});