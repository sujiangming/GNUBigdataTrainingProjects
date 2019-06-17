package com.niit.university.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niit.university.dao.UserDao;
import com.niit.university.pojo.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Resource
	UserDao userDao;
	
	public void register(User user) {
		userDao.insertUser(user);
	}

	public User login(String account, String pwd) {
		return userDao.login(account, pwd);
	}

	public User getUserById(String account) {
		return userDao.getUserById(account);
	}

}
