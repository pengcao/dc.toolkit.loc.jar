/**
 * Description: 通过调用地图服务商的公共API来对地址进行解析
 * Author: caopeng
 * Creation time: 2017年3月13日 下午5:21:46
 * (C) Copyright 2013-2016, deamoncao.
 * All rights reserved.
 */
package dc.toolkit.lbs.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import dc.toolkit.lbs.base.entity.LbsPointDtoX;
import dc.toolkit.lbs.base.entity.LbsPointVo;
import dc.toolkit.lbs.base.ex.AMapAddrResltnImpl;
import dc.toolkit.lbs.base.ex.BMapAddrResltnImpl;
import dc.toolkit.lbs.exception.AppException;
import dc.toolkit.lbs.key.MapManager;
import dc.toolkit.lbs.util.EarthDistance;

public class AddrResltnSrv {
	
	private static final Logger logger = Logger.getLogger(AddrResltnSrv.class);
	private static double DEF_SUB_DIS = 2.0;
	private MapManager mapManager;
	
	
	public AddrResltnSrv() {
		super();
		// TODO Auto-generated constructor stub
		mapManager = MapManager.getInstance();
	}
	
	public AddrResltnSrv(double defSubDis) {
		super();
		// TODO Auto-generated constructor stub
		mapManager = MapManager.getInstance();
		DEF_SUB_DIS = defSubDis;
	}
	
	/**
	 * 
	 * Description: 调用地图服务厂商的API对对个地址进行解析(默认高德和百度解析出来的地址之间的距离小于200米)，使用默认系统自带的百度和高德的key
	 * Author: caopeng
	 * Creation time: 2017年3月13日 下午6:28:29
	 *
	 * @param addrNmList
	 * @return
	 * @throws AppException
	 */
	public List<LbsPointDtoX> reslvMulitAddr(List<String> addrNmList) throws AppException{
		if(addrNmList!=null){
			return null;
		}else{
			List<LbsPointDtoX> lbsPointDtoxList = new ArrayList<>();
			List<String> amapKeyList = mapManager.getAmapKeyList();
			List<String> bdKeyList = mapManager.getBdKeyList();
			int addrSize = addrNmList.size();
			for(int ii = 0 ; ii < addrSize ; ii ++){
				lbsPointDtoxList.add(inlineReslvOneAddr(addrNmList.get(ii), amapKeyList, bdKeyList));
			}
			return lbsPointDtoxList;
		}
	}
	
	/**
	 * 
	 * Description: 调用地图服务厂商的API对对个地址进行解析(默认高德和百度解析出来的地址之间的距离小于200米),用户自定义百度和高德的key
	 * Author: caopeng
	 * Creation time: 2017年3月16日 上午9:53:22
	 *
	 * @param addrNmList 地址
	 * @param amapKeyList 高德key
	 * @param bdKeyList 百度key
	 * @return
	 * @throws AppException
	 */
	public List<LbsPointDtoX> reslvMulitAddr(List<String> addrNmList,List<String> amapKeyList,List<String> bdKeyList) throws AppException{
		if(addrNmList!=null){
			return null;
		}else{
			List<LbsPointDtoX> lbsPointDtoxList = new ArrayList<>();
			int addrSize = addrNmList.size();
			for(int ii = 0 ; ii < addrSize ; ii ++){
				lbsPointDtoxList.add(inlineReslvOneAddr(addrNmList.get(ii), amapKeyList, bdKeyList));
			}
			return lbsPointDtoxList;
		}
	}

	/**
	 * 
	 * Description: 调用地图服务厂商的API对对个地址进行解析,用户自定义百度和高德的key
	 * Author: caopeng
	 * Creation time: 2017年3月16日 上午9:56:27
	 *
	 * @param addrNmList 地址
	 * @param amapKeyList 高德key
	 * @param bdKeyList 百度key
	 * @param subDis 同一个地址在两坐标系之间的距离
	 * @return
	 * @throws AppException
	 */
	public List<LbsPointDtoX> reslvMulitAddr(List<String> addrNmList,List<String> amapKeyList,List<String> bdKeyList,double subDis) throws AppException{
		if(addrNmList!=null){
			return null;
		}else{
			List<LbsPointDtoX> lbsPointDtoxList = new ArrayList<>();
			int addrSize = addrNmList.size();
			for(int ii = 0 ; ii < addrSize ; ii ++){
				lbsPointDtoxList.add(inlineReslvOneAddr(addrNmList.get(ii), amapKeyList, bdKeyList, subDis));
			}
			return lbsPointDtoxList;
		}
	}
	
