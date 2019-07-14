/**
 * Description: 数据精度计算
 * Author: caopeng
 * Creation time: 2016年4月6日 下午6:25:31
 * (C) Copyright 2013-2016, deamoncao.
 * All rights reserved.
 */
package dc.toolkit.lbs.util;

import java.math.BigDecimal;

/**
 * 数据精确度计算
 * 
 * @author caopeng
 *
 */
public class DtValPrcsnUtil {
	
	
	/**
	 * 
	 * Description: 确定double类型数据的小数点位数
	 * Author: caopeng Creation time: 2016年4月6日
	 * 下午7:14:45
	 *
	 * @param value
	 *            double类型的数据
	 * @param decimal
	 *            double类型数据的精确度
	 * @return
	 */
	public static double getDtValPrcsn(double value, int decimal) {

		BigDecimal bdcimlVal = new BigDecimal(value);
		return bdcimlVal.setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
