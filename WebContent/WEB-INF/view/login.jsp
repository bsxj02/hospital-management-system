<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>登录</title>
    <link rel="icon" href="<%=path%>/resource/static/favicon.ico">
    <link rel="stylesheet" href="<%=path%>/resource/static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=path%>/resource/static/admin/css/login.css">
    <script src="<%=path%>/resource/static/js/vue.min.js"></script>
    <script src="<%=path%>/resource/static/js/jquery-3.3.1.min.js"></script>
    <script src="<%=path%>/resource/static/bootstrap/js/bootstrap.bundle.js"></script>
    <script src="<%=path%>/layui/layui.js"></script>
</head>
<body background="resource/bg.png" style="background-size: cover;">
<div class="login">
    <form id="saveForm">
        <h2>医院管理系统登录</h2>
        <div class="form-group">
            <label>用户名</label>
            <input type="text" v-model="username" name="username" id="username" class="form-control form-control-lg">
        </div>
        <div class="form-group">
            <label>密码</label>
            <input type="password" v-model="password" name ="password" id="password" class="form-control form-control-lg" id="pwd">
        </div>
        <div class="form-group form-check">
           <input type="radio" class="form-check-input" name="type" value="1" id="exampleCheck2" checked>
            <label class="form-check-label" for="exampleCheck2">管理员</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="radio" class="form-check-input" name="type" value="2" id="exampleCheck1" >
            <label class="form-check-label" for="exampleCheck1">患者</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" class="form-check-input" name="type" value="3" id="exampleCheck1" >
            <label class="form-check-label" for="exampleCheck1">医生</label>
            
        </div> 

        <button type="button" :disabled="loading" @click="login" id="login" class="btn btn-primary btn-lg  btn-block">
            <span v-show="loading" class="spinner-grow spinner-grow-sm" role="status" aria-hidden="true"></span>
            立即登录
        </button>
        <a  data-target="#myModal" data-toggle="modal" >用户注册</a>
    </form>
</div>


	<div class="modal fade" id="myModal" aria-labelledby="myModallabel"
		aria-hidden="true" tabindex="-1" >
		<div class="modal-dialog">
			<div class="modal-content" >
				<div class="modal-header">
					
					<h5 class="modal-title" id="myModallabel">注册患者</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-hidden="true">&times;</button>
				</div>
				<form id="addForm" class="form-horizontal">
					<div class="modal-body">
						<div class="form-group">
							<label class="control-label col-lg-4">用户名：</label>
							<div class="col-lg-9">
								<input type="text" name="username" class="form-control"> 
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-lg-4">姓名：</label>
							<div class="col-lg-9">
								<input type="text" name="realName" id="realName" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-lg-4">密码：</label>
							<div class="col-lg-9">
								<input type="text" name="password" id="password" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-lg-4">身份证：</label>
							<div class="col-lg-9">
								<input type="text" name="idCard" id="idCard" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-lg-4">手机号：</label>
							<div class="col-lg-9">
								<input type="text" name="phone" id="phone" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-lg-4">图片：</label>
							 <input type="hidden" name="img" class="img">
							<div class="col-lg-9">
								<div class="layui-upload">
								          <div class="layui-upload-list">
								                <img class="layui-upload-img" style="width:200px;height:100px;" id="demo1">
								                <button type="button" class="btn btn-primary" id="test1">+</button>
								                <p id="demoText"></p>
								            </div>
								           
								        </div>
							</div>
						</div>
					</div>

					<div class="modal-footer">
						<button class="btn btn-default" type="button" data-dismiss="modal">关闭</button>
						<a class="btn btn-success aaa" type="button" id="save" >提交</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	
<script>
		$("#save").click(function() {
			$.ajax({
				cache : true,
				type : "post",
				url : "addUser",
				data : $("#addForm").serialize(),
				async : false,
				success : function(e) {
					if (e) {
						alert("注册成功！");
						document.location.reload();//当前页面
					} else {
						alert("注册失败！");
					}
				}
			})
		});
	</script>
		
<script>

layui.use([ 'form','jquery','layer','laydate','upload' ], function() {
	var form = layui.form,
	 layer = layui.layer,
	 laydate = layui.laydate,
	 upload = layui.upload,
	 $= layui.jquery;
	 form.render();//这句一定要加，占坑
	
	  /*   laydate.render({
	    elem: '#schoolTime'
	  }); 
	    laydate.render({
	    elem: '#day'
	  });  */
	  
	  
	  var uploadInst = upload.render({
          elem: '#test1'
          ,url: 'upload2'
          ,accept:'images'
          ,size:50000
          ,before: function(obj){

              obj.preview(function(index, file, result){

                  $('#demo1').attr('src', result);
              });
          }
          ,done: function(res){
              //如果上传失败
              if(res.code > 0){
                  return layer.msg('上传失败');
              }
              //上传成功
              var demoText = $('#demoText');
              demoText.html('<span style="color: #4cae4c;">上传成功</span>');

              var fileupload = $(".img");
              fileupload.attr("value",res.data.src);
              console.log(fileupload.attr("value"));
          }
          ,error: function(){
              //演示失败状态，并实现重传
              var demoText = $('#demoText');
              demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
              demoText.find('.demo-reload').on('click', function(){
                  uploadInst.upload();
              });
          }
      });
	 
	    

	  });


</script>

<script>
	    $("#login").click(function(){
	    	var username = $("#username").val();
	    	var password = $("#password").val();
	    	if(username == null || username == ""){
	    		alert("请填写用户名");
	    		return false;
	    	}if(password == null || password == ""){
	    		alert("请填写密码");
	    		return false;
	    	}
			//执行添加的操作ajax
			$.ajax({
				cache:true,
				type:"post",
				url:"login",
				data:$("#saveForm").serialize(),
				async:false,
				success:function(e){
					if(e){
						alert("登录成功");
						 window.parent.location.href="toMain";
					}else{
						alert("登录失败，账号或密码错误");
					}
				}
			})
		});
		   
</script>

</body>
</html>
