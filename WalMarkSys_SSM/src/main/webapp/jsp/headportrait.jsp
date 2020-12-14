<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>
<%--头像--%>
<link rel="stylesheet" href="../css/cropper.min.css">
<%--<link rel="stylesheet" href="../css/bootstrap.min.css">--%>
<link rel="stylesheet" href="../css/myhead.css">

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>头像上传页面</span>
    </div>

    <%--主体--%>
    <div class="wrapper">
        <!--用块元素（容器）包装图像或画布元素-->
        <div class="img-container">
            <img id="image" src="../images/formBg.png" alt="">
        </div>
        <div class="img-preview-container">
            <h1>预览区域</h1>
            <div class="img-preview">
                <img src="" alt="">
            </div>
        </div>
        <div>
            <div class="btn-container" id="move_container">
                <div class="btn" data-movex="0" data-movey="-10">上移</div>
                <div class="btn" data-movex="0" data-movey="10">下移</div>
                <div class="btn" data-movex="-10" data-movey="0">左移</div>
                <div class="btn" data-movex="10" data-movey="0">右移</div>
            </div>
            <div class="btn-container" id="zoom_container">
                <div class="btn" data-zoom="0.1">放大</div>
                <div class="btn" data-zoom="-0.1" data-movey="10">缩小</div>
            </div>
            <div class="btn-container" id="rotate_container">
                <div class="btn" data-rotate="45">顺时针旋转</div>
                <div class="btn" data-rotate="-45" data-movey="10">逆时针旋转</div>
            </div>
            <div class="btn-container" id="scale_container">
                <div class="btn" data-scale="x">左右翻转</div>
                <div class="btn" data-scale="y" data-movey="10">上下翻转</div>
            </div>
            <div class="btn-container">
                <div class="btn" id="enable">可用</div>
                <div class="btn" id="disable">冻结</div>
                <div class="btn" id="reset1">重置</div>
                <%--<div class="btn" id="reset2">重置2</div>--%>
                <div class="btn" id="clear">清空</div>
                <div class="btn" id="destroy">销毁</div>
                <div class="btn">
                    <input class="input-upload" id="upload" type="file">
                    上传图片
                </div>
            </div>
            <%--<div class="btn-container">--%>
            <%--<div class="btn" id="replace">修改图片地址</div>--%>
            <%--</div>--%>
            <div class="btn-container">
                <div class="btn" id="getCroppedCanvas">裁剪</div>
            </div>
            <div class="btn-container">
                <div class="btn" id="submit">提交</div>
            </div>
            <div class="btn-container" id="ratio_container">
                <div class="btn">裁剪比列：</div>
                <div class="btn" data-ratio="16/9">16/9</div>
                <div class="btn" data-ratio="4/3">4/3</div>
                <div class="btn" data-ratio="1">1/1</div>
                <div class="btn" data-ratio="2/3">2/3</div>
                <div class="btn" data-ratio="">Free</div>
            </div>
            <!--<img src="" id="test" width="500px" height="500px" alt="">-->
            <div class="fixed-canvas hiddle">
                <div class="fixed-bg"></div>
                <div class="canvas-container">
                    <h1>裁剪区域</h1>
                    <div class="canvas" id="canvas">
                    </div>
                    <div class="btn-container" id="modal_canvas_btn">
                        <a class="btn" href="javascript:;" >取消</a>
                        <a class="btn" id="download" href="javascript:;" download="images/picture.jpg">下载</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</section>


<%@include file="/jsp/common/foot.jsp" %>
