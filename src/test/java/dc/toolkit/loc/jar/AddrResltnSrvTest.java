/**
 * Description: 
 * Author: caopeng
 * Creation time: 2017年3月14日 下午10:11:21
 * (C) Copyright 2013-2016, deamoncao.
 * All rights reserved.
 */
package dc.toolkit.loc.jar;

import dc.toolkit.lbs.base.entity.LbsPointDtoX;
import dc.toolkit.lbs.exception.AppException;
import dc.toolkit.lbs.service.AddrResltnSrv;

public class AddrResltnSrvTest {

	public static void main(String[] args) throws AppException {
		// TODO Auto-generated method stub
		
		AddrResltnSrv addrResltnSrv = new AddrResltnSrv();
		LbsPointDtoX LbsPointDtoX = addrResltnSrv.reslvOneAddr("湖北省仙桃市汉江路109号");
//		LbsPointDtoX LbsPointDtoX = addrResltnSrv.reslvOneAddr("深圳市南山区腾讯大厦");
		System.out.println(LbsPointDtoX.getBdLbsPointVo().toString());
		System.out.println(LbsPointDtoX.getAmapLbsPointVo().toString());
	}

}
