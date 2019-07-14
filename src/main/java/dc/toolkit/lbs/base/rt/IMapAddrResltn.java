/**
 * Description: 
 * Author: caopeng
 * Creation time: 2016年4月6日 上午10:12:17
 * (C) Copyright 2013-2016, deamoncao.
 * All rights reserved.
 */
package dc.toolkit.lbs.base.rt;

import net.sf.json.JSONObject;

public interface IMapAddrResltn {

	/**
	 * 
	 * Description: 根据地址获取地理位置的详细信息(包含地址信息验证,API正向解析,API逆向解析)
	 * Author: caopeng
	 * Creation time: 2016年4月6日 上午10:14:59
	 *
	 * @param address
	 * @param apiKey
	 * @param defaultProvince
	 * @return
	 */
	public JSONObject getLbsInfoByAddr(String address, String apiKey,String defaultProvince);
	
	
	/**
	 * 
	 * Description: 通过地址描述来获取地址的详细信息
	 * Author: caopeng
	 * Creation time: 2016年4月6日 上午10:16:23
	 *
	 * @param address
	 * @param apiKey
	 * @return
	 */
	public JSONObject getLbsFullInfoByAddr(String address, String apiKey);
	
	/**
	 * 
	 * Description: 经纬度信息逆向解析,通过经纬度来获取地址的详细信息
	 * Author: caopeng
	 * Creation time: 2016年4月6日 上午10:17:07
	 *
	 * @param lat
	 * @param lng
	 * @param apiKey
	 * @return
	 */
	public JSONObject getLbsInfoByLngLat(double lat,double lng,String apiKey);
	
	/**
	 * 
	 * Description: 
	 * Author: caopeng
	 * Creation time: 2016年4月6日 上午10:17:57
	 *
	 * @param address
	 * @param apiKey
	 * @return
	 */
	public JSONObject getLbsDetailInfoByAddr(String address, String apiKey);
	
}
