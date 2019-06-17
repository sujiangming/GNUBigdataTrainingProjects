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
import com.niit.university.pojo.Result;
import com.niit.university.service.ResultService;

@Controller
public class ResultController {

	@Resource
	ResultService resultService;

	@RequestMapping("result/getResultData")
	public @ResponseBody String getResultData(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");// 解决跨域问题
		List<Result> result = resultService.getResultData();
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == result || 0 == result.size()) {
			map.put("message", "没有数据");
			map.put("status", "0");
			map.put("data", "没有数据");
			return JSON.toJSONString(map);
		}

		map.put("message", "查询成功");
		map.put("status", "1");
		map.put("data", result);
		return JSON.toJSONString(map);
	}
}
