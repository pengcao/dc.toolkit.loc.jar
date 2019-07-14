/**
 * Description: 地址对象
 * Author: caopeng
 * Creation time: 2017年3月13日 下午2:56:46
 * (C) Copyright 2013-2016, deamoncao.
 * All rights reserved.
 */
package dc.toolkit.lbs.base.entity;
import org.apache.log4j.Logger;

public class LbsPointVo {

    private static final Logger logger = Logger.getLogger(LbsPointVo.class);
   
    private String nmCn = null; // 地址名,根据省市区县拼接而成的地址    
    private String ctryCd = null;//国家编码    
    private String ctryNmCn = null;//国家中文名   
    private String stCd = null;//省(州/自治区/直辖市)编码    
    private String stNmCn = null;   //省(州/自治区/直辖市)中文名
    private String ctyCd = null; // 城市编码   
    private String ctyNmCn = null; // 城市中文名
    private String distCd = null;   // 区县编码
    private String distNmCn = null; // 区县中文名
    private String townCd = null;   //乡镇编码
    private String townNmCn = null; //乡镇中文名
    private String strCd = null;   //门牌号
    private String strNmCn = null; // 街道中文名
    private String addrLine1 = null; //API得到返回的地址
    private String addrLine2 = null;
    private Double lon = null; // 经度
    private Double lat = null; // 纬度
    private Double amapLon = null; // 高德经度
    private Double amaplat = null; // 高德纬度
    private Double bdLon = null;   // 百度经度
    private Double bdlat = null;   // 百度纬度
    private String busAddrLine = null; // 被解析的地址
//    private String 
    
	public LbsPointVo() {
		super();
		// TODO Auto-generated constructor stub
	}


	public LbsPointVo(String busAddrLine) {
		super();
		this.busAddrLine = busAddrLine;
	}


	public LbsPointVo(Double lon, Double lat, String busAddrLine) {
		super();
		this.lon = lon;
		this.lat = lat;
		this.busAddrLine = busAddrLine;
	}


	public LbsPointVo(String nmCn, String ctryCd, String ctryNmCn, String stCd, String stNmCn, String ctyCd,
			String ctyNmCn, String distCd, String distNmCn, String townCd, String townNmCn, String strCd,
			String strNmCn, String addrLine1, String addrLine2, Double lon, Double lat, Double amapLon, Double amaplat,
			Double bdLon, Double bdlat, String busAddrLine) {
		super();
		this.nmCn = nmCn;
		this.ctryCd = ctryCd;
		this.ctryNmCn = ctryNmCn;
		this.stCd = stCd;
		this.stNmCn = stNmCn;
		this.ctyCd = ctyCd;
		this.ctyNmCn = ctyNmCn;
		this.distCd = distCd;
		this.distNmCn = distNmCn;
		this.townCd = townCd;
		this.townNmCn = townNmCn;
		this.strCd = strCd;
		this.strNmCn = strNmCn;
		this.addrLine1 = addrLine1;
		this.addrLine2 = addrLine2;
		this.lon = lon;
		this.lat = lat;
		this.amapLon = amapLon;
		this.amaplat = amaplat;
		this.bdLon = bdLon;
		this.bdlat = bdlat;
		this.busAddrLine = busAddrLine;
	}

	public String getNmCn() {
		return nmCn;
	}

	public void setNmCn(String nmCn) {
		this.nmCn = nmCn;
	}

	public String getCtryCd() {
		return ctryCd;
	}

	public void setCtryCd(String ctryCd) {
		this.ctryCd = ctryCd;
	}

	public String getCtryNmCn() {
		return ctryNmCn;
	}

	public void setCtryNmCn(String ctryNmCn) {
		this.ctryNmCn = ctryNmCn;
	}

	public String getStCd() {
		return stCd;
	}

	public void setStCd(String stCd) {
		this.stCd = stCd;
	}

	public String getStNmCn() {
		return stNmCn;
	}

	public void setStNmCn(String stNmCn) {
		this.stNmCn = stNmCn;
	}

	public String getCtyCd() {
		return ctyCd;
	}

	public void setCtyCd(String ctyCd) {
		this.ctyCd = ctyCd;
	}

	public String getCtyNmCn() {
		return ctyNmCn;
	}

	public void setCtyNmCn(String ctyNmCn) {
		this.ctyNmCn = ctyNmCn;
	}

	public String getDistCd() {
		return distCd;
	}

	public void setDistCd(String distCd) {
		this.distCd = distCd;
	}

	public String getDistNmCn() {
		return distNmCn;
	}

	public void setDistNmCn(String distNmCn) {
		this.distNmCn = distNmCn;
	}

	public String getTownCd() {
		return townCd;
	}

	public void setTownCd(String townCd) {
		this.townCd = townCd;
	}

	public String getTownNmCn() {
		return townNmCn;
	}

	public void setTownNmCn(String townNmCn) {
		this.townNmCn = townNmCn;
	}

	public String getStrCd() {
		return strCd;
	}

	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}

	public String getStrNmCn() {
		return strNmCn;
	}

	public void setStrNmCn(String strNmCn) {
		this.strNmCn = strNmCn;
	}

	public String getAddrLine1() {
		return addrLine1;
	}

	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	public String getAddrLine2() {
		return addrLine2;
	}

	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getAmapLon() {
		return amapLon;
	}

	public void setAmapLon(Double amapLon) {
		this.amapLon = amapLon;
	}

	public Double getAmaplat() {
		return amaplat;
	}

	public void setAmaplat(Double amaplat) {
		this.amaplat = amaplat;
	}

	public Double getBdLon() {
		return bdLon;
	}

	public void setBdLon(Double bdLon) {
		this.bdLon = bdLon;
	}

	public Double getBdlat() {
		return bdlat;
	}

	public void setBdlat(Double bdlat) {
		this.bdlat = bdlat;
	}


	public String getBusAddrLine() {
		return busAddrLine;
	}

	public void setBusAddrLine(String busAddrLine) {
		this.busAddrLine = busAddrLine;
	}


	@Override
	public String toString() {
		return "LbsPointVo [nmCn=" + nmCn + ", ctryCd=" + ctryCd + ", ctryNmCn=" + ctryNmCn + ", stCd=" + stCd
				+ ", stNmCn=" + stNmCn + ", ctyCd=" + ctyCd + ", ctyNmCn=" + ctyNmCn + ", distCd=" + distCd
				+ ", distNmCn=" + distNmCn + ", townCd=" + townCd + ", townNmCn=" + townNmCn + ", strCd=" + strCd
				+ ", strNmCn=" + strNmCn + ", addrLine1=" + addrLine1 + ", addrLine2=" + addrLine2 + ", lon=" + lon
				+ ", lat=" + lat + ", amapLon=" + amapLon + ", amaplat=" + amaplat + ", bdLon=" + bdLon + ", bdlat="
				+ bdlat + ", busAddrLine=" + busAddrLine + "]";
	}	
}
