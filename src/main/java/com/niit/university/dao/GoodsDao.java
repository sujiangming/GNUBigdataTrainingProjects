package com.niit.university.dao;

import java.util.List;

import com.niit.university.pojo.Goods;

/**
 * @category 商品增删改查接口
 * @author JDRY-SJM
 *
 */
public interface GoodsDao {
	/**
	 * @category 新增或保存
	 * @param goods
	 */
	void save(Goods goods);

	/**
	 * @category 删除商品对象
	 * @param goods
	 */
	void delete(Goods goods);

	/**
	 * @category 根据id删除商品
	 * @param goods
	 */
	int deleteById(String goodsId);

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
