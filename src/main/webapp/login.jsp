<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	System.out.println("欢迎来到未来工程师世界~");
%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>登录</title>
		<meta name="viewport" content="width=device-width,initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			//登录
			function login() {
				var username = $("#username").val();
				if(null == username || "" == username) {
					alert("请输入用户名");
					return;
				}
				var pwd = $("#pwd").val();
				if(null == pwd || "" == pwd) {
					alert("请输入密码");
					return;
				}
				var data = {
					"account": username,
					"pwd": pwd
				};
				
				//关键的一步来了:将前端输入的数据发送给后台控制器，怎么发送？？？
				$.ajax({
					url : "http://192.168.215.131:8080/university/login",
					type : "POST",
					data : data,
					dataType : "json",
					success : function(data) {
						if (data.status == "1") {
							window.location.href = "home.jsp";
						}
					}
				});
				
			}
			//显示注册窗口
			function show_register_window() {
				$("#register_window").css("display", "block");
			}
			//隐藏注册窗口
			function hide_register_window() {
				$("#register_window").css("display", "none");
			}
			//注册按钮事件
			function register() {
				//setTimeout("hide_register_window()", 200);
				var reg_account = $("#reg_account").val();
				if(null == reg_account || "" == reg_account){
					alert("请输入账号");
					return;
				}
				var reg_pwd = $("#reg_pwd").val();
				if(null == reg_pwd || "" == reg_pwd){
					alert("请输入密码");
					return;
				}
				var reg_cname = $("#reg_cname").val();
				if(null == reg_cname || "" == reg_cname){
					alert("请输入中文名");
					return;
				}
				var reg_mobile = $("#reg_mobile").val();
				if(null == reg_mobile || "" == reg_mobile){
					alert("请输入手机号");
					return;
				}
				
				var data = {
						"account" : reg_account,
						"pwd" : reg_pwd,
						"cname":reg_cname,
						"mobile":reg_mobile
				};
				
				//关键的一步来了:将前端输入的数据发送给后台控制器，怎么发送？？？
				$.ajax({
					url : "http://192.168.215.131:8080/university/register",
					type : "POST",
					data : data,
					dataType : "json",
					success : function(data) {
						if (data.status == "1") {
							alert("注册成功~");
							setTimeout("hide_register_window()", 200);
						} else {
							alert("错误");
						}
					}
				});
			}
			//取消注册
			function cancel_register() {
				setTimeout("hide_register_window()", 200);
			}
		</script>
	</head>
	<style type="text/css">
		html,
		body {
			padding: 0;
			margin: 0;
			width: 100%;
			height: 100%;
			background: #376B6D;
		}
	</style>

	<body>
		<div style="width: 100%;height: 75px;background: #305A56;text-align: center;line-height: 75px;font-size: 24px;color: white;">
			商品后台管理系统
		</div>
		<div style="margin: 0 auto;width: 30%;height: 50%;background: #E7E5DD;margin-top: 7%;border-radius: 12px;
			text-align: center;padding-top: 12px;">
			<div style="width: 100%;margin-top: 48px;color: #209E85;font-size: 18px;">登录</div>
			<div style="margin-top: 18px;height: 35px;width: 100%;">
				<input id="username" type="text" placeholder="用户名" style="outline: none;height: 80%;width: 60%;text-align: center;" />
			</div>
			<div style="margin-top: 18px;height: 35px;width: 100%;">
				<input id="pwd" type="password" placeholder="密码" style="outline: none;height: 80%;width: 60%;text-align: center;" />
			</div>
			<div style="margin-top: 18px;height: 35px;width: 100%;text-align: center;">
				<div onclick="login()" style="margin: 0 auto;height: 100%;line-height: 35px;width: 60%;text-align: center;background: #209E85;border-radius: 5px;color: white;">
					登录
				</div>
				<div onclick="show_register_window()" style="font-size: 14px;padding: 10px;color: #878787;">
					没有账号？注册
				</div>
			</div>
		</div>
		<!--注册-->
		<div id="register_window" style="position: absolute;width: 100%;height: 100%;background: rgba(0,0,0,0.5);top: 0;left: 0;display: none;">
			<div style="width: 30%;height: 70%;top: 15%;left: 35%;
				background: #E7E5DD;position: relative;
				border-radius: 12px;text-align: center;">
				<div style="margin: 0 auto;height: 80%;height: 90%;position: relative;top: 5%;
					padding-top: 5%;">
					<div style="width: 100%;color: #209E85;font-size: 18px;">注册</div>
					<div style="margin-top: 18px;height: 35px;width: 100%;">
						<input id="reg_account" type="text" placeholder="账号" style="outline: none;height: 80%;width: 60%;text-align: center;" />
					</div>
					<div style="margin-top: 18px;height: 35px;width: 100%;">
						<input id="reg_pwd" type="password" placeholder="密码" style="outline: none;height: 80%;width: 60%;text-align: center;" />
					</div>
					<div style="margin-top: 18px;height: 35px;width: 100%;">
						<input id="reg_cname" type="text" placeholder="中文名" style="outline: none;height: 80%;width: 60%;text-align: center;" />
					</div>
					<div style="margin-top: 18px;height: 35px;width: 100%;">
						<input id="reg_mobile" type="text" placeholder="手机号" style="outline: none;height: 80%;width: 60%;text-align: center;" />
					</div>
					<div style="margin-top: 25px;height: 35px;width: 100%;text-align: center;">
						<div onclick="register()" style="margin: 0 auto;height: 100%;line-height: 35px;width: 60%;text-align: center;background: #209E85;border-radius: 5px;color: white;">
							注册
						</div>
						<div onclick="cancel_register()" style="margin: 0 auto;height: 100%;line-height: 35px;width: 60%;text-align: center;background: #878787;border-radius: 5px;color: white;margin-top: 12px;">
							取消
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>

</html>