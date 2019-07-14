/**
 * Description: 
 * Author: caopeng
 * Creation time: 2016年4月26日 下午1:59:07
 * (C) Copyright 2013-2016, deamoncao.
 * All rights reserved.
 */
package dc.toolkit.app.lbs.map.tool;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import dc.toolkit.lbs.base.entity.LbsPointDtoX;
import dc.toolkit.lbs.base.entity.LbsPointVo;
import dc.toolkit.lbs.base.ex.AMapAddrResltnImpl;
import dc.toolkit.lbs.exception.AppException;
import dc.toolkit.lbs.service.AddrResltnSrv;

import net.sf.json.JSONObject;

public class AddrStandTool {
	
	private static final Logger logger = Logger.getLogger(AddrStandTool.class);

	public static JSONObject getStandAddr(String address) {

		JSONObject jsonObj = new JSONObject();
		AddrResltnSrv addrResltnSrv = new AddrResltnSrv();
		try{
			LbsPointDtoX lbsPointDtoX = addrResltnSrv.reslvOneAddr(address);
			if (lbsPointDtoX != null) {
				LbsPointVo  bdLbsPointVo = lbsPointDtoX.getBdLbsPointVo();
				LbsPointVo amapLbsPointVo = lbsPointDtoX.getAmapLbsPointVo();
				System.out.println("== bdLbsPointVo : " + bdLbsPointVo.toString());
				System.out.println("== amapLbsPointVo : " + amapLbsPointVo.toString());
				if (bdLbsPointVo != null && amapLbsPointVo!=null) {
					double aLat = amapLbsPointVo.getLat();
					double aLng = amapLbsPointVo.getLon();
					double bdLat = bdLbsPointVo.getLat();
					double dbLng = bdLbsPointVo.getLon();
					
					String bdAddrLine1 = bdLbsPointVo.getAddrLine1();
					String bdProvince = bdLbsPointVo.getStNmCn();
					String bdCity = bdLbsPointVo.getCtyNmCn();
					String bdDistrict = bdLbsPointVo.getDistNmCn();
					String bdStreetName = bdLbsPointVo.getStrNmCn();
					String bdStreetNumber = bdLbsPointVo.getStrCd();
					
					String aAddrLine1 = amapLbsPointVo.getAddrLine1();
					String aProvince = amapLbsPointVo.getStNmCn();
					String aCity = amapLbsPointVo.getCtyNmCn();
					String aDistrict = amapLbsPointVo.getDistNmCn();
					String aTownship = amapLbsPointVo.getTownNmCn();
					String aStreetName = amapLbsPointVo.getStrNmCn();
					String aStreetNumber = amapLbsPointVo.getStCd();
					
					jsonObj.put("bdAddrLine1", bdAddrLine1);
					jsonObj.put("bdProvince", bdProvince);
					jsonObj.put("bdCity", bdCity);
					jsonObj.put("bdDistrict", bdDistrict);
					jsonObj.put("bdStreetName", bdStreetName);
					jsonObj.put("bdStreetNumber", bdStreetNumber);
					jsonObj.put("bdLat", bdLat);
					jsonObj.put("dbLng", dbLng);				
	
					jsonObj.put("aAddrLine1", aAddrLine1);
					jsonObj.put("aProvince", aProvince);
					jsonObj.put("aCity", aCity);
					jsonObj.put("aDistrict", aDistrict);
					jsonObj.put("aTownship", aTownship);
					jsonObj.put("aStreetName", aStreetName);
					jsonObj.put("aStreetNumber", aStreetNumber);
					jsonObj.put("aLat", aLat);
					jsonObj.put("aLng", aLng);
					jsonObj.put("info", 1);			
				} else {
					jsonObj.put("info", 3);
				}
			} else {
				jsonObj.put("info", 4);
			}
		}catch(Exception e){
			jsonObj.put("info", 5);
			logger.info("解析" + address + "出错");
			e.printStackTrace();
		}
		return jsonObj;
	}

	public static void main(String[] args) throws AppException {

		String addr = "上海市红星世贸大厦B座";
		System.out.println("***  addr:" + addr);
		String amapKey = "7df40d8c4cf250dadf907245a02a5e9d";
		System.out.println(AMapAddrResltnImpl.getLbsFullInfoByAddr(addr, amapKey));

	}
}
