/**
 * Description: 地球距离计算(曲面距离)
 * Author: caopeng
 * Creation time: 2016年3月30日 上午8:33:47
 * (C) Copyright 2013-2016, deamoncao.
 * All rights reserved.
 */
package dc.toolkit.lbs.util;

import net.sf.json.JSONObject;

public class EarthDistance {

	private static double EARTH_RADIUS = 6378.137;

	private static double getLocalRadius(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 求亮点之间的地球距离 Description: Author: caopeng Creation time: 2016年3月30日
	 * 上午8:35:10
	 *
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double getACoorDistance(double startLat, double startLng, double endLat, double endLng) {
		double radLat1 = getLocalRadius(startLat);
		double radLat2 = getLocalRadius(endLat);
		double a = radLat1 - radLat2;
		double b = getLocalRadius(startLng) - getLocalRadius(endLng);
		double distance = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		distance = distance * EARTH_RADIUS;
		distance = Math.round(distance * 10000.0) / 10000.0;
		return distance;
	}

	/**
	 * 
	 * Description: Author: caopeng Creation time: 2016年4月26日 下午2:42:38
	 *
	 * @param startLat
	 * @param startLng
	 * @param endLat
	 * @param endLng
	 * @return
	 */
	public static double getDiffBD2GCJCoorDistance(double bdLat, double bdLng, double aLat, double aLng) {

		// gcj2bd
		JSONObject gcjJson = CoorUtil.gcj2bd(aLat, aLng);
		double bdaLat = gcjJson.getDouble("lat");
		double bdaLng = gcjJson.getDouble("lng");
		double distance = getACoorDistance(bdLat, bdLng, bdaLat, bdaLng);
		return distance;
	}

}
