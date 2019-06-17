package com.niit.university.service;

import java.util.List;

import com.niit.university.pojo.JdGoodsInfo;

public interface JdGoodsService {
	/**
	 * @category 保存京东商品
	 * @param jdGoodsInfo
	 */
	void save(JdGoodsInfo jdGoodsInfo);
	/**
	 * @category 全部查询
	 */
	List<JdGoodsInfo> qeury();
	
	/**
	 * @category 清空表
	 */
	void delete();
}
