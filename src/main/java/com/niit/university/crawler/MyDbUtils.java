package com.niit.university.crawler;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.stereotype.Component;

public class MyDbUtils {

	// 根据自己的数据库地址修改
	static DataSource ds = getDataSource("jdbc:mysql://localhost:3306/university?useUnicode=true&characterEncoding=UTF-8");
	static QueryRunner qr = new QueryRunner(ds);

	/**
	 * @category 连接数据库驱动
	 * @param connectURI
	 * @return
	 */
	public static DataSource getDataSource(String connectURI) {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");// MySQL的jdbc驱动
		ds.setUsername("root"); // 所要连接的数据库名
		ds.setPassword("123456"); // MySQL的登陆密码
		ds.setUrl(connectURI);
		return ds;
	}

	// 第一类方法
	public static void executeUpdate(String sql) {
		try {
			qr.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @category 保存商品信息方法
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
		
		//拼接SQL语句
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
		//插入数据库
		executeUpdate(sql);
	}

}
