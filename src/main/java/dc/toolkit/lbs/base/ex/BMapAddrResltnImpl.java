/**
 * Description: 调用百度地图API对地址进行解析
 * Author: caopeng
 * Creation time: 2016年3月30日 下午2:45:21
 * (C) Copyright 2013-2016, deamoncao.
 * All rights reserved.
 */
package dc.toolkit.lbs.base.ex;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import dc.toolkit.lbs.base.entity.LbsPointVo;
import dc.toolkit.lbs.exception.AppException;
import dc.toolkit.lbs.exception.BaseSvc;
import dc.toolkit.lbs.exception.SvcMsgCode;
import dc.toolkit.lbs.key.MapManager;
import dc.toolkit.lbs.util.AddrValidator;
import dc.toolkit.lbs.util.HttpServerUtil;

import net.sf.json.JSONObject;

public class BMapAddrResltnImpl extends BaseSvc{

	private static final Logger logger = Logger.getLogger(BMapAddrResltnImpl.class);
	
//	public static final String GERO_URL = "http://api.map.baidu.com/geocoder/v2/";// 百度正向解析API
//	public static final String REGERO_URL = "http://api.map.baidu.com/geocoder/v2/";// 百度逆向解析API

	/**
	 * 
	 * Description: 根据地址获取地理位置的详细信息(包含地址信息验证,API正向解析,API逆向解析) Author: caopeng
	 * Creation time: 2016年4月6日 上午10:14:59
	 *
	 * @param address
	 * @param apiKey
	 * @param defaultProvince
	 * @return
	 */
	public static LbsPointVo getLbsInfoByAddr(String address, String apiKey, String defaultProvince) throws AppException{

		String addrStandard = "";
		if (AddrValidator.validAddr(address)) {
			addrStandard = address;
		} else {
			addrStandard = defaultProvince + address;
		}
		return getLbsFullInfoByAddr(addrStandard, apiKey);
	}

	/**
	 * 
	 * Description: 通过地址描述来获取地址的详细信息 Author: caopeng Creation time: 2016年4月6日
	 * 上午10:16:23
	 *
	 * @param address
	 * @param apiKey
	 * @return
	 */
	public static LbsPointVo getLbsFullInfoByAddr(String address, String apiKey) throws AppException{

		LbsPointVo lbsPointVo = new LbsPointVo();
		try{
			LbsPointVo addrGeroObj = getLbsDetailInfoByAddr(address, apiKey);
			if (addrGeroObj != null) {
				if (addrGeroObj.getLat() != null || addrGeroObj.getLon() != null) {
	
					double lng = addrGeroObj.getLon();
					double lat = addrGeroObj.getLat();
	
					LbsPointVo lbsAddrGeroObj = getLbsInfoByLngLat(lat, lng, apiKey);
					if (lbsAddrGeroObj != null) {
						lbsPointVo = lbsAddrGeroObj;
					} else {
						lbsPointVo = null;
					}
				} else {
					lbsPointVo = null;
				}
			} else {
				lbsPointVo = null;
			}
		}catch (Throwable t) {
			throwException(new AppException(SvcMsgCode._200100, null, t),logger);
		}
		return lbsPointVo;
	}

