package com.niit.university.crawler;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.stereotype.Component;

public class MyDbUtils {

	// �����Լ������ݿ��ַ�޸�
	static DataSource ds = getDataSource("jdbc:mysql://localhost:3306/university?useUnicode=true&characterEncoding=UTF-8");
	static QueryRunner qr = new QueryRunner(ds);

	/**
	 * @category �������ݿ�����
	 * @param connectURI
	 * @return
	 */
	public static DataSource getDataSource(String connectURI) {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");// MySQL��jdbc����
		ds.setUsername("root"); // ��Ҫ���ӵ����ݿ���
		ds.setPassword("123456"); // MySQL�ĵ�½����
		ds.setUrl(connectURI);
		return ds;
	}

	// ��һ�෽��
	public static void executeUpdate(String sql) {
		try {
			qr.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @category ������Ʒ��Ϣ����
	 * @param goodsId
	 * @param goodsName
	 * @param goodsPrice
	 * @param goodsUrl
	 * @param goodsPicUrl
	 * @param goodsDetils
	 * @param curr_time
	 */
	public static void save(
			String goodsId, 
			String goodsName, 
			String goodsPrice, 
			String goodsUrl, 
			String goodsPicUrl,
			String goodsDetils, 
			String insertTime,
			String updateTime) {
		
		//ƴ��SQL���
		String sql = "insert into t_jd_goods (goodsId,goodsName,goodsPrice,goodsUrl,goodsPicUrl,goodsDetail,insertTime,updateTime) VALUES ('"
				+goodsId + "','" 
				+ goodsName + "','" 
				+ goodsPrice + "','" 
				+ goodsUrl + "','"
				+ goodsPicUrl + "','"
				+ goodsDetils.trim() + "','"
				+ insertTime + "','"
				+ updateTime
				+"')";
		//�������ݿ�
		executeUpdate(sql);
	}

}
