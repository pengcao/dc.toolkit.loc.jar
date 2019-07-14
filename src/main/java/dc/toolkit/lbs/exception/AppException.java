/**
 * Description: 该类用于将Runtime和非Runtime异常封装为具有统一编码的应用异常。
 * 强制要求通过expCode来统一异常消息，不提供参数直接为异常消息的构造函数，例如：AppException(String expMsg, Throwable cause)。
 * Author: caopeng
 * Creation time: 2015年10月22日 上午11:34:36
 * (C) Copyright 2013-2016, deamoncao.
 * All rights reserved.
 */
package dc.toolkit.lbs.exception;

import java.util.ArrayList;
import java.util.List;

import dc.toolkit.lbs.i18n.ResourceReader;


public class AppException extends Exception {

	private static final long serialVersionUID = 1L;

	private String expCode; // 错误代码
	private String expMsg; // 错误消息
	private String childMsg; // 子异常的消息
	private List<AppException> childExpList; // 子异常列表

	private String respTimestamp; // 响应时间
	private String hostName; // 异常发出的主机名
	private String ipAddr; // 异常发出的主机IP
	private String compId; // 异常发出的组件Id
	private String compName; // 异常发出的组件名称
	private String expStackTrace; // 针对胖Java客户端情况，客户端需要记录异常的栈信息以便于前后端联调。

	/**
	 * Description: 
	 * Author: caopeng
	 * Creation time: 2015年10月22日 下午2:09:33
	 *
	 * @param expCode
	 */
	private void init(String expCode, String expMsg) {
		this.expCode = expCode;
		this.expMsg = expMsg;
		this.childMsg = "";
		childExpList = new ArrayList<AppException>();
	}

	/**
	 * Description: 在未提供消息代码的情况下构建对象。
	 * Author: caopeng
	 * Creation time: 2015年10月22日 上午11:40:39
	 */
	public AppException(String expMsg, Throwable cause) {
		super(expMsg, cause);
		init("", expMsg);
	}

	/**
	 * Description: 在提供了消息代码、消息参数和根异常的情况下构建对象。
	 * Author: caopeng
	 * Creation time: 2015年10月22日 上午11:46:56
	 */
	public AppException(String expCode, String[] msgParams, Throwable cause) {
		super(ResourceReader.getMsg(expCode, msgParams), cause);
		init(expCode, ResourceReader.getMsg(expCode, msgParams));
	}

	/**
	 * 追加所包含的子异常。
	 * 
	 * @param expCode
	 * @param msgParams
	 */
	public void addChildExp(AppException childExp) {

		if (childExpList == null) {
			childExpList = new ArrayList<AppException>();
		}
		childExpList.add(childExp);

		// 将给定异常的消息追加到已有消息中。
		if (childExpList.size() == 1) {
			childMsg = childExp.getExpMsg();
		} else {
			childMsg = childExp.getExpMsg() + " <- " + childMsg;
		}

	}

	public String getExpCode() {
		return expCode;
	}

	public String getExpMsg() {
		return expMsg;
	}

	public String getChildMsg() {
		return childMsg;
	}

	public void setChildMsg(String childMsg) {
		this.childMsg = childMsg;
	}

	public List<AppException> getChildExpList() {
		return childExpList;
	}

	public String getRespTimestamp() {
		return respTimestamp;
	}

	public void setRespTimestamp(String respTimestamp) {
		this.respTimestamp = respTimestamp;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddress(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getExpStackTrace() {
		return expStackTrace;
	}

	public void setExpStackTrace(String expStackTrace) {
		this.expStackTrace = expStackTrace;
	}

}
