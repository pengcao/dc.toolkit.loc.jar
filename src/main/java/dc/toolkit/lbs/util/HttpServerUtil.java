/**
 * Description: 
 * Author: caopeng
 * Creation time: 2016年3月30日 下午2:49:42
 * (C) Copyright 2013-2016, deamoncao.
 * All rights reserved.
 */
package dc.toolkit.lbs.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.log4j.Logger;
import dc.toolkit.lbs.exception.SvcMsgCode;

public class HttpServerUtil {

	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	private static Logger logger = Logger.getLogger(HttpServerUtil.class);

	/**
	 * 
	 * Description: 通过http请求来获取数据 
	 * Author: caopeng 
	 * Creation time: 2016年3月30日
	 * 下午2:50:46
	 *
	 * @param strUrl
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param method
	 *            请求方法
	 * @return 网络请求字符串n
	 * @throws Exception
	 */
	public static String getResultByHttp(String strUrl, Map params, String method) {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if (method == null || method.equals("GET")) {
				strUrl = strUrl + "?" + urlEncode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if (method == null || method.equals("GET")) {
				conn.setRequestMethod("GET");
			} else {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if (params != null && method.equals("POST")) {
				try {
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(urlEncode(params));
				} catch (Throwable t) {
					// TODO: Throwable thandle exception
					logger.error(SvcMsgCode._100101, t);
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (Throwable t) {
			logger.error(SvcMsgCode._100100, t);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Throwable t) {
					// TODO Auto-generated catch block
					logger.error(SvcMsgCode._100102, t);
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}
	
	
	public static String getResultByUrl(String strUrl, Map params, String method) {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if (method == null || method.equals("GET")) {
				strUrl = strUrl + "?" + urlEncode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if (method == null || method.equals("GET")) {
				conn.setRequestMethod("GET");
			} else {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
//			conn.set
			conn.connect();
			if (params != null && method.equals("POST")) {
				try {
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(urlEncode(params));
				} catch (Throwable t) {
					// TODO: Throwable thandle exception
					logger.error(SvcMsgCode._100101, t);
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (Throwable t) {
			logger.error(SvcMsgCode._100100, t);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Throwable t) {
					// TODO Auto-generated catch block
					logger.error(SvcMsgCode._100102, t);
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	/**
	 * 
	 * Description: 将map型转为请求参数型 
	 * Author: caopeng 
	 * Creation time: 2016年3月30日
	 * 下午2:50:55
	 *
	 * @param param
	 * @return
	 */
	public static String urlEncode(Map<String, Object> param) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : param.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
			} catch (Throwable t) {
				logger.error(SvcMsgCode._100103, t);
			}
		}
		return sb.toString();
	}

}
