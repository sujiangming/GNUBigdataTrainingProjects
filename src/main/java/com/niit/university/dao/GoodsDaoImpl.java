package com.niit.university.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.niit.university.pojo.Goods;

@Repository
public class GoodsDaoImpl implements GoodsDao {

	@Resource
	SessionFactory sessionFactory;

	/**
	 * @category 获取当前连接数据库的session对象
	 * @return
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * @param goods
	 * @category 新增或保存商品
	 */
	public void save(Goods goods) {
		Session session = getSession();
		Serializable id = session.save(goods);
		System.out.println("hibernate save方法返回结果：" + id);
	}

	/**
	 * @param goods
	 * @category 删除商品
	 */
	public void delete(Goods goods) {
		getSession().delete(goods);
	}

	/**
	 * @category 根据id删除商品
	 */
	public int deleteById(String goodsId) {
		SQLQuery query = getSession().createSQLQuery("delete from t_goods where id='" + goodsId + "'");
		int ret = query.executeUpdate();
		return ret;
	}

	/**
	 * @param goods
	 * @category 更新商品
	 */
	public void update(Goods goods) {
		getSession().update(goods);
	}

	/**
	 * @param goodsId
	 * @category 根据商品编号来查询
	 */
	public Goods queryById(String goodsId) {
		Object object = getSession().createQuery("from " + Goods.class.getName() + " where id='" + goodsId + "'")
				.uniqueResult();
		if (object == null) {
			return null;
		}
		return (Goods) object;
	}

	/**
	 * @category 查询全部商品
	 */
	@SuppressWarnings("unchecked")
	public List<Goods> queryAll() {
		return getSession().createQuery("from " + Goods.class.getName()).list();
	}

	/**
	 * @category 分页查询
	 */
	@SuppressWarnings("unchecked")
	public List<Goods> queryAll(int pageNum) {
		// 1.创建Criteria对象
		Criteria criteria = getSession().createCriteria(Goods.class);
		// 2.排序
		criteria.addOrder(Order.asc("goodsPrice"));// 按照商品价格升序
		// 3.分页
		criteria.setFirstResult(pageNum - 1);// 从什么位置开始，默认为0
		criteria.setMaxResults(10);// 最多检出的条数
		// 4.执行SQL
		List<Goods> list = criteria.list();
		return list;
	}

}
