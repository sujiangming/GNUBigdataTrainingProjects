package com.niit.university.dao;

import com.niit.university.pojo.User;

public interface UserDao {
	/**
	 * @category 新增用户
	 * @param user
	 */
	public void insertUser(User user);
	
	/**
	 * @category 根据用户密码登录
	 * @param account
	 * @param pwd
	 * @return
	 */
	public User login(String account,String pwd);
	
	/**
	 * @category 根据用户账号获取用户
	 * @param account
	 * @return
	 */
	public User getUserById(String account);
	
	
}
