<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/view/header.jsp"/>
<body>
<div class="row lyear-wrapper">
  <div class="lyear-login">
    <div class="login-center">
      <div class="login-header text-center">
        <a > <h4  style="color: #15c377;">注册</h4></a>
      </div>
      <form id="saveForm"  method="post" >
        <div class="form-group has-feedback feedback-left">
          <input type="text" placeholder="请输入用户名"  class="form-control" name="username" id="username" />
          <span class="mdi mdi-account form-control-feedback" aria-hidden="true"> </span>
         <span class="msg" style="color:red;">	${requestScope.msg}</span>
        </div>
        <div class="form-group has-feedback feedback-left">
          <input type="text" placeholder="请输入真实姓名" class="form-control" id="realName" name="realName" />
          <span class="mdi mdi-lock form-control-feedback" aria-hidden="true"></span>
        </div>
        <div class="form-group has-feedback feedback-left">
          <input type="text" placeholder="请输入身份证" class="form-control" id="idCard" name="idCard" />
          <span class="mdi mdi-lock form-control-feedback" aria-hidden="true"></span>
        </div>
        <div class="form-group has-feedback feedback-left">
          <input type="text" placeholder="请输入手机号" class="form-control" id="mobile" name="mobile" />
          <span class="mdi mdi-lock form-control-feedback" aria-hidden="true"></span>
        </div>
        <div class="form-group has-feedback feedback-left">
          <input type="text" placeholder="请输入密码" class="form-control" id="password" name="password" />
          <span class="mdi mdi-lock form-control-feedback" aria-hidden="true"></span>
        </div>
        <div class="form-group has-feedback feedback-left">
          <input type="text" placeholder="请输入邮箱" class="form-control" id="email" name="email" />
          <span class="mdi mdi-lock form-control-feedback" aria-hidden="true"></span>
        </div>
              <input type="hidden" name="img" class="img">
		   <div class="form-group">
		        <label for="name">头像:</label>
		        <div class="layui-upload">
		          <div class="layui-upload-list">
		                <img class="layui-upload-img" style="width:200px;height:100px;" id="demo1">
		                <button type="button" class="layui-btn layui-btn-sm " id="test1">+</button>
		                <p id="demoText"></p>
		            </div>
		           
		        </div>
		    </div>
                                        
        <div class="form-group">
          <button class="btn btn-block btn-primary" type="button"  id="save"   >立即注册</button>
        </div>
      </form>
       <a class="btn btn-block btn-primary" type="button" href="toLogin">去登录</a>
      <hr>
      <footer class="col-sm-12 text-center">
      </footer>
    </div>
  </div>
</div>

<script>

layui.use([ 'form','jquery','layer','laydate','upload' ], function() {
	var form = layui.form,
	 layer = layui.layer,
	 laydate = layui.laydate,
	 upload = layui.upload,
	 $= layui.jquery;
	 form.render();//这句一定要加，占坑
	
	  
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
	 
	 
	    $("#save").click(function(){
			//执行添加的操作ajax
			$.ajax({
				cache:true,
				type:"post",
				url:"addUser",
				data:$("#saveForm").serialize(),
				async:false,
				success:function(e){
					if(e){
						 layer.msg('新增成功', {
						  icon: 1,
						  time: 2000 //2秒关闭（如果不配置，默认是3秒）
						});  

					}else{
						 layer.msg('新增失败', {
							  icon: 1,
							  time: 2000 //2秒关闭（如果不配置，默认是3秒）
							}); 
					}
				}
			})
		});
		   
	    

	  });
	  
	


</script>
</body>
</html>