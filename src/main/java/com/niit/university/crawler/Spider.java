package com.niit.university.crawler;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class Spider {

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		Spider spider = new Spider();
//		spider.start();
//	}

	public void start() {
		// TODO Auto-generated method stub
		System.out.println("==========开始启动爬虫=============");
		// 爬取本周热卖网页
		String content = SpiderUtils.download("https://list.jd.com/list.html?tid=1000157");
		// 匹配这个网页所有商品网址
		Pattern compile = Pattern.compile("//item.jd.com/([0-9]+).html");
		// 使用正则进行匹配
		Matcher matcher = compile.matcher(content);
		// 使用正则进行查找,查找过程中可能会出现重复的URL，所以我们需要存入HashSet从而保证URL唯一
		HashSet<String> hashSet = new HashSet<String>();
		String goodId = "";
		// 使用正则进行查找
		while (matcher.find()) {
			String goodURL = matcher.group(0);
			hashSet.add(goodURL);
		}
		for (String goodUrl : hashSet) {
			Thread th = new Thread(new ParsePage("https:" + goodUrl));
			th.start();
		}
	}

}
