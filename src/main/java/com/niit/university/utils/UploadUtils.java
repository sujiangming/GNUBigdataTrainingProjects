package com.niit.university.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
/**
 * @category 上传图片工具类
 * @author JDRY-SJM
 *
 */
public class UploadUtils {
	// 设置缩略图的宽度和高度
	public static final int witdth = 100;
	public static final int heigth = 100;

	/**
	 * @category 上传图片并返回图片的相对地址
	 * @param file
	 * @param realUploadPath
	 * @return
	 * @throws IOException
	 */
	public static String uploadImage(CommonsMultipartFile file, String realUploadPath) throws IOException {
		// 如果目录不存在则创建目录
		File uploadFile = new File(realUploadPath);
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		// 创建输入流
		InputStream inputStream = file.getInputStream();
		// 生成输出地址URL
		String outputPath = realUploadPath + file.getOriginalFilename();
		// 创建输出流
		OutputStream outputStream = new FileOutputStream(outputPath);
		// 设置缓冲区
		byte[] buffer = new byte[1024];

		// 输入流读入缓冲区，输出流从缓冲区写出
		while ((inputStream.read(buffer)) > 0) {
			outputStream.write(buffer);
		}
		outputStream.close();

		// 返回原图上传后的相对地址
		return "http://192.168.215.131:8080/images/" + file.getOriginalFilename();
	}

	/**
	 * @category 生成缩略图并且返回相对地址
	 * @param file
	 * @param realUploadPath
	 * @return
	 * @throws IOException
	 */
	public static String generateThumbnail(CommonsMultipartFile file, String realUploadPath) throws IOException {

		// 如果目录不存在则创建目录
		File uploadFile = new File(realUploadPath + "/thumbImages");
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		// 缩略图保存的绝对地址
		String des = realUploadPath + "/thumbImages/" + file.getOriginalFilename();
		// 生成缩略图
		Thumbnails.of(file.getInputStream()).size(witdth, heigth).toFile(des);
		// 返回缩略图的相对地址
		return "http://192.168.215.131:8080/images/thumbImages/" + file.getOriginalFilename();
	}

	/**
	 * @category 获取文件夹下所有文件名
	 * @param path
	 * @return
	 */
	public static List<String> printFile(String path) {
		File file = new File(path);
		List<String> images = new ArrayList<String>();

		// 是文件夹的话
		if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(path + "/" + filelist[i]);
				if (!readfile.isDirectory()) {
					images.add(readfile.getName());
				}
			}

		}
		return images;
	}
}
