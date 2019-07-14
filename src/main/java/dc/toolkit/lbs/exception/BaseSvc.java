
package dc.toolkit.lbs.exception;

import org.apache.log4j.Logger;

import dc.toolkit.lbs.i18n.ResourceReader;


public class BaseSvc {
	
	/**
	 * Description: 在仅提供错误信息的情况下处理异常
	 * Author: caopeng
	 * Creation time: 2015年11月4日 下午1:53:09
	 *
	 * @param expMsg
	 * @param t
	 * @param logger
	 * @throws AppException
	 */
	public static void throwException(AppException ae, Logger logger) throws AppException {
		logger.error(ae.getMessage(), ae);
		throw ae;
	}

	public void logError(String msgCode, String[] msgParams, Logger logger) {
		logger.error(ResourceReader.getMsg(msgCode, msgParams));
	}

	public void logMsg(String msgCode, String[] msgParams, Logger logger) {
		logger.info(ResourceReader.getMsg(msgCode, msgParams));
	}

}
