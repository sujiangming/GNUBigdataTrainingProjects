package com.niit.university.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.niit.university.crawler.MyDbUtils;
import com.niit.university.pojo.JdGoodsInfo;

@Repository
public class JdGoodsDaoImpl implements JdGoodsDao {

	@Resource
	SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(JdGoodsInfo jdGoodsInfo) {
		
		getSession().save(jdGoodsInfo);
	}

	@SuppressWarnings("unchecked")
	public List<JdGoodsInfo> qeury() {
		Session session = getSession();
		return session.createQuery("from " + JdGoodsInfo.class.getName()).list();
	}

	public void delete() {
		Session session = getSession();
		session.createQuery("delete from t_jd_goods").executeUpdate();
	}

}
