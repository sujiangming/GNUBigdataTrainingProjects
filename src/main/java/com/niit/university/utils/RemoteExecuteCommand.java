package com.niit.university.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.apache.commons.lang.StringUtils;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * @category 远程执行linux的shell script
 * 
 * @author JDRY-SJM
 */
public class RemoteExecuteCommand {
	// 字符编码默认是utf-8
	private static String DEFAULTCHART = "UTF-8";
	private Connection conn;
	private String ip;
	private String userName;
	private String userPwd;

	public static volatile RemoteExecuteCommand instance;

	private RemoteExecuteCommand() {
		this.ip = "192.168.215.131";
		this.userName = "root";
		this.userPwd = "subenjiang";
	}

	public static RemoteExecuteCommand getInstance() {
		if (instance == null) {
			synchronized (RemoteExecuteCommand.class) {
				if (instance == null) {
					instance = new RemoteExecuteCommand();
				}
			}
		}

		return instance;
	}

	/**
	 * @category 远程登录linux的主机
	 * 
	 * @author JDRY-SJM
	 * @since V0.1
	 * @return 登录成功返回true，否则返回false
	 */
	public Boolean login() {
		boolean flg = false;
		try {
			conn = new Connection(ip);
			conn.connect();// 连接
			flg = conn.authenticateWithPassword(userName, userPwd);// 认证
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flg;
	}

	/**
	 * @category 远程执行shll脚本或者命令
	 * @param cmd 即将执行的命令
	 * @return 命令执行完后返回的结果值
	 * @since V0.1
	 */
	public String execute(String cmd) {
		String result = "";
		try {
			if (login()) {
				Session session = conn.openSession();// 打开一个会话
				session.execCommand(cmd);// 执行命令
				result = processStdout(session.getStdout(), DEFAULTCHART);
				// 如果为得到标准输出为空，说明脚本执行出错了
				if (StringUtils.isBlank(result)) {
					result = processStdout(session.getStderr(), DEFAULTCHART);
				}
				conn.close();
				session.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @category 远程执行shell脚本或者命令
	 * @param cmd 即将执行的命令
	 * @return 命令执行成功后返回的结果值，如果命令执行失败，返回空字符串，不是null
	 * @since V0.1
	 */
	public String executeSuccess(String cmd) {
		String result = "";
		try {
			if (login()) {
				Session session = conn.openSession();// 打开一个会话
				session.execCommand(cmd);// 执行命令
				result = processStdout(session.getStdout(), DEFAULTCHART);
				conn.close();
				session.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @category 解析脚本执行返回的结果集
	 * @author JDRY-SJM
	 * @param in      输入流对象
	 * @param charset 编码
	 * @since V0.1
	 * @return 以纯文本的格式返回
	 */
	private String processStdout(InputStream in, String charset) {
		InputStream stdout = new StreamGobbler(in);
		StringBuffer buffer = new StringBuffer();
		;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset));
			String line = null;
			while ((line = br.readLine()) != null) {
				buffer.append(line + "\n");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public static void setCharset(String charset) {
		DEFAULTCHART = charset;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
}
