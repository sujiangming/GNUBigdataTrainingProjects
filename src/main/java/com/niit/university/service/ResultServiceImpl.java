package com.niit.university.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niit.university.dao.ResultDao;
import com.niit.university.pojo.Result;

@Service
@Transactional
public class ResultServiceImpl implements ResultService{
	
	@Resource
	ResultDao resultDao;
	
	public List<Result> getResultData() {
		System.out.println("-----service层被调用--------");
		List<Result> result = resultDao.getResultData();
		return result;
	}

}
