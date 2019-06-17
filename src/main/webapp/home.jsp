<%@page import="org.hibernate.Session"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.niit.university.pojo.User"%>
<%
	HttpSession httpSession = request.getSession();
	User user = (User) httpSession.getAttribute("userInfo");
	request.setAttribute("user", user.getCname());
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>商品管理系统</title>
<meta name="viewport"
	content="width=device-width,height-device-height,initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
<link rel="stylesheet" href="css/admin_css.css" />
</head>

<body>
	<!--头部-->
	<header>
		<div class="header_wrapper">
			<img src="img/baoma.jpg" /> <font style="font-weight: bold;">后台管理系统</font>
			<div class="header_wrapper_lastchild">
				<font id="username_">${user}</font> <font>|</font> <font
					onclick="logout()">退出</font>
			</div>
		</div>
	</header>
	<!--中间部分-->
	<section>
		<!--左边主要菜单-->
		<div class="left_menu">
			<ul class="ul_css">
				<li id="menu_index">首页</li>
				<li id="menu_univ">商品管理</li>
				<li id="menu_spider">网络爬虫</li>
				<li id="menu_prepare">数据准备</li>
				<li id="menu_generate">数据生成</li>
				<li id="menu_acquisition">数据采集</li>
				<li id="menu_storage">数据存储</li>
				<li id="menu_analysis">数据分析</li>
				<li id="menu_handle">数据处理</li>
				<li id="menu_view">数据可视化</li>
			</ul>
		</div>
		<!--右边内容区-->
		<div class="right_content">
			<div
				style="width: 100%; height: 35px; background: white; line-height: 35px;">
				<font id="main_title" style="margin-left: 12px;">首页</font>
			</div>

			<!--主要内容区域-->
			<div id="main_content"></div>

			<!--添加窗口部分，默认是隐藏的-->
			<div id="add_window">
				<div class="first_div">
					<div class="first_first_div">
						<!--新增弹出顶部-->
						<div class="add_window_content_top">
							<font id="addWinDesc">新增/编辑商品</font> <img id="close_window"
								src="img/close_btn.png" />
						</div>
						<!--表单上半部分内容-->
						<div style="width: 100%; height: auto; position: relative;">
							<div class="add_window_desc">
								<font>* 商品编号</font> <input id="goodsCode" type="text" />
							</div>
							<div class="add_window_desc">
								<font>* 商品名称</font> <input type="text" id="goodsName" />
							</div>
							<div class="add_window_desc">
								<font>* 商品价格</font> <input type="text" id="goodsPrice" />
							</div>
							<div class="add_window_desc">
								<font>* 商品类型</font> <select id="goodsType" name="goodsType">
									<option value="商品类型一">商品类型一</option>
									<option value="商品类型二">商品类型二</option>
									<option value="商品类型三">商品类型三</option>
								</select>
							</div>
							<div class="add_window_desc">
								<font>* 商品简介</font> <input id="goodsDesc" name="goodsDesc"
									type="text" />
							</div>

							<div class="add_window_desc">
								<font>* 上传图片</font> <input id="file" type="file" name="image"
									onchange="choose_image()">
							</div>

							<div style="height: 120px; margin-left: 27%; margin-right: 13%;">

								<img id="showChooseImage" style="width: 50%; height: 100%;" />

							</div>

							<!--关闭按钮-->
							<div
								style="width: 100%; height: 50px; text-align: center; margin-top: 30px;">
								<button
									style="width: 60px; height: 35px; background: #787878; margin-right: 20%;"
									onclick="close_add_win()">关闭</button>
								<button id="saveBtn"
									style="width: 60px; height: 35px; background: #209e85;"
									onclick="addGoods()">保存</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--增加删除按钮对话提示框-->
			<div id="add_dialog_window"
				style="width: 100%; height: 95%; background: rgba(0, 0, 0, 0.5); position: absolute; top: 0; overflow-y: scroll; display: none;">
				<div
					style="width: 30%; height: 30%; background: white; margin: 0 auto; position: relative; top: 25%; border-radius: 10px;">
					<div
						style="width: 100%; height: 40px; background: #008EBC; position: relative; border-top-left-radius: 10px; border-top-right-radius: 10px; line-height: 40px;">
						<font style="margin-left: 12px; font-size: 18px; color: white;">温馨提示：</font>
					</div>
					<div
						style="width: 100%; height: 50%; display: -webkit-box; -webkit-box-align: center; -webkit-box-pack: center;">
						<font style="margin: 0 auto; font-size: 16px; color: #353535;">您确定需要进行删除操作吗？</font>
					</div>
					<div
						style="width: 100%; height: 45px; text-align: right; line-height: 45px;">
						<button style="background-color: #878787;"
							onclick="hide_dialog();">取消</button>
						<button style="margin-right: 40px; margin-left: 15px;"
							onclick="deleteGoodsById();">确定</button>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	//		绑定事件定义和用法
	//		当 DOM（document object model 文档对象模型）加载完毕且页面完全加载（包括图像）时发生 ready 事件。
	//		由于该事件在文档就绪后发生，因此把所有其他的 jQuery 事件和函数置于该事件中是非常好的做法。如上面的实例所示。
	//		ready() 方法规定当 ready 事件发生时执行的代码。
	//		提示：ready() 方法不应该与 <body onload=""> 一起使用。

	$(function() {
		$('#main_content').load('welcome.html');
	});

	$(document).ready(function() {
		//1、首页菜单点击事件
		$("#menu_index").click(function() {
			$("#main_title").html($("#menu_index").text());
			$('#main_content').load('welcome.html');
		});
		//2、商品管理菜单点击事件
		$("#menu_univ").click(function() {
			$("#main_title").html($("#menu_univ").text());
			//下面的调用方法是解决js加载新页面进来后无法调用js方法的方法
			loadGoodsListView();
		});
		//3、网络爬虫菜单点击事件
		$("#menu_spider").click(function() {
			$("#main_title").html($("#menu_spider").text());
			//下面的调用方法是解决js加载新页面进来后无法调用js方法的方法
			loadCrawlerGoodsList();
		});
		//4、数据准备菜单点击事件
		$("#menu_prepare").click(function() {
			$("#main_title").html($("#menu_prepare").text());
			initDataMenu("initABTestData");
		});
		//5、数据生成菜单点击事件
		$("#menu_generate").click(function() {
			$("#main_title").html($("#menu_generate").text());
			initDataMenu("dataGeneration");
		});
		//6、数据采集菜单点击事件
		$("#menu_acquisition").click(function() {
			$("#main_title").html($("#menu_acquisition").text());
			initDataMenu("dataAcquisition");
		});
		//7、数据存储菜单点击事件
		$("#menu_storage").click(function() {
			$("#main_title").html($("#menu_storage").text());
			initDataMenu("dataStorage");
		});
		//8、数据分析菜单点击事件
		$("#menu_analysis").click(function() {
			$("#main_title").html($("#menu_analysis").text());
			$('#main_content').load('analysis.html');
		});
		//9、数据处理菜单点击事件
		$("#menu_handle").click(function() {
			$("#main_title").html($("#menu_handle").text());
			$('#main_content').load('sqoop.html');
		});
		//10、数据可视化菜单点击事件
		$("#menu_view").click(function() {
			$("#main_title").html($("#menu_view").text());
			$('#main_content').load('result.html', function() {
				//调用后台接口
				getResultData();
			});
		});
	});
	
	//商品管理菜单所对应的数据页面
	function loadGoodsListView() {
		$('#main_content').load('goods_list.html', function() {
			console.log("添加的html内部js事件");
			//1、新增弹出窗口事件
			$("#add_operate").click(function() {
				open_add_win();
			});
			//2、关闭弹出窗口事件
			$("#close_window").click(function() {
				close_add_win();
			});

			//3、测试新增表格内容
			$("#next_btn").click(function() {
				alert("暂无数据");
			});
		});
	}
	//网络爬虫菜单所对应的数据页面
	function loadCrawlerGoodsList() {
		$('#main_content').load('crawler_goods_list.html', function() {
			//1、新增弹出窗口事件
			$("#add_operate").click(function() {
				open_add_win();
			});
			//2、关闭弹出窗口事件
			$("#close_window").click(function() {
				close_add_win();
			});

			//3、测试新增表格内容
			$("#next_btn").click(function() {
				alert("暂无数据");
			});
		});
	}
	
	//以数据开头的菜单页面
	function initDataMenu(name) {
		$('#main_content').load('common.html', function() {
			//调用后台接口
			init(name);
		});
	}

	//打开新增窗口
	function open_add_win() {
		//新增前先清除之前可能被赋值过的input标签
		clearAddWindowValues();
		$("#addWinDesc").html("新增商品");
		$("#saveBtn").attr("onclick", "addGoods()");
		$("#add_window").css("display", "block");
	}

	//关闭新增窗口
	function close_add_win() {
		$("#add_window").css("display", "none");
	}

	var goodsUniqueId = null;

	//打开编辑窗口
	function edit_win(id) {
		$("#addWinDesc").html("编辑商品");
		$("#saveBtn").attr("onclick", "updateGoods()");
		$("#add_window").css("display", "block");

		var json = {
			goodsId : id
		}

		//根据商品id查询商品信息
		$.ajax({
			url : "http://192.168.215.131:8080/university/goods/queryById",
			type : "POST",
			data : json,
			dataType : "json",
			success : function(ret) {
				console.log(ret.data);
				if (ret.status == "1") {
					var obj = ret.data; //先给input标签赋值
					$("#goodsCode").val(obj.goodsCode);
					$("#goodsName").val(obj.goodsName);
					$("#goodsPrice").val(obj.goodsPrice);
					$("#goodsType").val(obj.goodsType);
					$("#goodsDesc").val(obj.goodsDesc);
					$("#showChooseImage").attr("src", obj.goodsPicUrl);

					//复制给全局变量
					goodsUniqueId = id;
				}
			}
		});

	}
	//清除新增弹窗的input标签中的值
	function clearAddWindowValues() {
		$("#goodsCode").val("");
		$("#goodsName").val("");
		$("#goodsPrice").val("");
		$("#goodsType").val("");
		$("#goodsDesc").val("");
		$("#showChooseImage").attr("src", "");
	}

	//定义一个全局变量，用于存放欲被删除的行
	var deleteValue = null;

	//显示删除提示对话框
	function show_dialog(el) {
		$("#add_dialog_window").css("display", "block");
		deleteValue = $(el).attr("value");
	}

	//隐藏删除提示对话框
	function hide_dialog() {
		//清空全局变量
		deleteValue = null;
		$("#add_dialog_window").css("display", "none");
	}

	//根据ID删除商品
	function deleteGoodsById() {

		if (deleteValue == null) {
			alert("当前商品信息不全，不能删除");
			return;
		}

		var json = {
			goodsId : deleteValue
		}

		//根据商品id查询商品信息
		$.ajax({
			url : "http://192.168.215.131:8080/university/goods/deleteById",
			type : "POST",
			data : json,
			dataType : "json",
			success : function(ret) {
				console.log(ret.data);
				if (ret.status == "1") {
					//关闭对话框
					hide_dialog();
					//重新刷新页面
					loadGoodsListView();
				}
			}
		});

	}

	//退出
	function logout() {
		window.location.href = "login.jsp";
	}
	
	//退出
	function openDataViews() {
		window.location.href = "result.html";
	}

	//上传图片并且预览
	function choose_image() {
		var formData = new FormData();
		formData.append('file', $('#file')[0].files[0]);
		$.ajax({
			url : 'http://192.168.215.131:8080/university/goods/uploadImage',
			type : 'POST',
			data : formData,
			processData : false, // tell jQuery not to process the data
			contentType : false, // tell jQuery not to set contentType
			success : function(data) {
				data = JSON.parse(data);
				$("#showChooseImage").attr("src", data.imageUrl);
			}
		});
	}

	//新增商品
	function addGoods() {
		var goodsCode = $("#goodsCode").val();
		if (goodsCode == null || goodsCode == "") {
			alert("请输入商品编号");
			return;
		}
		var goodsName = $("#goodsName").val();
		if (goodsName == null || goodsName == "") {
			alert("请输入商品名称");
			return;
		}
		var goodsPrice = $("#goodsPrice").val();
		if (goodsPrice == null || goodsPrice == "") {
			alert("请输入商品的价格");
			return;
		}
		var goodsDesc = $("#goodsDesc").val();
		if (goodsDesc == null || goodsDesc == "") {
			alert("请输入商品描述信息");
			return;
		}
		var goodsType = $("#goodsType").val();
		if (goodsType == null || goodsType == "") {
			alert("请选择商品类型");
			return;
		}

		var goodsPicUrl = $("#showChooseImage").attr("src");
		if (goodsPicUrl == null || goodsPicUrl == "") {
			alert("请选择图片");
			return;
		}

		var json = {
			goodsCode : goodsCode,
			goodsName : goodsName,
			goodsPrice : goodsPrice,
			goodsType : goodsType,
			goodsDesc : goodsDesc,
			goodsPicUrl : goodsPicUrl
		}

		$.ajax({
			type : "POST",
			url : "http://192.168.215.131:8080/university/goods/save",
			data : json,
			dataType : "json",
			success : function(result) {
				console.log(JSON.stringify(result));
				if (result.status == 1) {
					//成功后需要关闭当前的窗口
					close_add_win();
					//重新加载一遍，相当于刷新一次
					loadGoodsListView();
				}
			}
		});
	}

	//新增商品
	function updateGoods() {

		if (goodsUniqueId == null) {
			alert("该商品没有ID");
			return;
		}

		var goodsCode = $("#goodsCode").val();
		if (goodsCode == null || goodsCode == "") {
			alert("请输入商品编号");
			return;
		}
		var goodsName = $("#goodsName").val();
		if (goodsName == null || goodsName == "") {
			alert("请输入商品名称");
			return;
		}
		var goodsPrice = $("#goodsPrice").val();
		if (goodsPrice == null || goodsPrice == "") {
			alert("请输入商品的价格");
			return;
		}
		var goodsDesc = $("#goodsDesc").val();
		if (goodsDesc == null || goodsDesc == "") {
			alert("请输入商品描述信息");
			return;
		}
		var goodsType = $("#goodsType").val();
		if (goodsType == null || goodsType == "") {
			alert("请选择商品类型");
			return;
		}

		var goodsPicUrl = $("#showChooseImage").attr("src");
		if (goodsPicUrl == null || goodsPicUrl == "") {
			alert("请选择图片");
			return;
		}

		var json = {
			goodsId : goodsUniqueId,
			goodsCode : goodsCode,
			goodsName : goodsName,
			goodsPrice : goodsPrice,
			goodsType : goodsType,
			goodsDesc : goodsDesc,
			goodsPicUrl : goodsPicUrl
		}

		$.ajax({
			type : "POST",
			url : "http://192.168.215.131:8080/university/goods/update",
			data : json,
			dataType : "json",
			success : function(result) {
				if (result.status == 1) {
					//成功后需要关闭当前的窗口
					close_add_win();
					//重新加载一遍，相当于刷新一次
					loadGoodsListView();
					//重新给全局变量复制为null
					goodsUniqueId = null;
				}
			}
		});
	}
	
	//初始化被测试商品url地址
	function initABData(){
		$.ajax({
			type : "GET",
			url : "http://192.168.215.131:8080/university/goods/initABTestData",
			dataType : "json",
			success : function(result) {
				if (result.status == 1) {
					alert(result.message);
				}
			}
		});
	}
	
	//AB压测生成数据
	function dataGeneration(){
		$.ajax({
			type : "GET",
			url : "http://192.168.215.131:8080/university/goods/dataGeneration",
			dataType : "json",
			success : function(result) {
				console.log(JSON.stringify(result));
				if (result.status == 1) {
					alert(result.message);
				}
			}
		});
	}
	
	//数据采集
	function dataAcquisition(){
		$.ajax({
			type : "GET",
			url : "http://192.168.215.131:8080/university/goods/dataAcquisition",
			dataType : "json",
			success : function(result) {
				console.log(JSON.stringify(result));
				if (result.status == 1) {
					alert(result.message);
				}
			}
		});
	}
	
	//数据存储
	function dataStorage(){
		$.ajax({
			type : "GET",
			url : "http://192.168.215.131:8080/university/goods/dataStorage",
			dataType : "json",
			success : function(result) {
				console.log(JSON.stringify(result));
				if (result.status == 1) {
					alert(result.message);
				}
			}
		});
	}
	
	//数据分析
	function dataAnalysis(){
		$.ajax({
			type : "GET",
			url : "http://192.168.215.131:8080/university/goods/dataAnalysis",
			dataType : "json",
			success : function(result) {
				console.log(JSON.stringify(result));
				if (result.status == 1) {
					alert(result.message);
				}
			}
		});
	}
	
</script>
</html>