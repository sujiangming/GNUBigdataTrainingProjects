package com.niit.university.crawler;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.niit.university.dao.JdGoodsDao;
import com.niit.university.pojo.JdGoodsInfo;

public class ParsePage implements Runnable {

	private String url;
	
	public ParsePage() {
	}

	public ParsePage(String url) {
		this.url = url;
	}

	/**
	 * @category 将每个页面的业务逻辑放在Runnable接口的run()方法中，这样可以调用多线程爬取每个页面。
	 */
	public void run() {
		// 通过构造函数插入的url,然后获取该URL的响应结果
		String contents = SpiderUtils.download(url);
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		// 获取所有节点
		TagNode tn = htmlCleaner.clean(contents);
		// 插入商品信息到数据库
		insertGoodInfo(tn);
	}

	/**
	 * @category 插入商品信息到数据库
	 * @param tagNode
	 */
	private void insertGoodInfo(TagNode tn) {
		// 获取商品ID、名称、价格、URL、图片URL、详情、插入日期，插入到MySQL
		// 个商品ID添加"jd_",这样可以和其他爬取的电商数据区分开来。
		String goodsId = String.valueOf(goodsId(tn).longValue()) ;//"jd_" + goodsId(tn);
		String goodsName = goodsName(tn);
		String goodsPrice = goodsPrice(tn);
		// 构造函数中的参数，初始化时传入的页面URL
		String goodsUrl = url;
		String goodsPicUrl = goodsPicUrl(tn);
		String goodsDetils = goodsDetils(tn);
		Date date = new Date();
		// MyDateUtils是个人封装的工具类
		String insertTime = MyDateUtils.formatDate2("yyyy-MM-dd hh:mm:ss", date);
		// MyDbUtils是个人封装的工具类
		MyDbUtils.save(goodsId, goodsName, goodsPrice, goodsUrl, goodsPicUrl, goodsDetils, insertTime, "");
//		JdGoodsInfo jdGoodsInfo = new JdGoodsInfo();
//		jdGoodsInfo.setGoodsId(goodsId);
//		jdGoodsInfo.setGoodsName(goodsName);
//		jdGoodsInfo.setGoodsPicUrl(goodsPicUrl);
//		jdGoodsInfo.setGoodsPrice(goodsPrice);
//		jdGoodsInfo.setGoodsUrl(goodsUrl);
//		jdGoodsInfo.setInsertTime(insertTime);
//		jdGoodsInfo.setGoodsDetail(goodsDetils);
//		
//		jdGoodsDao.save(jdGoodsInfo);
	}

	/**
	 * @category 获取商品详情
	 * @param tn
	 * @return
	 */
	private String goodsDetils(TagNode tn) {
		String xpath = "//*[@id=\"detail\"]/div[2]/div[1]/div[1]/ul[2]";
		StringBuilder info = new StringBuilder();
		Object[] objects = null;
		try {
			objects = tn.evaluateXPath(xpath);
		} catch (XPatherException e) {
			e.printStackTrace();
		}

		for (Object obj : objects) {
			TagNode node = (TagNode) obj;
			String goodInfo = node.getText().toString();
			info.append(goodInfo);
			info.append("--");
		}
		return info.toString();
	}

	/**
	 * @category 获取图片信息
	 * @param tn
	 * @return
	 */
	private String goodsPicUrl(TagNode tn) {
		// 商品图片的XPath
		String xpath = "//*[@id=\"spec-img\"]";
		Object[] objects = null;
		String picUrl = "";
		try {
			objects = tn.evaluateXPath(xpath);
		} catch (XPatherException e) {
			e.printStackTrace();
		}

		if (objects != null && objects.length > 0) {
			TagNode node = (TagNode) objects[0];
			// 通过节点的属性获取图片URL
			Map<String, String> map = node.getAttributes();
			if (map.containsKey("src")) {
				picUrl = node.getAttributeByName("src").toString();
			} else if (map.containsKey("data-origin")) {
				picUrl = node.getAttributeByName("data-origin").toString();
			}
		}

		return "http:" + picUrl;
	}

	/**
	 * @category 获取商品价格
	 * @param tn
	 * @return
	 */
	private String goodsPrice(TagNode tn) {
		String pricURl = "https://pe.3.cn/prices/mgets?source=wxsq&skuids=" + goodsId(tn);
		String con = SpiderUtils.download(pricURl);
		JSONArray jsonArray = new JSONArray(con);
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		String priceStr = jsonObject.getString("p");
		return priceStr;
	}

	/**
	 * @category 获取商品名称
	 * @param tn
	 * @return
	 */
	private String goodsName(TagNode tn) {
		// 商品名称的XPath
		String xpath = "//*[@id=\"detail\"]/div[2]/div[1]/div[1]/ul[2]/li[1]";
		Object[] objects = null;
		String name = "";

		try {
			objects = tn.evaluateXPath(xpath);
		} catch (XPatherException e) {
			e.printStackTrace();
		}

		if (objects != null && objects.length > 0) {
			TagNode node = (TagNode) objects[0];
			name = node.getText().toString();
		}
		return name;
	}

	/**
	 * @category 获取商品ID
	 * @param tn
	 * @return
	 */
	private Long goodsId(TagNode tn) {
		String xpath = "//*[@id=\"detail\"]/div[2]/div[1]/div[1]/ul[2]/li[2]";
		Object[] objects = null;
		try {
			objects = tn.evaluateXPath(xpath);
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		TagNode node = (TagNode) objects[0];
		// 获取商品编号
		String idInfo = node.getText().toString();// id=商品编号：6888610
		System.out.println("--before--" + idInfo);
		Long idLong = Long.valueOf(findDigit(idInfo));
		System.out.println("--after--" + idLong);
		return idLong;
	}
	
	/**
	 * @category 查找字符串中的数字部分
	 * 			   因为获取到的商品编号的格式是：商品编号：6888610	
	 * @param idStr
	 * @return
	 */
	public String findDigit(String idStr) {
		// 按指定模式在字符串查找
		String line = idStr;
		String pattern = "(\\D*)(\\d+)(.*)";
		// 创建 Pattern 对象
		Pattern r = Pattern.compile(pattern);
		// 现在创建 matcher 对象
		Matcher m = r.matcher(line);
		if (m.find()) {
			return m.group(2);
		} else {
			return "";
		}
	}
}
