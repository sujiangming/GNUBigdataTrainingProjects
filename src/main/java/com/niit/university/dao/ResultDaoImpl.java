package com.niit.university.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.niit.university.pojo.Result;

@Repository
public class ResultDaoImpl implements ResultDao{

	@Resource
	SessionFactory sessionFactory;
	
	public List<Result> getResultData() {
		Session session = sessionFactory.getCurrentSession();
		String queryString = "select t1.*,t2.goodsName from t_mr_result t1 LEFT JOIN t_goods t2 ON (t1.goodsId=t2.id) ORDER BY t1.goodsViewCount DESC;";//"SELECT t.goodsId AS \"商品编号\",SUM(t.goodsViewCount) AS \"总数\" FROM t_mr_result t GROUP BY t.goodsId ORDER BY SUM(t.goodsViewCount);";
		Query query = session.createSQLQuery(queryString);
		List<Result> list = query.list();
		return list;
	}

}