	/**
	 * 
	 * Description: 
	 * Author: caopeng
	 * Creation time: 2017年3月16日 上午10:01:58
	 *
	 * @param addrNmList 地址
	 * @param subDis 同一个地址在两坐标系之间的距离
	 * @return
	 * @throws AppException
	 */
	public List<LbsPointDtoX> reslvMulitAddr(List<String> addrNmList,double subDis) throws AppException{
		if(addrNmList!=null){
			return null;
		}else{
			List<LbsPointDtoX> lbsPointDtoxList = new ArrayList<>();
			List<String> amapKeyList = mapManager.getAmapKeyList();
			List<String> bdKeyList = mapManager.getBdKeyList();
			int addrSize = addrNmList.size();
			for(int ii = 0 ; ii < addrSize ; ii ++){
				lbsPointDtoxList.add(inlineReslvOneAddr(addrNmList.get(ii), amapKeyList, bdKeyList, subDis));
			}
			return lbsPointDtoxList;
		}
	}
	
	/**
	 * 
	 * Description: 调用地图服务厂商的API对单个地址进行解析(默认高德和百度解析出来的地址之间的距离小于200米)，使用默认系统自带的百度和高德的key
	 * Author: caopeng
	 * Creation time: 2017年3月13日 下午6:23:19
	 *
	 * @param addrNmCn 地址
	 * @return
	 * @throws AppException
	 */
	public LbsPointDtoX reslvOneAddr(String addrNmCn) throws AppException{
		List<String> amapKeyList = mapManager.getAmapKeyList();
		List<String> bdKeyList = mapManager.getBdKeyList();
		return inlineReslvOneAddr(addrNmCn, amapKeyList, bdKeyList);	
	}
	
	
	
	/**
	 * 
	 * Description: 调用地图服务厂商的API对单个地址进行解析(默认高德和百度解析出来的地址之间的距离小于200米)，使用用户自定义的百度和高德的key
	 * Author: caopeng
	 * Creation time: 2017年3月16日 上午10:05:16
	 *
	 * @param addrNmList 地址
	 * @param amapKeyList 高德key
	 * @param bdKeyList 百度key
	 * @return
	 * @throws AppException
	 */
	public LbsPointDtoX reslvOneAddr(String addrNmCn,List<String> amapKeyList,List<String> bdKeyList) throws AppException{
		return inlineReslvOneAddr(addrNmCn, amapKeyList, bdKeyList);	
	}
	
	/**
	 * 
	 * Description: 调用地图服务厂商的API对单个地址进行解析
	 * Author: caopeng
	 * Creation time: 2017年3月16日 上午10:07:01
	 *
	 * @param addrNmCn 地址
	 * @param subDis 同一个地址在两坐标系之间的距离
	 * @return
	 * @throws AppException
	 */
	public LbsPointDtoX reslvOneAddr(String addrNmCn,double subDis) throws AppException{
		
		List<String> amapKeyList = mapManager.getAmapKeyList();
		List<String> bdKeyList = mapManager.getBdKeyList();
		return inlineReslvOneAddr(addrNmCn, amapKeyList, bdKeyList, subDis);	
	}
	
	/**
	 * 
	 * Description: 调用地图服务厂商的API对单个地址进行解析
	 * Author: caopeng
	 * Creation time: 2017年3月16日 上午10:06:23
	 *
	 * @param addrNmList 地址
	 * @param amapKeyList 高德key
	 * @param bdKeyList 百度key
	 * @param subDis 同一个地址在两坐标系之间的距离
	 * @return
	 * @throws AppException
	 */
	public LbsPointDtoX reslvOneAddr(String addrNmCn,List<String> amapKeyList,List<String> bdKeyList,double subDis) throws AppException{
		
		return inlineReslvOneAddr(addrNmCn, amapKeyList, bdKeyList, subDis);	
	}
	
	
	/**
	 * 
	 * Description: 调用高德和百度的API对地址进行解析
	 * Author: caopeng
	 * Creation time: 2017年3月13日 下午6:20:11
	 *
	 * @param addrNmCn
	 * @param amapKeyList
	 * @param bdKeyList
	 * @return
	 * @throws AppException
	 */
	private LbsPointDtoX inlineReslvOneAddr(String addrNmCn,List<String> amapKeyList,List<String> bdKeyList) throws AppException{
		
		LbsPointDtoX lbsPointDtoX = new LbsPointDtoX();
		if(addrNmCn!=null){
			int addrLen = addrNmCn.length();
			if(addrLen>=7){
				int amapKeySize = amapKeyList.size();
				int bdKeySize = bdKeyList.size();
				long currMilis = System.currentTimeMillis();
				int amapIndex = (int) (currMilis%amapKeySize);
				int bdIndex = (int) (currMilis%bdKeySize);
				lbsPointDtoX = inlineReslvOneAddr(addrNmCn, bdKeyList.get(bdIndex), amapKeyList.get(amapIndex), DEF_SUB_DIS);
			}else{
				lbsPointDtoX.setIsRslv(false);
			}
		}else{
			lbsPointDtoX.setIsRslv(false);
		}
		
		return lbsPointDtoX;		
	}
	
