package com.niit.university.service;

import com.niit.university.pojo.User;

public interface UserService {
	/**
	 * @category 注册
	 * @param user
	 */
	public void register(User user);
	/**
	 * @category 登录
	 * @param account
	 * @param pwd
	 * @return
	 */
	public User login(String account,String pwd);
	/**
	 * @category 根据账号获取用户
	 * @param acocunt
	 * @return
	 */
	public User getUserById(String account);
}