	/**
	 * 
	 * Description:  经纬度信息逆向解析,通过经纬度来获取地址的详细信息
	 * Author: caopeng
	 * Creation time: 2017年3月13日 下午4:11:30
	 *
	 * @param lat
	 * @param lng
	 * @param apiKey
	 * @return
	 */
	public static LbsPointVo getLbsInfoByLngLat(double lat, double lng, String apiKey) throws AppException{
		if(lat>90 || lat <-90 || lng > 180 || lng < -180){
			return null;
		}else{
			LbsPointVo lbsPointVo = new LbsPointVo();
			String result = null;
			// String url = "http://api.map.baidu.com/geocoder";
			StringBuffer locStrBuf = new StringBuffer();
			locStrBuf.append(lat);
			locStrBuf.append(",");
			locStrBuf.append(lng);

			Map<String, String> params = new HashMap<String, String>();
			params.put("location", locStrBuf.toString());
			params.put("ak", apiKey);
			params.put("output", "json");

			try {// REGERO_URL
				result = HttpServerUtil.getResultByHttp(MapManager.getInstance().getBdRegeoUrl(), params, "GET");
				if (result != null) {
					JSONObject jsonObject = JSONObject.fromObject(result);
					if (jsonObject != null) {
						JSONObject jsonObjResult = JSONObject.fromObject(jsonObject.get("result"));
						if (jsonObjResult != null) {
							Object formattedAddressObj = jsonObjResult.get("formatted_address");
							if (formattedAddressObj != null) {
								lbsPointVo.setAddrLine1(jsonObjResult.get("formatted_address").toString());
							} 

							// 地址组件
							JSONObject jsonObjAddress = JSONObject.fromObject(jsonObjResult.get("addressComponent"));
							// 省市区县等信息
							StringBuffer addrNmCnBuf = new StringBuffer();
							String province = "";
							String city = "";
							String district = "";
							if (jsonObjAddress.get("province") != null) {
								province = jsonObjAddress.get("province").toString();
								lbsPointVo.setStNmCn(province);
								addrNmCnBuf.append(province);
							}

							if (jsonObjAddress.get("city") != null) {
								city = jsonObjAddress.get("city").toString();
								lbsPointVo.setCtyNmCn(city);
								if(!city.equals(province)){
									addrNmCnBuf.append(city);
								}
							} 

							if (jsonObjAddress.get("district") != null) {
								district = jsonObjAddress.get("district").toString();
								lbsPointVo.setDistNmCn(district);
								if(!district.equals(city)){
									addrNmCnBuf.append(district);
								}
							} 

							if (jsonObjAddress.get("street") != null) {
								lbsPointVo.setStrNmCn(jsonObjAddress.get("street").toString());
								addrNmCnBuf.append(jsonObjAddress.get("street").toString());
							}
							
							// street_number
							if (jsonObjAddress.get("street_number") != null) {
								lbsPointVo.setStrCd(jsonObjAddress.get("street_number").toString());
								addrNmCnBuf.append(jsonObjAddress.get("street_number").toString());
							}
							lbsPointVo.setLon(lng);
							lbsPointVo.setLat(lat);
							if(addrNmCnBuf!=null){
								lbsPointVo.setNmCn(addrNmCnBuf.toString());;
							}
							
						} else {
							lbsPointVo = null;
						}
					} else {
						lbsPointVo = null;
					}
				} else {
					lbsPointVo = null;
				}
			} catch (Throwable t) {
				throwException(new AppException(SvcMsgCode._200101, null, t),logger);
			} finally {
				return lbsPointVo;
			}
		}

	}

	/**
	 * 
	 * Description: 根据地址名获取经纬度信息
	 * Author: caopeng
	 * Creation time: 2017年3月13日 下午3:49:31
	 *
	 * @param address
	 * @param apiKey
	 * @return
	 */
	public static LbsPointVo getLbsDetailInfoByAddr(String address, String apiKey) throws AppException{

		LbsPointVo lbsPointVo = new LbsPointVo();
		String result = null;

		Map<String, String> params = new HashMap<String, String>();
		params.put("address", address);
		params.put("ak", apiKey);
		params.put("output", "json");

		if (address.length() < 1) {
			return null;
		}

		try {// GERO_URL
			result = HttpServerUtil.getResultByHttp(MapManager.getInstance().getBdGeoUrl(), params, "GET");
			if (result != null) {
				String resultStr = result.toString();
				if (resultStr.contains("{") && resultStr.contains("}")) {

					JSONObject jsonObject = JSONObject.fromObject(result);

					if (jsonObject != null) {
						JSONObject rsltJsonObj = jsonObject.getJSONObject("result");
						if (rsltJsonObj.isNullObject()) {
							lbsPointVo = null;
						} else {
							if (rsltJsonObj.get("location") != null) {
								JSONObject locJsonObj = rsltJsonObj.getJSONObject("location");
								if (locJsonObj.isNullObject()) {
									lbsPointVo = null;
								} else {
									lbsPointVo.setLon(locJsonObj.getDouble("lng"));
									lbsPointVo.setLat(locJsonObj.getDouble("lat"));
								}
							} else {
								lbsPointVo = null;
							}
						}

					} else {
						lbsPointVo = null;
					}
				} else {
					lbsPointVo = null;
				}
			} else {
				lbsPointVo = null;
			}
		} catch (Throwable t) {
			throwException(new AppException(SvcMsgCode._200102, null, t),logger);
		} finally {
			return lbsPointVo;
		}
	}
	
	
}
