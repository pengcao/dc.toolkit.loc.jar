/**
 * Description: 
 * Author: caopeng
 * Creation time: 2016年3月30日 下午5:45:40
 * (C) Copyright 2013-2016, deamoncao.
 * All rights reserved.
 */
package dc.toolkit.lbs.util;

public class AddrValidator {
	

	/**
	 * 
	 * Description: 验证地址是否
	 * Author: caopeng
	 * Creation time: 2016年3月30日 下午5:49:56
	 *
	 * @param addr
	 * @return
	 */
	public static boolean validAddr(String addr){
		if(addr!=null){
			if (addr.contains("省") || addr.contains("市") || addr.contains("区") || addr.contains("县")
					|| addr.contains("自治州")) {
				return true;
			}else{
				return false;
			}			
		}else{
			return false;
		}
	}
}