	/**
	 * 
	 * Description: 调用高德和百度的API对地址进行解析，并且对比两个服务厂商返回的地址之间的距离currSubDis，
	 *              当currSubDis<=subDis时，解析得到的地址为有效地址;
	 *              否则，为无效地址
	 * Author: caopeng
	 * Creation time: 2017年3月16日 上午9:48:59
	 *
	 * @param addrNmCn
	 * @param amapKeyList
	 * @param bdKeyList
	 * @param subDis
	 * @return
	 * @throws AppException
	 */
	private LbsPointDtoX inlineReslvOneAddr(String addrNmCn,List<String> amapKeyList,List<String> bdKeyList,double subDis) throws AppException{		
		LbsPointDtoX lbsPointDtoX = new LbsPointDtoX();
		if(addrNmCn!=null){
			int addrLen = addrNmCn.length();
			if(addrLen<7){
				int amapKeySize = amapKeyList.size();
				int bdKeySize = bdKeyList.size();
				long currMilis = System.currentTimeMillis();
				int amapIndex = (int) (currMilis%amapKeySize);
				int bdIndex = (int) (currMilis%bdKeySize);
				lbsPointDtoX = inlineReslvOneAddr(addrNmCn, bdKeyList.get(bdIndex), amapKeyList.get(amapIndex), subDis);
			}else{
				lbsPointDtoX.setIsRslv(false);
			}
		}else{
			lbsPointDtoX.setIsRslv(false);
		}
		
		return lbsPointDtoX;		
	}
	
	/**
	 * 
	 * Description: 调用高德和百度的API对地址进行解析，并且对比两个服务厂商返回的地址之间的距离currSubDis，
	 *              当currSubDis<=subDis时，解析得到的地址为有效地址;
	 *              否则，为无效地址
	 * Author: caopeng
	 * Creation time: 2017年3月13日 下午5:59:36
	 *
	 * @param addrNmCn
	 * @param bdKey
	 * @param amapKey
	 * @param subDis
	 * @return
	 * @throws AppException
	 */
	private LbsPointDtoX inlineReslvOneAddr(String addrNmCn,String bdKey,String amapKey,double subDis) throws AppException{
		
		LbsPointDtoX lbsPointDtoX = new LbsPointDtoX();
		LbsPointVo bdLbsPointVo = BMapAddrResltnImpl.getLbsFullInfoByAddr(addrNmCn, bdKey);
//		System.out.println("===bdLbsPointVo==  " + bdLbsPointVo.toString());
		LbsPointVo amapLbsPointVo = AMapAddrResltnImpl.getLbsFullInfoByAddr(addrNmCn, amapKey);
//		System.out.println("=== bdLbsPointVo : "+bdLbsPointVo.toString());
//		System.out.println("=== amapLbsPointVo : "+ amapLbsPointVo.toString());
//		System.out.println("===amapLbsPointVo==  " + amapLbsPointVo.toString());
		if(bdLbsPointVo!=null && amapLbsPointVo!=null){
			if(bdLbsPointVo.getLat()!=null && bdLbsPointVo.getLon()!=null && amapLbsPointVo.getLon()!=null
					&& amapLbsPointVo.getLat()!=null){
				double bdLat = bdLbsPointVo.getLat();
				double bdLng = bdLbsPointVo.getLon();
				double aLat = amapLbsPointVo.getLat();
				double aLng = amapLbsPointVo.getLon();
				double currSubDis = EarthDistance.getDiffBD2GCJCoorDistance(bdLat, bdLng, aLat, aLng);
				if(currSubDis<=subDis){
					
					bdLbsPointVo.setBusAddrLine(addrNmCn);
					amapLbsPointVo.setBusAddrLine(addrNmCn);
					lbsPointDtoX.setBdLbsPointVo(bdLbsPointVo);
					lbsPointDtoX.setAmapLbsPointVo(amapLbsPointVo);
					lbsPointDtoX.setIsRslv(true);
				}else{
					lbsPointDtoX.setIsRslv(false);
				}
			}else{
				lbsPointDtoX.setIsRslv(false);
			}
		}else{
			lbsPointDtoX.setIsRslv(false);
		}
		return lbsPointDtoX;
	}

}
