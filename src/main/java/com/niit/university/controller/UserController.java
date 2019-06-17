package com.niit.university.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.niit.university.pojo.User;
import com.niit.university.service.UserService;

@Controller
public class UserController {

	/**
	 * @category 依赖注入 DI IOC 
	 */
	@Resource
	UserService userService;// @Resource注解 等价于 userService = new UserServiceImpl();

	/**
	 * @category 注册
	 * @param request
	 * @param response
	 * @param session
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = "/register")
	public @ResponseBody String register(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam Map<String, String> parameter) {
		System.out.println("前台传递过来的数据：" + JSON.toJSONString(parameter));
		// 1、获取注册页面传递进来的账号密码中文名等参数
		String account = parameter.get("account");
		String pwd = parameter.get("pwd");
		String cname = parameter.get("cname");
		String mobile = parameter.get("mobile");

		// 思路：先使用account账号到数据库中查一遍，看看是否已经存在该账号
		User user = userService.getUserById(account);
		// 1、如果存在，则不进行注册，返回失败消息给前端页面进行提示
		if (null != user) {
			// 失败消息提示
			parameter.put("message", "该账号已经被注册了");
			// 失败状态码 0 表示失败
			parameter.put("status", "0");
			// 返回失败结果信息给前端页面，通过JSON将Map转换成String类型进行网络传输
			return JSON.toJSONString(parameter);
		}
		// 2、如果不存在，则进行注册，同样需要返回成功消息给前端页面进行提示
		user = new User();
		user.setAccount(account);
		user.setPwd(pwd);
		user.setCname(cname);
		user.setMobile(mobile);
		// 3、调用注册方法，执行注册操作
		userService.register(user);
		// 4、返回注册成功的信息给前端页面
		parameter.put("message", "恭喜您，注册成功~");
		parameter.put("status", "1"); // 成功状态码 1表示成功
		return JSON.toJSONString(parameter);
	}

	@RequestMapping("/login")
	public @ResponseBody String login(
			@RequestParam Map<String, String> parameter, 
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) {
		// 1、获取前端传递过来的数据
		String account = parameter.get("account");
		String pwd = parameter.get("pwd");
		// 2、使用前端参数请求查询数据库是否存在当前的用户
		User user = userService.login(account, pwd);
		// 3、判断当前用户是否存在，如果不存在，则返回给前端登录失败信息
		if (null == user) {
			parameter.put("message", "用户名或密码错误~");
			parameter.put("status", "0");
			return JSON.toJSONString(parameter);
		}
		//4、登录成功，需要将用户信息保存在request对象中，
		//   便于在前端页面获取到当前是哪一个用户正在登录系统
		session.setAttribute("userInfo", user);
		
		//5、构造登录成功返回给前端的数据
		parameter.put("message", "登录成功~");
		parameter.put("status", "1");
		//System.out.println("登录成功返回的结果数据：" + JSON.toJSONString(parameter));
		return JSON.toJSONString(parameter);

	}
}
