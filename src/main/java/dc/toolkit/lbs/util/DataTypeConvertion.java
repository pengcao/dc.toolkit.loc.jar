/**
 * Description: 数据类型之间的转换
 * Author: caopeng
 * Creation time: 2016年3月30日 下午2:55:46
 * (C) Copyright 2013-2016, deamoncao.
 * All rights reserved.
 */
package dc.toolkit.lbs.util;

public class DataTypeConvertion {

	/**
	 * 
	 * Description: 将String类型的数据转换成Double类型
	 * Author: caopeng
	 * Creation time: 2016年3月30日 下午2:56:06
	 *
	 * @param doubleStr
	 * @return
	 */
	public static double convertStrToDouble(String doubleStr){
		if(doubleStr!=null){
			return Double.valueOf(doubleStr);
		}else{
			return 0;
		}	
	}
}
