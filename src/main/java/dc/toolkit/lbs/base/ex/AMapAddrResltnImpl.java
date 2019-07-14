/**
 * Description: 调用高德地图API对地址进行解析
 * Author: caopeng
 * Creation time: 2016年3月30日 下午2:45:02
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
import dc.toolkit.lbs.util.DataTypeConvertion;
import dc.toolkit.lbs.util.HttpServerUtil;

import net.sf.json.JSONObject;

public class AMapAddrResltnImpl extends BaseSvc{

	private static final Logger logger = Logger.getLogger(AMapAddrResltnImpl.class);

//	public static final String GERO_URL = "http://restapi.amap.com/v3/geocode/geo";// 高德正向解析API
//	public static final String REGERO_URL = "http://restapi.amap.com/v3/geocode/regeo";// 高德逆向解析API

	/**
	 * 
	 * Description: 根据地址获取地理位置的详细信息(包含地址信息验证,API正向解析,API逆向解析) Author: caopeng
	 * Creation time: 2016年3月30日 下午5:55:35
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
	 * Description: 通过地址来获取地址的详细信息
	 * Author: caopeng
	 * Creation time: 2017年3月13日 下午5:18:29
	 *
	 * @param address
	 * @param apiKey
	 * @return
	 * @throws AppException
	 */
	public static LbsPointVo getLbsFullInfoByAddr(String address, String apiKey) throws AppException{
		LbsPointVo lbsPointVo = new LbsPointVo();
		LbsPointVo addrGeroObj = getLbsDetailInfoByAddr(address, apiKey);
		// System.out.println("*** addrGeroObj:"+addrGeroObj.toString());
		if (addrGeroObj != null ) {
			if(addrGeroObj.getLon()!=null){
				double lng = addrGeroObj.getLon();
				double lat = addrGeroObj.getLat();
				LbsPointVo lbsAddrGeroObj = getLbsInfoByLngLat(lat, lng, apiKey);
				// System.out.println("***
				// lbsAddrGeroObj:"+lbsAddrGeroObj.toString());
				if (lbsAddrGeroObj != null) {
					lbsPointVo = lbsAddrGeroObj;
				} else {
					lbsPointVo = null;
				}
			}
		} else {
			lbsPointVo = null;
		}
		return lbsPointVo;
	}
	

	/**
	 * 
	 * Description: 经纬度信息逆向解析
	 * Author: caopeng
	 * Creation time: 2017年3月13日 下午4:58:34
	 *
	 * @param lat
	 * @param lng
	 * @param apiKey
	 * @return
	 * @throws AppException
	 */
	public static LbsPointVo getLbsInfoByLngLat(double lat, double lng, String apiKey) throws AppException {

		if (lat > 90 || lat < -90 || lng > 180 || lng < -180) {
			return null;
		} else {
			LbsPointVo lbsPointVo = new LbsPointVo();
			String result = null;
			// String url = "http://restapi.amap.com/v3/geocode/regeo";

			StringBuffer locationBuff = new StringBuffer();
			locationBuff.append(lng);
			locationBuff.append(",");
			locationBuff.append(lat);
			Map<String, String> params = new HashMap<String, String>();
			params.put("location", locationBuff.toString());
			params.put("key", apiKey);
			try {//REGERO_URL
				result = HttpServerUtil.getResultByHttp(MapManager.getInstance().getAmapRegeoUrl(), params, "GET");
				// System.out.println("lbs result:"+result);
				if (result != null) {
					JSONObject jsonObject = JSONObject.fromObject(result);
					if (jsonObject != null) {
						JSONObject geroInfoObj = jsonObject.getJSONObject("regeocode");
						if (geroInfoObj != null) {

							if (geroInfoObj.getString("formatted_address") != null) {
								lbsPointVo.setAddrLine1(geroInfoObj.getString("formatted_address"));
							} else {
								lbsPointVo = null;
							}
							JSONObject addressComponentObj = geroInfoObj.getJSONObject("addressComponent");

							if (addressComponentObj != null) {
								StringBuffer addrNmCnBuf = new StringBuffer();
								String province = "";
								if(addressComponentObj.get("province")!=null){
									province = addressComponentObj.getString("province");
									lbsPointVo.setStNmCn(province);
									addrNmCnBuf.append(province);
								}
								
								if (addressComponentObj.get("city") != null) {
									
									String city = addressComponentObj.getString("city");
									lbsPointVo.setCtyNmCn(city);
									if(!city.equals(province) && !city.equals("[]")){
										addrNmCnBuf.append(city);
									}
								}
								
								if (addressComponentObj.get("district") != null) {
									String district = addressComponentObj.getString("district");
									lbsPointVo.setDistNmCn(district);
									addrNmCnBuf.append(district);
								}
																
								if (addressComponentObj.get("township") != null) {
									String township = addressComponentObj.getString("township");
									lbsPointVo.setTownNmCn(township);
									addrNmCnBuf.append(township);
								}

								if(addressComponentObj.getJSONObject("streetNumber")!=null){
									JSONObject streetObj = addressComponentObj.getJSONObject("streetNumber");
									if (streetObj != null) {								
										if (streetObj.get("street") != null) {
											String streetName = streetObj.getString("street");
											if(!streetName.equals("[]")){
												lbsPointVo.setStrNmCn(streetName);
												addrNmCnBuf.append(streetName);
											}
										}
										if (streetObj.get("number") != null) {
											String streetNumber = streetObj.getString("number");
											if(!streetNumber.equals("[]")){
												lbsPointVo.setStrCd(streetNumber);
												addrNmCnBuf.append(streetNumber);
											}

										}
									}
								}

								if(addrNmCnBuf!=null){
									lbsPointVo.setNmCn(addrNmCnBuf.toString());	
								}
								
								lbsPointVo.setLon(lng);
								lbsPointVo.setLat(lat);
							} else {
								lbsPointVo = null;
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
				throwException(new AppException(SvcMsgCode._200101, null, t), logger);
			} finally {
				return lbsPointVo;
			}
		}
	}

	/**
	 * 
	 * Description: 地址正向解析
	 * Author: caopeng
	 * Creation time: 2017年3月13日 下午5:05:39
	 *
	 * @param address
	 * @param apiKey
	 * @return
	 * @throws AppException
	 */
	public static LbsPointVo getLbsDetailInfoByAddr(String address, String apiKey) throws AppException{

		LbsPointVo lbsPointVo = new LbsPointVo();
		String result = null;
		// String url = "http://restapi.amap.com/v3/geocode/geo";

		Map<String, String> params = new HashMap<String, String>();
		params.put("address", address);
		params.put("key", apiKey);
		try {// GERO_URL
//			MapManager.getInstance().getAmapTruckRoutUrl();
			result = HttpServerUtil.getResultByHttp(MapManager.getInstance().getAmapGeoUrl(), params, "GET");
			if (result != null || result.length() < 6) {
				JSONObject jsonObject = JSONObject.fromObject(result);
				if (jsonObject != null) {
					net.sf.json.JSONArray geroInfo = jsonObject.getJSONArray("geocodes");
					int geroInfoSize = geroInfo.size();

					if (geroInfo != null && geroInfoSize > 0) {
						JSONObject geroJson = geroInfo.getJSONObject(0);
//						addressDetail = geroJson.getString("formatted_address");
//						province = geroJson.getString("province");
//						city = geroJson.getString("city");
//						district = geroJson.getString("district");
						if(geroJson.get("location")!=null){
							String location = geroJson.getString("location");
							int commaIndex = location.indexOf(",");
							String lngStr = location.substring(0, commaIndex);
							String latStr = location.substring(commaIndex + 1);
							lbsPointVo.setLon(DataTypeConvertion.convertStrToDouble(lngStr));
							lbsPointVo.setLat(DataTypeConvertion.convertStrToDouble(latStr));
						}else{
							lbsPointVo = null;
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
