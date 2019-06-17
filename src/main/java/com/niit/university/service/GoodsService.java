package com.niit.university.service;

import java.util.List;

import com.niit.university.pojo.Goods;

public interface GoodsService {
	/**
	 * @category 新增或保存
	 * @param goods
	 */
	void save(Goods goods);

	/**
	 * @category 删除商品
	 * @param goods
	 */
	void delete(Goods goods);

	/**
	 * @category 删除商品
	 * @param goods
	 */
	boolean deleteById(String goodsId);

	/**
	 * @category 更新商品
	 * @param goods
	 */
	void update(Goods goods);

	/**
	 * @category 根据id来查询
	 * @param goodsId
	 * @return
	 */
	Goods queryById(String goodsId);

	/**
	 * @category 查询全部
	 * @return
	 */
	List<Goods> queryAll();

	/**
	 * @category 分页查询
	 * @param pageNum 当前页码数
	 * @return
	 */
	List<Goods> queryAll(int pageNum);
}
