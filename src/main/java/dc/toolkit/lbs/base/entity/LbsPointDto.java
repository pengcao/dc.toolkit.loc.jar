/**
 * Description: 
 * Author: caopeng
 * Creation time: 2017年3月13日 下午3:14:48
 * (C) Copyright 2013-2016, deamoncao.
 * All rights reserved.
 */
package dc.toolkit.lbs.base.entity;

import org.apache.log4j.Logger;

public class LbsPointDto {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(LbsPointDto.class);
	
	protected LbsPointVo amapLbsPointVo = new LbsPointVo();
	protected LbsPointVo bdLbsPointVo = new LbsPointVo();
	protected Boolean isRslv;
	
	
	public LbsPointVo getAmapLbsPointVo() {
		return amapLbsPointVo;
	}
	public void setAmapLbsPointVo(LbsPointVo amapLbsPointVo) {
		this.amapLbsPointVo = amapLbsPointVo;
	}
	public LbsPointVo getBdLbsPointVo() {
		return bdLbsPointVo;
	}
	public void setBdLbsPointVo(LbsPointVo bdLbsPointVo) {
		this.bdLbsPointVo = bdLbsPointVo;
	}
	public Boolean getIsRslv() {
		return isRslv;
	}
	public void setIsRslv(Boolean isRslv) {
		this.isRslv = isRslv;
	}
}
