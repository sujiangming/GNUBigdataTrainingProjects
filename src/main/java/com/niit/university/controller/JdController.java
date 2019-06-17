package com.niit.university.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.niit.university.crawler.Spider;
import com.niit.university.pojo.JdGoodsInfo;
import com.niit.university.service.JdGoodsService;

@Controller
public class JdController {

	@Resource
	JdGoodsService jdGoodsService;

	@Resource
	Spider spider;

	@RequestMapping("jdGoods/crawler")
	public @ResponseBody String save(HttpServletRequest request, HttpServletResponse response) {

		// 开启爬虫
		spider.start();

		Map<String, String> result = new HashMap<String, String>();
		result.put("message", "成功爬取数据~");
		result.put("status", "1");

		return JSON.toJSONString(result);

	}

	@RequestMapping("jdGoods/query")
	public @ResponseBody String qeury(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<JdGoodsInfo> list = jdGoodsService.qeury();
		if (list == null || 0 == list.size()) {
			result.put("message", "没有查到数据~");
			result.put("status", "0");
			return JSON.toJSONString(result);
		}

		result.put("message", "查询成功~");
		result.put("status", "1");
		result.put("data", list);
		return JSON.toJSONString(result);

	}

	@RequestMapping("jdGoods/delete")
	public @ResponseBody String delete(HttpServletRequest request, HttpServletResponse response) {

		// 删除数据
		jdGoodsService.delete();

		Map<String, String> result = new HashMap<String, String>();
		result.put("message", "成功爬取数据~");
		result.put("status", "1");

		return JSON.toJSONString(result);
	}
}
