package com.niit.university.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niit.university.dao.JdGoodsDao;
import com.niit.university.pojo.JdGoodsInfo;

@Service
@Transactional
public class JdGoodsServiceImpl implements JdGoodsService {

	@Resource
	JdGoodsDao jdGoodsDao;

	public void save(String goodsId, String goodsName, String goodsPrice, String goodsUrl, String goodsPicUrl,
			String goodsDetils, String insertTime, String updateTime) {

	}

	public void save(JdGoodsInfo jdGoodsInfo) {
		jdGoodsDao.save(jdGoodsInfo);
	}

	public List<JdGoodsInfo> qeury() {
		return jdGoodsDao.qeury();
	}

	public void delete() {
		jdGoodsDao.delete();
	}

}
