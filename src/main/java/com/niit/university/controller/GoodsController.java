package com.niit.university.controller;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.niit.university.pojo.Goods;
import com.niit.university.service.GoodsService;
import com.niit.university.utils.MyUtils;
import com.niit.university.utils.RemoteExecuteCommand;
import com.niit.university.utils.UploadUtils;

@Controller
public class GoodsController {

	/**
	 * @category 注释下
	 */
	@Resource
	GoodsService goodsService;

	/**
	 * @category 新增商品
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("goods/save")
	public @ResponseBody String save(@RequestParam Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");// 解决跨域问题
		// 1、获取页面传递来的参数
		String goodsCode = map.get("goodsCode").toString();
		String goodsName = map.get("goodsName").toString();
		String goodsPicUrl = map.get("goodsPicUrl").toString();
		String goodsType = map.get("goodsType").toString();
		Double goodsPrice = Double.valueOf(map.get("goodsPrice").toString());
		String goodsDesc = map.get("goodsDesc").toString();
		String insertTime = MyUtils.formatDate(new Date().getTime(), "yyyy-MM-dd hh:mm:ss");
		// 2、新建商品对象，并赋值
		Goods goods = new Goods();
		goods.setGoodsCode(goodsCode);
		goods.setGoodsDesc(goodsDesc);
		goods.setGoodsName(goodsName);
		goods.setGoodsPicUrl(goodsPicUrl);
		goods.setGoodsPrice(goodsPrice);
		goods.setGoodsType(goodsType);
		goods.setInsertTime(insertTime);

		// 3、执行新增操作，即将商品保存到MySQL数据库中
		goodsService.save(goods);

		// 4、组装结果数据返回给页面
		Map<String, String> result = new HashMap<String, String>();
		result.put("message", "新增成功");
		result.put("status", "1");

		return JSON.toJSONString(result);
	}

	/**
	 * @category 根据id删除商品
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("goods/deleteById")
	public @ResponseBody String deleteById(@RequestParam Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) {
		// 1、获取页面传递来的参数
		String goodsId = map.get("goodsId").toString();
		// 2、执行删除操作，即将MySQL数据库中商品删除
		boolean isDel = goodsService.deleteById(goodsId);
		// 3、组装结果数据返回给页面
		Map<String, String> result = new HashMap<String, String>();
		if (isDel) {
			result.put("message", "删除成功");
			result.put("status", "1");
			return JSON.toJSONString(result);
		}

		result.put("message", "删除失败");
		result.put("status", "0");
		return JSON.toJSONString(result);
	}

	/**
	 * @category 更新或修改商品
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("goods/update")
	public @ResponseBody String update(@RequestParam Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) {
		// 1、获取页面传递来的参数
		String goodsId = map.get("goodsId").toString();
		String goodsCode = map.get("goodsCode").toString();
		String goodsName = map.get("goodsName").toString();
		String goodsPicUrl = map.get("goodsPicUrl").toString();
		String goodsType = map.get("goodsType").toString();
		Double goodsPrice = Double.valueOf(map.get("goodsPrice").toString());
		String goodsDesc = map.get("goodsDesc").toString();
		String updateTime = MyUtils.formatDate(new Date().getTime(), "yyyy-MM-dd hh:mm:ss");
		// 2、新建商品对象，并赋值
		Goods goods = goodsService.queryById(goodsId);
		goods.setGoodsCode(goodsCode);
		goods.setGoodsDesc(goodsDesc);
		goods.setGoodsName(goodsName);
		goods.setGoodsPicUrl(goodsPicUrl);
		goods.setGoodsPrice(goodsPrice);
		goods.setGoodsType(goodsType);
		goods.setUpdateTime(updateTime);

		// 3、执行更新操作，即将商品更新到MySQL数据库中
		goodsService.update(goods);

		// 4、组装结果数据返回给页面
		Map<String, String> result = new HashMap<String, String>();
		result.put("message", "更新成功");
		result.put("status", "1");

		return JSON.toJSONString(result);
	}

	/**
	 * @category 查询所有的商品
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("goods/queryAll")
	public @ResponseBody String queryAll(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");// 解决跨域问题
		// 1、执行查询操作，即将MySQL数据库中商品查询出来
		List<Goods> list = goodsService.queryAll();
		// 2、组装结果数据返回给页面
		Map<String, Object> result = new HashMap<String, Object>();
		if (list == null || list.size() == 0) {
			result.put("message", "目前还没有任何商品，请先添加商品");
			result.put("status", "0");
			return JSON.toJSONString(result);
		}

		result.put("message", "查询成功");
		result.put("status", "1");
		result.put("data", list);
		return JSON.toJSONString(result);
	}

	/**
	 * @category 查询所有的商品
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("goods/queryById")
	public @ResponseBody String queryById(@RequestParam Map<String, String> map, HttpServletRequest request,
			HttpServletResponse response) {
		// 解决跨域问题
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 0、获取页面传递过来的参数
		String goodsId = map.get("goodsId");
		// 1、执行查询操作，即将MySQL数据库中商品查询出来
		Goods goods = goodsService.queryById(goodsId);
		// 2、组装结果数据返回给页面
		Map<String, Object> result = new HashMap<String, Object>();
		if (goods == null) {
			result.put("message", "没有该商品");
			result.put("status", "0");
			return JSON.toJSONString(result);
		}
		result.put("message", "查询成功");
		result.put("status", "1");
		result.put("data", goods);
		return JSON.toJSONString(result);
	}

	/**
	 * @category 文件上传并生成缩略图
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "goods/uploadImage", method = RequestMethod.POST)
	public @ResponseBody String uploadImageFile(@RequestParam CommonsMultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 解决跨域问题
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 根据相对路径获取绝对路径，图片上传后位于元数据中
		// 上线部署前徐需要修改成虚拟机上的tomcat安装路径下的webappps/images
		String realUploadPath = "/training/apache-tomcat-8.5.40/webapps/images/";// "E:\\apache-tomcat-8.5.16-windows-x64\\apache-tomcat-8.5.16\\webapps\\images\\";//
																					// request.getContextPath()
																					// // "/images";
		// 获取上传后原图的相对地址
		String imageUrl = UploadUtils.uploadImage(file, realUploadPath);
		// 获取生成的缩略图的相对地址
		String thumbImageUrl = UploadUtils.generateThumbnail(file, realUploadPath);

		Map<String, String> map = new HashMap<String, String>();
		map.put("imageUrl", imageUrl);
		map.put("thumbImageUrl", thumbImageUrl);

		System.out.println("上传图片：" + JSON.toJSONString(map));

		return JSON.toJSONString(map);
	}

	/**
	 * @category 查询所有的商品
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("goods/initABTestData")
	public @ResponseBody String initABTestData(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");// 解决跨域问题
		// 1、执行查询操作，即将MySQL数据库中商品查询出来
		List<Goods> list = goodsService.queryAll();
		// 2、组装结果数据返回给页面
		Map<String, Object> result = new HashMap<String, Object>();
		if (list == null || list.size() == 0) {
			result.put("message", "目前还没有任何商品，请先添加商品");
			result.put("status", "0");
			return JSON.toJSONString(result);
		}

		String str = "";

		for (Goods goods : list) {
			// 192.168.215.141这个虚拟机上的ip地址
			str += "http://192.168.215.141/shop/detail.html?id=" + goods.getId() + "\n";
		}

		System.out.println(str);

		FileWriter writer;

		try {
			writer = new FileWriter("/root/url.txt");
			writer.write(str);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		result.put("message", "查询成功");
		result.put("status", "1");
		result.put("data", list);
		return JSON.toJSONString(result);
	}

	/**
	 * @category AB压测生成用户访问数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("goods/dataGeneration")
	public @ResponseBody String dataGeneration(HttpServletRequest request, HttpServletResponse response) {
		// 1、构造返回前端页面的map对象
		Map<String, String> map = new HashMap<String, String>();
		// 2、使用SSH2登录远程Linux服务器，执行shell脚本
		String execResultStr = RemoteExecuteCommand.getInstance().execute("sh /root/ab_test.sh");
		if (!"".equals(execResultStr)) {
			map.put("message", "生成用户访问数据成功~");
			map.put("status", "1");
			map.put("data", execResultStr);

			return JSON.toJSONString(map);
		}

		map.put("message", "生成用户访问数据失败~");
		map.put("status", "0");
		map.put("data", "生成用户访问数据失败~");

		return JSON.toJSONString(map);
	}

	/**
	 * @category 数据的采集即用户访问日志被滚动到FlumeLogs目录下
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("goods/dataAcquisition")
	public @ResponseBody String dataAcquisition(HttpServletRequest request, HttpServletResponse response) {
		// 1、构造返回前端页面的map对象
		Map<String, String> map = new HashMap<String, String>();
		// 2、使用SSH2登录远程Linux服务器，执行shell脚本
		String execResultStr = RemoteExecuteCommand.getInstance().executeSuccess("sh /root/rollingLog.sh");
		if (!"".equals(execResultStr)) {
			map.put("message", "数据采集成功~");
			map.put("status", "1");
			map.put("data", execResultStr);

			return JSON.toJSONString(map);
		}

		map.put("message", "数据采集失败~");
		map.put("status", "0");
		map.put("data", "数据采集失败~");

		return JSON.toJSONString(map);
	}

	/**
	 * @category Flume实现数据存储到HDFS
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("goods/dataStorage")
	public @ResponseBody String dataStorage(HttpServletRequest request, HttpServletResponse response) {
		// 1、构造返回前端页面的map对象
		Map<String, String> map = new HashMap<String, String>();
		// 2、使用SSH2登录远程Linux服务器，执行shell脚本
		String execResultStr = RemoteExecuteCommand.getInstance().executeSuccess("sh /root/flume_to_hdfs.sh");
		if (!"".equals(execResultStr)) {
			map.put("message", "数据存储成功~");
			map.put("status", "1");
			map.put("data", execResultStr);

			return JSON.toJSONString(map);
		}

		map.put("message", "数据存储失败~");
		map.put("status", "0");
		map.put("data", "数据存储失败~");

		return JSON.toJSONString(map);
	}
	
	/**
	 * @category 数据的分析即执行MapReduce程序
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("goods/dataAnalysis")
	public @ResponseBody String dataAnalysis(@RequestParam Map<String, String> param, HttpServletRequest request, HttpServletResponse response) {
		//0、获取需要的分析的是HDFS哪一个目录下的数据
		String path = param.get("path");
		//1、拿到前端输入的HDFS的路径写入到Linux下mr_input_path.txt文件中
		FileWriter writer;
		try {
			writer = new FileWriter("/root/mr_input_path.txt");
			writer.write(path);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 3、使用SSH2登录远程Linux服务器，执行shell脚本
		String execResultStr = RemoteExecuteCommand.getInstance().executeSuccess("sh /root/exec_mr.sh");
		
		// 4、构造返回前端页面的map对象
		Map<String, String> map = new HashMap<String, String>();
		if (!"".equals(execResultStr)) {
			map.put("message", "数据分析成功~");
			map.put("status", "1");
			map.put("data", execResultStr);

			return JSON.toJSONString(map);
		}

		map.put("message", "数据分析失败~");
		map.put("status", "0");
		map.put("data", "数据分析失败~");

		return JSON.toJSONString(map);
	}
	
	/**
	 * @category 将MapReduce程序分析结果抽取到MySQL中
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("goods/dataSqoop")
	public @ResponseBody String dataSqoop(@RequestParam Map<String, String> param, HttpServletRequest request, HttpServletResponse response) {
		//0、获取需要的分析的是HDFS哪一个目录下的数据
		String path = param.get("path");
		//1、拿到前端输入的HDFS的路径写入到Linux下result_path.txt文件中
		FileWriter writer;
		try {
			writer = new FileWriter("/root/result_path.txt");
			writer.write(path);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 3、使用SSH2登录远程Linux服务器，执行shell脚本
		String execResultStr = RemoteExecuteCommand.getInstance().executeSuccess("sh /root/sqoop_to_mysql.sh");
		
		// 4、构造返回前端页面的map对象
		Map<String, String> map = new HashMap<String, String>();
		if (!"".equals(execResultStr)) {
			map.put("message", "结果数据抽取成功~");
			map.put("status", "1");
			map.put("data", execResultStr);

			return JSON.toJSONString(map);
		}

		map.put("message", "结果数据抽取失败~");
		map.put("status", "0");
		map.put("data", "结果数据抽取失败~");

		return JSON.toJSONString(map);
	}

}
