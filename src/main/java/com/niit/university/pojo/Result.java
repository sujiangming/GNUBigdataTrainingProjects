package com.niit.university.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

public class Result implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String goodsId;
	private String goodsName;
	private Integer goodsViewCount;
	
	public String getGoodsId() {
		return goodsId;
	}
	
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getGoodsViewCount() {
		return goodsViewCount;
	}
	public void setGoodsViewCount(Integer goodsViewCount) {
		this.goodsViewCount = goodsViewCount;
	}
}
