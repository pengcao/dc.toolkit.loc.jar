/*
 * Class: ResourceReader 
 * Description:
 * Version: 1.0
 * Author: caopeng
 * Creation date: 2015年10月20日
 * (C) Copyright 2010-2015 deamoncao.
 * All rights reserved.
 */
package dc.toolkit.lbs.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class ResourceReader {

	private static Logger logger = Logger.getLogger(ResourceReader.class);

	private static String PKG_NAME = ResourceReader.class.getPackage().getName();
	private static String MSG_BUNDLE_NAME = PKG_NAME + ".MessageBundle";
	private static ResourceBundle msgBundle;

	// 在ClassLoader第一次加载此类的时候调用本段语句。
	static {
		// 采用缺省locale加载消息资源。
		msgBundle = ResourceBundle.getBundle(MSG_BUNDLE_NAME);
	}

	public static void setLocal(Locale currentLocale) {
		msgBundle = ResourceBundle.getBundle(MSG_BUNDLE_NAME, currentLocale);
	}

	public static String getMsg(String key) {

		String msg = null;

		try {
			msg = msgBundle.getString(key);
		} catch (Throwable t) {
			logger.error(t.getMessage() + ". key: " + key, t);
		}

		return formatMsg(key, msg);

	}

	/**
	 * 把MessageBundle中的{}占位符用params数组内容填充后返回结果
	 * 
	 * @param key
	 * @param params
	 * @return
	 */
	public static String getMsg(String key, String params[]) {

		String msg = null;

		try {

			msg = msgBundle.getString(key);

			// 用参数数组填充"{}"
			if (msg != null) {
				MessageFormat mf = null;
				if (params != null && params.length > 0) {
					mf = new MessageFormat(msg, msgBundle.getLocale());
					msg = mf.format(params, new StringBuffer(), null).toString();
				}
			}

		} catch (Throwable t) {
			logger.error(t.getMessage() + ". key: " + key, t);
		}

		return formatMsg(key, msg);

	}

	/**
	 * 把MessageBundle中的{}占位符用params数组内容填充后返回结果
	 * 
	 * @param key
	 * @param params
	 * @return
	 */
	private static String formatMsg(String key, String msg) {
		if(msg==null)return null;
		return "[" + key + "] " + (msg == null ? key : msg);
	}

}
