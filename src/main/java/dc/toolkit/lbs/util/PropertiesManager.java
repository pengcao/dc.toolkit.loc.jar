package dc.toolkit.lbs.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import dc.toolkit.lbs.exception.SvcMsgCode;



public class PropertiesManager {

	private static final Logger logger = Logger.getLogger(PropertiesManager.class);

	/**
	 * 采用单例
	 */
	private static PropertiesManager instances = new PropertiesManager();

	private PropertiesManager() {

	}

	public static PropertiesManager getInistances() {
		return instances;
	}

	/**
	 * 读取配置文件.properties中配置的参数
	 * 
	 * @param propertiesName
	 *            配置文件的名称
	 * @param nodeName
	 *            配置文件中参数的名称
	 * @return .properties中配置的参数
	 * @throws IOException
	 */
	public String getProperty(String propertiesName, String nodeName){
		String value = null;
		try {
			Properties properties = new Properties();
			
			InputStream inputStream = new FileInputStream(propertiesName);
			
			properties.load(inputStream);
			value = properties.getProperty(nodeName).trim();
			logger.debug("--------------------" + value);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			System.out.println("code:"+SvcMsgCode._300202+",配置文件读取异常");
			logger.error(SvcMsgCode._300202, e);
		}
		return value;
	}

	/**
	 * 测试
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		
	}

}