var billObj;

//订单管理页面上点击删除按钮弹出删除框(billlist.jsp)
function deleteBill(obj){
	$.ajax({
		type:"GET",
		url:path+"/bill/delBill.do",
		data:{billid:obj.attr("billid")},
		dataType:"json",
		success:function(data){
			if(data.delResult == "true"){//删除成功：移除删除行
				cancleBtn();
				obj.parents("tr").remove();
			}else if(data.delResult == "false"){//删除失败
				//alert("对不起，删除订单【"+obj.attr("billcc")+"】失败");
				changeDLGContent("对不起，删除订单【"+obj.attr("billcc")+"】失败");
			}else if(data.delResult == "notexist"){
				//alert("对不起，订单【"+obj.attr("billcc")+"】不存在");
				changeDLGContent("对不起，订单【"+obj.attr("billcc")+"】不存在");
			}
		},
		error:function(data){
			alert("对不起，删除失败");
		}
	});
}

function openYesOrNoDLG(){
	$('.zhezhao').css('display', 'block');
	$('#removeBi').fadeIn();
}

function cancleBtn(){
	$('.zhezhao').css('display', 'none');
	$('#removeBi').fadeOut();
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
            data:{key:"bill"+id.val()}
        });
    });
    //获得记录
    $('.queryProductName').mouseenter(function () {
        var id = $("input[name='id']");
        if ($(".queryProductName").val()==""){
            $.ajax({
                type:"GET",
                url:"/search/getsearch",
                data:{key:"bill"+id.val()},
                dataType:"json",
                success:function(data){
                    $("#browsers").empty();
                    for(i in data){
                        var option = $("<option>");
                        option.val(data[i])
                        $("#browsers").append(option);
                    }
                }
            });
        }
    });

    $(".queryProductName").bind('input propertychange', function() {
        $("#browsers").empty();
        $.ajax({
            type:"GET",
            url:"/bill/getdata",
            data:{queryProductName:$(".queryProductName").val()},
            dataType:"json",
            success:function(data){
                for(i in data){
                    var option = $("<option>");
                    option.val(data[i])
                    $("#browsers").append(option);
                }
            }
        });
    })
    //自动提交
    $('.queryProductName').change(function() {
        $('#billsearch').submit();
    });



	$(".viewBill").on("click",function(){
		//将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
		var obj = $(this);
		window.location.href=path+"/bill/viewBill.do?billid="+ obj.attr("billid");
	});
	
	$(".modifyBill").on("click",function(){
		var obj = $(this);
		window.location.href=path+"/bill/getBillById.do?billid="+ obj.attr("billid");
	});
	$('#no').click(function () {
		cancleBtn();
	});
	
	$('#yes').click(function () {
		deleteBill(billObj);
	});

	$(".deleteBill").on("click",function(){
		billObj = $(this);
		changeDLGContent("你确定要删除订单【"+billObj.attr("billcc")+"】吗？");
		openYesOrNoDLG();
	});
	
	/*$(".deleteBill").on("click",function(){
		var obj = $(this);
		if(confirm("你确定要删除订单【"+obj.attr("billcc")+"】吗？")){
			$.ajax({
				type:"GET",
				url:path+"/bill.do",
				data:{method:"delbill",billid:obj.attr("billid")},
				dataType:"json",
				success:function(data){
					if(data.delResult == "true"){//删除成功：移除删除行
						alert("删除成功");
						obj.parents("tr").remove();
					}else if(data.delResult == "false"){//删除失败
						alert("对不起，删除订单【"+obj.attr("billcc")+"】失败");
					}else if(data.delResult == "notexist"){
						alert("对不起，订单【"+obj.attr("billcc")+"】不存在");
					}
				},
				error:function(data){
					alert("对不起，删除失败");
				}
			});
		}
	});*/

//	导入导出
    $("#file").change(function () {
        var formData = new FormData();
        var file = $(this)[0].files[0];//获取文件对象
        var fileName = file.name
        if (fileName!=undefined && fileName!="") {
            formData.append("file",file)
            var fileNames = fileName.split(".");
            var fileNameSuffix = fileNames[fileNames.length-1]
            // alert(fileNameSuffix)
            if (fileNameSuffix=="xlsx" || fileNameSuffix=="xls") {
                $.ajax({
                    url:  path+'/bill/fileUpload',
                    type: 'post',
                    async: false,
                    data: formData,
                    processData: false,// 告诉jQuery不要去处理发送的数据
                    contentType: false,// 告诉jQuery不要去设置Content-Type请求头
                    success:function (data) {
                        $("#warningBox").css("display","block")
                        if (data.result=="成功") {
                            $("#warningBox strong").text(fileName+"导入成功!")
                        }else {
                            $("#warningBox strong").text(data.result)
                        }

                    }
                })
            } else {
                $("#example").popover({
                    content:"只支持.xlsx格式和xls格式"
                })
                $("#example").popover("toggle")
            }
        }

    });
});

function openFile() {
    $("#file").trigger("click")
}

function getExcel() {
	var queryProductName = $("[name='queryProductName']").val()
    var queryProviderId = $("[name='queryProviderId']").val()
    var queryIsPayment = $("[name='queryIsPayment']").val()
    var page = location.search
    var a = 1
    if (page!=""&& page!=undefined) {
        a = page.split("&")[0].split("=")[1]
    }
	location.href =  path+"/bill/getExcel?queryProductName="+queryProductName+"&queryProviderId="+queryProviderId+"&queryIsPayment="+queryIsPayment+"&page="+a
}