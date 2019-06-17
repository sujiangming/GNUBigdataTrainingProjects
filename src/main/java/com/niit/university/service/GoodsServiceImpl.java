package com.niit.university.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niit.university.dao.GoodsDao;
import com.niit.university.pojo.Goods;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Resource
	GoodsDao goodsDao;

	public void save(Goods goods) {
		goodsDao.save(goods);
	}

	public void delete(Goods goods) {
		goodsDao.delete(goods);
	}

	public boolean deleteById(String goodsId) {
		int result = goodsDao.deleteById(goodsId);
		if (result < 0) {
			return false;
		}
		return true;
	}

	public void update(Goods goods) {
		goodsDao.update(goods);
	}

	public Goods queryById(String goodsId) {
		return goodsDao.queryById(goodsId);
	}

	public List<Goods> queryAll() {
		return goodsDao.queryAll();
	}

	public List<Goods> queryAll(int pageNum) {
		return goodsDao.queryAll(pageNum);
	}

}
