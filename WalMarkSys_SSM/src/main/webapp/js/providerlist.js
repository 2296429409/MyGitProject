var providerObj;

//供应商管理页面上点击删除按钮弹出删除框(providerlist.jsp)
function deleteProvider(obj){
	$.ajax({
		type:"GET",
        // contentType:'application/json;charset=UTF-8',//必须有
		url:path+"/provider/delProviderById.do",
		data:{proid:obj.attr("proid")},
		dataType:"json",
		success:function(data){
			if(data.delResult == "true"){//删除成功：移除删除行
				cancleBtn();
				obj.parents("tr").remove();
			}else if(data.delResult == "false"){//删除失败
				//alert("对不起，删除供应商【"+obj.attr("proname")+"】失败");
				changeDLGContent("对不起，删除供应商【"+obj.attr("proname")+"】失败");
			}else if(data.delResult == "notexist"){
				//alert("对不起，供应商【"+obj.attr("proname")+"】不存在");
				changeDLGContent("对不起，供应商【"+obj.attr("proname")+"】不存在");
			}else{
				//alert("对不起，该供应商【"+obj.attr("proname")+"】下有【"+data.delResult+"】条订单，不能删除");
				changeDLGContent("对不起，该供应商【"+obj.attr("proname")+"】下有【"+data.delResult+"】条订单，不能删除");
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
	$('#removeProv').fadeIn();
}

function cancleBtn(){
	$('.zhezhao').css('display', 'none');
	$('#removeProv').fadeOut();
}
function changeDLGContent(contentStr){
	var p = $(".removeMain").find("p");
	p.html(contentStr);
}

$(function(){
	//清除记录1
	$('.clearsearch1').click(function () {
		$("#browsers1").empty();
		var id = $("input[name='id']");
		$.ajax({
			type:"GET",
			url:"/search/clearsearch",
			data:{key:"providercode"+id.val()}
		});
	});
	//获得记录1
	$('.queryProCode').mouseenter(function () {
		var id = $("input[name='id']");
		if ($(".queryProCode").val()==""){
			$.ajax({
				type:"GET",
				url:"/search/getsearch",
				data:{key:"providercode"+id.val()},
				dataType:"json",
				success:function(data){
					$("#browsers1").empty();
					for(i in data){
						var option = $("<option>");
						option.val(data[i])
						$("#browsers1").append(option);
					}
				}
			});
		}
	});
	$(".queryProCode").bind('input propertychange', function() {
		$("#browsers1").empty();
		$.ajax({
			type:"GET",
			url:"/provider/getdata1",
			data:{queryProCode:$(".queryProCode").val()},
			dataType:"json",
			success:function(data){
				for(i in data){
					var option = $("<option>");
					option.val(data[i])
					$("#browsers1").append(option);
				}
			}
		});
	})




	//清除记录2
	$('.clearsearch2').click(function () {
		$("#browsers2").empty();
		var id = $("input[name='id']");
		$.ajax({
			type:"GET",
			url:"/search/clearsearch",
			data:{key:"providername"+id.val()}
		});
	});
	//获得记录2
	$('.queryProName').mouseenter(function () {
		var id = $("input[name='id']");
		if ($(".queryProName").val()==""){
			$.ajax({
				type:"GET",
				url:"/search/getsearch",
				data:{key:"providername"+id.val()},
				dataType:"json",
				success:function(data){
					$("#browsers2").empty();
					for(i in data){
						var option = $("<option>");
						option.val(data[i])
						$("#browsers2").append(option);
					}
				}
			});
		}
	});
	$(".queryProName").bind('input propertychange', function() {
		$("#browsers2").empty();
		$.ajax({
			type:"GET",
			url:"/provider/getdata2",
			data:{queryProductName:$(".queryProName").val()},
			dataType:"json",
			success:function(data){
				for(i in data){
					var option = $("<option>");
					option.val(data[i])
					$("#browsers2").append(option);
				}
			}
		});
	})

	//自动提交
	$('.queryProCode').change(function() {
		$('#providersearch').submit();
	});

	$('.queryProName').change(function() {
		$('#providersearch').submit();
	});





	$(".viewProvider").on("click",function(){
		//将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
		var obj = $(this);
		window.location.href=path+"/provider/getProviderById.do?method=view&proid="+ obj.attr("proid");
	});
	
	$(".modifyProvider").on("click",function(){
		var obj = $(this);
		window.location.href=path+"/provider/modifyBefore.do?method=modify&proid="+ obj.attr("proid");
	});

	$('#no').click(function () {
		cancleBtn();
	});
	
	$('#yes').click(function () {
		deleteProvider(providerObj);
	});

	$(".deleteProvider").on("click",function(){
		providerObj = $(this);
		changeDLGContent("你确定要删除供应商【"+providerObj.attr("proname")+"】吗？");
		openYesOrNoDLG();
	});
	$(".recovery").on("click",function () {
		var obj=$(this)
			var r=confirm("你确定要恢复供应商【"+obj.attr("proname")+"】吗？");
			if (r==true){
                recovery(obj);
			}
    })

/*	$(".deleteProvider").on("click",function(){
		var obj = $(this);
		if(confirm("你确定要删除供应商【"+obj.attr("proname")+"】吗？")){
			$.ajax({
				type:"GET",
				url:path+"/jsp/provider.do",
				data:{method:"delprovider",proid:obj.attr("proid")},
				dataType:"json",
				success:function(data){
					if(data.delResult == "true"){//删除成功：移除删除行
						alert("删除成功");
						obj.parents("tr").remove();
					}else if(data.delResult == "false"){//删除失败
						alert("对不起，删除供应商【"+obj.attr("proname")+"】失败");
					}else if(data.delResult == "notexist"){
						alert("对不起，供应商【"+obj.attr("proname")+"】不存在");
					}else{
						alert("对不起，该供应商【"+obj.attr("proname")+"】下有【"+data.delResult+"】条订单，不能删除");
					}
				},
				error:function(data){
					alert("对不起，删除失败");
				}
			});
		}
	});*/
})
function recovery(obj) {
    window.location.href=path+"/provider/recoveryProviderById.do?proid="+ obj.attr("proid");
}
function getHistory(from,number,num) {
    from.isdelete.value=number;
    from.pageIndex.value = num
    from.submit()
}
function isdelete(from,sign) {
	from.isdelete.value=sign;
	from.submit()
}
function hist(frm,number,num){
    //alert(num);
    //验证用户的输入
    var regexp=/^[1-9]\d*$/;
    var totalPageCount = document.getElementById("totalPageCount").value;
    //alert(totalPageCount);
    if(!regexp.test(num)){
        alert("请输入大于0的正整数！");
        return false;
    }else if((num-totalPageCount) > 0){
        alert("请输入小于总页数的页码");
        return false;
    }else{
        getHistory(frm,number,num);
    }
}