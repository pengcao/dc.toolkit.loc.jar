/**
 * Description: 地址坐标系之间的转换
 * 谷歌用的WGS84坐标系，但是中国这边国家测绘要求用GCJ-02加密一次,
 * 百度在GCJ-02要求加密的基础上，又自己对坐标加密了一次，它官方文档里说叫BD-09,
 * 从GPS坐标转到百度坐标有接口提供，反过来不提供
 * Author: caopeng
 * Creation time: 2016年4月26日 下午2:34:43
 * (C) Copyright 2013-2016, deamoncao.
 * All rights reserved.
 */
package dc.toolkit.lbs.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import dc.toolkit.lbs.base.entity.Point;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CoorUtil {
	
	private static double radius = 6378.137, x_pi = Math.PI * 3000.0 / 180.0,
			a = 6378245.0, ee = 0.00669342162296594323;

	private static double transformLat(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
				+ 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x
				* Math.PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0
				* Math.PI)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y
				* Math.PI / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	private static double transformLon(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
				* Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x
				* Math.PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * Math.PI) + 40.0 * Math.sin(x / 3.0
				* Math.PI)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * Math.PI) + 300.0 * Math.sin(x
				/ 30.0 * Math.PI)) * 2.0 / 3.0;
		return ret;
	}



	public static JSONObject delta(double lat, double lng) {
		JSONObject jsonObject = new JSONObject();
		double dLat = transformLat(lng - 105.0, lat - 35.0);
		double dLng = transformLon(lng - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * Math.PI;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0)
				/ ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
		dLng = (dLng * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);
		jsonObject.put("lat", dLat);
		jsonObject.put("lng", dLng);
		return jsonObject;
	}	
	
	/**
	 * 
	 * Description: 将google（除中国）坐标转换为高德坐标
	 * Author: caopeng
	 * Creation time: 2016年8月2日 上午12:08:18
	 *
	 * @param wgsLat
	 * @param wgsLng
	 * @return
	 */
	public static JSONObject wgs2gcj(double wgsLat, double wgsLng) {
		JSONObject jsonObject = new JSONObject();
		JSONObject d = delta(wgsLat, wgsLng);
		jsonObject.put("lat", wgsLat + d.getDouble("lat"));
		jsonObject.put("lng", wgsLng + d.getDouble("lng"));
		return jsonObject;
	}

	/**
	 * 
	 * Description: 将高德坐标转换为google(除中国)坐标
	 * Author: caopeng
	 * Creation time: 2016年8月2日 上午12:09:11
	 *
	 * @param gcjLat
	 * @param gcjLng
	 * @return
	 */
	public static JSONObject gcj2wgs(double gcjLat, double gcjLng) {
		JSONObject jsonObject = new JSONObject();
		JSONObject d = delta(gcjLat, gcjLng);

		jsonObject.put("lat", gcjLat - d.getDouble("lat"));
		jsonObject.put("lng", gcjLng - d.getDouble("lng"));
		return jsonObject;
	}

	/**
	 * 
	 * Description: 将高德坐标转换为百度坐标
	 * Author: caopeng
	 * Creation time: 2016年8月2日 上午12:10:18
	 *
	 * @param lat
	 * @param lng
	 * @return
	 */
	public static JSONObject gcj2bd(double lat, double lng) {
		JSONObject jsonObject = new JSONObject();
		double z = Math.sqrt(lng * lng + lat * lat) + 0.00002
				* Math.sin(lat * x_pi);
		double theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * x_pi);
		double bd_lon = z * Math.cos(theta) + 0.0065;
		double bd_lat = z * Math.sin(theta) + 0.006;
		jsonObject.put("lat", bd_lat);
		jsonObject.put("lng", bd_lon);
		return jsonObject;
	}

	/**
	 * 
	 * Description: 将百度坐标转换为高德坐标
	 * Author: caopeng
	 * Creation time: 2016年8月2日 上午12:10:40
	 *
	 * @param bd_lat
	 * @param bd_lon
	 * @return
	 */
	public static JSONObject bd2gcj(double bd_lat, double bd_lon) {
		JSONObject jsonObject = new JSONObject();
		double x = bd_lon - 0.0065, y = bd_lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		double gg_lon = z * Math.cos(theta);
		double gg_lat = z * Math.sin(theta);

		jsonObject.put("lat", gg_lat);
		jsonObject.put("lng", gg_lon);
		return jsonObject;
	}

	// 图吧坐标转换成wgs坐标
	public static JSONObject mapBar2WGS84(double sbar_lng, double sbar_lat) {
		JSONObject jsonObject = new JSONObject();
		
		float bar_lng = ((float)sbar_lng) * 100000 % 36000000;
		float bar_lat = (float)sbar_lat * 100000 % 36000000;
		int lng1 = (int) (-(((Math.cos(bar_lat / 100000)) * (bar_lng / 18000)) + ((Math.sin(bar_lng / 100000)) * (bar_lat / 9000))) + bar_lng);
		int lat1 = (int) (-(((Math.sin(bar_lat / 100000)) * (bar_lng / 18000)) + ((Math.cos(bar_lng / 100000)) * (bar_lat / 9000))) + bar_lat);
		int lng2 = (int) (-(((Math.cos(lat1 / 100000)) * (lng1 / 18000)) + ((Math.sin(lng1 / 100000)) * (lat1 / 9000))) + bar_lng + ((bar_lng > 0) ? 1: -1));
		int lat2 = (int) (-(((Math.sin(lat1 / 100000)) * (lng1 / 18000)) + ((Math.cos(lng1 / 100000)) * (lat1 / 9000))) + bar_lat + ((bar_lat > 0) ? 1: -1));
		double endLat=lat2/100000.0;
		double endLng=lng2/100000.0;
		jsonObject.put("lat", endLat);
		jsonObject.put("lng", endLng);
		return jsonObject;
	}

	// 图吧坐标转换成gcj坐标
	public static JSONObject mapBar2gcj(double bar_lng, double bar_lat) {
		JSONObject wgs = mapBar2WGS84(bar_lng, bar_lat);
		return wgs2gcj(wgs.getDouble("lat"), wgs.getDouble("lng"));
	}

	// 图吧坐标转换成gcj坐标
	public static JSONObject mapBar2bd(double bar_lng, double bar_lat) {
		JSONObject wgs = mapBar2WGS84(bar_lng, bar_lat);
		return wgs2bd(wgs.getDouble("lat"), wgs.getDouble("lng"));
	}

	// GPS(wgs-84) -> 百度（bd-09）
	// 单个转换 -- google坐标系转换为百度坐标系
	public static JSONObject wgs2bd(double wgsLat, double wgsLng) {
		JSONObject gcj = wgs2gcj(wgsLat, wgsLng);
		return gcj2bd(gcj.getDouble("lat"), gcj.getDouble("lng"));
	}

	// 百度（bd-09）-> GPS(wgs-84)
	// 单个转换 -- 百度坐标系转换为google坐标系
	public static JSONObject bd2wgs(double lat, double lng) {
		JSONObject gcj = bd2gcj(lat, lng);
		return gcj2wgs(gcj.getDouble("lat"), gcj.getDouble("lng"));
	}

	// GPS(wgs-84) -> 百度（bd-09）
	//批量装换  -- Google坐标系转换为百度坐标系
	public static JSONArray wgs2bds(List<Point> points) {
		JSONArray ret = new JSONArray();
		for (Point point : points) {
			ret.add(wgs2bd(point.getY(), point.getX()));
		}
		return ret;
	}

	// 百度（bd-09）-> GPS(wgs-84)
	// 批量装换  -- 百度坐标秀转换为google坐标系
	public static JSONArray bd2wgss(List<Point> points) {
		JSONArray ret = new JSONArray();
		for (Point point : points) {
			ret.add(bd2wgs(point.getY(), point.getX()));
		}
		return ret;
	}

	public static JSONObject gcj2wgs_exact(Double gcjLat, Double gcjLng) {
		JSONObject jsonObject = new JSONObject();
		double initDelta = 0.01;
		double threshold = 0.000001;
		double dLat = initDelta, dLng = initDelta;
		double mLat = gcjLat - dLat, mLng = gcjLng - dLng;
		double pLat = gcjLat + dLat, pLng = gcjLng + dLng;
		double wgsLat = 0, wgsLng = 0;
		for (int i = 0; i < 30; i++) {
			wgsLat = (mLat + pLat) / 2;
			wgsLng = (mLng + pLng) / 2;
			JSONObject tmp = wgs2gcj(wgsLat, wgsLng);
			dLat = tmp.getDouble("lat") - gcjLat;
			dLng = tmp.getDouble("lng") - gcjLng;
			if ((Math.abs(dLat) < threshold) && (Math.abs(dLng) < threshold)) {
				jsonObject.put("lat", wgsLat);
				jsonObject.put("lng", wgsLng);
				return jsonObject;
			}
			if (dLat > 0) {
				pLat = wgsLat;
			} else {
				mLat = wgsLat;
			}
			if (dLng > 0) {
				pLng = wgsLng;
			} else {
				mLng = wgsLng;
			}
		}
		jsonObject.put("lat", wgsLat);
		jsonObject.put("lng", wgsLng);
		return jsonObject;
	}

	public static String getNDayFromTodayDate(String specifiedDay, int Siwtchday) {
		Calendar cal = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		cal.setTime(date);
		int day = cal.get(Calendar.DATE);
		cal.set(Calendar.DATE, day + Siwtchday);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime());
		return dayBefore + " 00:00:00.0";
	}

	private static final double EARTH_RADIUS = 6378.137;

	/**
	 * 根据地球上任意两点的经纬度计算两点间的距离 C = sin(MLatA)*sin(MLatB)*cos(MLonA-MLonB) +
	 * cos(MLatA)*cos(MLatB) Distance = R*Arccos(C)*Pi/180
	 * 
	 * @param longt1
	 * @param lat1
	 * @param longt2
	 * @param lat2
	 * @return
	 */
	public static double getDistance(double longt1, double lat1, double longt2,
			double lat2) {
		double x, y, distance;
		x = (longt2 - longt1) * Math.PI * EARTH_RADIUS * 1000
				* Math.cos(((lat1 + lat2) / 2) * Math.PI / 180) / 180;
		y = (lat2 - lat1) * Math.PI * EARTH_RADIUS * 1000 / 180;
		distance = Math.hypot(x, y);
		return distance;
	}

	public static HashMap<String, Object> getSmallCircle(List<Point> plist) {
		HashMap<String, Object> circleHashMap = new HashMap<String, Object>();
		Point RecleP = null;
		if (plist.size() >= 3) {
			Point maxPoint = plist.get(0), minPoint = plist.get(0);
			for (Point point : plist) {
				double x = point.getX(), y = point.getY();
				double directXY = getXY(x, y);
				if (directXY > getXY(maxPoint.getX(), maxPoint.getY()))
					maxPoint = point;
				if (directXY < getXY(minPoint.getX(), minPoint.getY()))
					minPoint = point;
			}
			if (maxPoint.equals(minPoint)) {
				System.out.println("无法构成最小圆");
				return null;
			}
			Point middleXY = new Point(
					((maxPoint.getX() + minPoint.getX()) / 2d),
					((maxPoint.getY() + minPoint.getY()) / 2d));
			Point maxXY = null;
			double mRange = -1;
			double AB = Range(minPoint.getX(), minPoint.getY(),
					maxPoint.getX(), maxPoint.getY());
			double a = AB, b = 0, c = 0;
			for (Point point : plist) {
				double RangeA = Range(minPoint.getX(), minPoint.getY(),
						point.getX(), point.getY());
				double RangeB = Range(maxPoint.getX(), maxPoint.getY(),
						point.getX(), point.getY());
				double ABRange = RangeA + RangeB;
				if (maxXY == null || ABRange > mRange) {
					maxXY = point;
					mRange = ABRange;
					b = RangeA;
					c = RangeB;
				}
			}// 先求出最大值，然后根据最大角判断
				// 计算最大边长
			double temp = 0;
			if (c > a && c > b) {
				temp = c;
				c = a;
				a = temp;
				middleXY = new Point(((maxPoint.getX() + maxXY.getX()) / 2d),
						((maxPoint.getY() + maxXY.getY()) / 2d));
			} else if (b > a && b > c) {
				temp = b;
				b = a;
				a = temp;
				middleXY = new Point(((minPoint.getX() + maxXY.getX()) / 2d),
						((minPoint.getY() + maxXY.getY()) / 2d));
			}

			double R = 0d;
			if (b + c > a) {
				// 说明圆心不是他们的中点,但这三点必定在最小圆上
				// 运用3点求圆公式
				RecleP = getABInEquation(maxPoint, minPoint, maxXY);
				R = getDistance(maxPoint.getX(), maxPoint.getY(),
						RecleP.getX(), RecleP.getY());
			} else {
				// 说明最小圆心是他们的中点
				R = Math.sqrt(a / 4);
				RecleP = middleXY;
			}

			System.out.println("圆心: (" + RecleP.getX() + "," + RecleP.getY()
					+ ")");
			System.out.println("半径: " + R);
			circleHashMap.put("centerPoint", RecleP);

			circleHashMap.put("circleR", R);
		}
		return circleHashMap;
	}

	// 两点之间的距离的平方
	public static double Range(double x1, double y1, double x2, double y2) {
		double dis = getDistance(x1, y1, x2, y2);
		return dis * dis;
	}

	// 获取x^2+y^2的和
	public static double getXY(double x, double y) {
		return x * x + y * y;
	}

	// 根据3点求出圆心
	public static Point getABInEquation(Point p1, Point p2, Point p3) {
		double a = (-2d) * (p1.getX() - p2.getX()), b = (-2d)
				* (p1.getY() - p2.getY()), c = p1.getX() * p1.getX()
				- p2.getX() * p2.getX() + p1.getY() * p1.getY() - p2.getY()
				* p2.getY(), a1 = (-2d) * (p2.getX() - p3.getX()), b1 = (-2d)
				* (p2.getY() - p3.getY()), c1 = p2.getX() * p2.getX()
				- p3.getX() * p3.getX() + p2.getY() * p2.getY() - p3.getY()
				* p3.getY();
		Point point = new Point(((b1 * c - b * c1) / (b * a1 - b1 * a)), ((a
				* b * c1 - c * b * a1) / (b * b * a1 - b * b1 * a)));
		return point;
	}

	/**
	 * 判断点是否在多边形内
	 * 
	 * @param pnt
	 *            车辆位置
	 * @param pntlist
	 *            多边形边点集合
	 * @return
	 */
	public static boolean PolygonIsContainPoint(Point pnt, List<Point> pntlist) {
		if (pntlist == null) {
			return false;
		}
		int j = 0, cnt = 0;
		for (int i = 0; i < pntlist.size(); i++) {
			j = (i == pntlist.size() - 1) ? 0 : j + 1;
			if ((pntlist.get(i).getY() != pntlist.get(j).getY())
					&& (((pnt.getY() >= pntlist.get(i).getY()) && (pnt.getY() < pntlist
							.get(j).getY())) || ((pnt.getY() >= pntlist.get(j)
							.getY()) && (pnt.getY() < pntlist.get(i).getY())))
					&& (pnt.getX() < (pntlist.get(j).getX() - pntlist.get(i)
							.getX())
							* (pnt.getY() - pntlist.get(i).getY())
							/ (pntlist.get(j).getY() - pntlist.get(i).getY())
							+ pntlist.get(i).getX()))
				cnt++;
		}
		return (cnt % 2 > 0) ? true : false;
	}

	public static boolean isPointInPolygon(double px, double py,
			List<Point> pntlist) {
		boolean isInside = false;
		double ESP = 1e-9;
		int count = 0;
		double linePoint1x;
		double linePoint1y;
		double linePoint2x = 180;
		double linePoint2y;

		linePoint1x = px;
		linePoint1y = py;
		linePoint2y = py;

		for (int i = 0; i < pntlist.size() - 1; i++) {
			double cx1 = pntlist.get(i).getX();
			double cy1 = pntlist.get(i).getY();
			double cx2 = pntlist.get(i + 1).getX();
			double cy2 = pntlist.get(i + 1).getY();
			if (isPointOnLine(px, py, cx1, cy1, cx2, cy2)) {
				return true;
			}
			if (Math.abs(cy2 - cy1) < ESP) {
				continue;
			}

			if (isPointOnLine(cx1, cy1, linePoint1x, linePoint1y, linePoint2x,
					linePoint2y)) {
				if (cy1 > cy2)
					count++;
			} else if (isPointOnLine(cx2, cy2, linePoint1x, linePoint1y,
					linePoint2x, linePoint2y)) {
				if (cy2 > cy1)
					count++;
			} else if (isIntersect(cx1, cy1, cx2, cy2, linePoint1x,
					linePoint1y, linePoint2x, linePoint2y)) {
				count++;
			}
		}
		if (count % 2 == 1) {
			isInside = true;
		}

		return isInside;
	}

	public static double Multiply(double px0, double py0, double px1,
			double py1, double px2, double py2) {
		return ((px1 - px0) * (py2 - py0) - (px2 - px0) * (py1 - py0));
	}

	public static boolean isPointOnLine(double px0, double py0, double px1,
			double py1, double px2, double py2) {
		boolean flag = false;
		double ESP = 1e-9;
		if ((Math.abs(Multiply(px0, py0, px1, py1, px2, py2)) < ESP)
				&& ((px0 - px1) * (px0 - px2) <= 0)
				&& ((py0 - py1) * (py0 - py2) <= 0)) {
			flag = true;
		}
		return flag;
	}

	public static boolean isIntersect(double px1, double py1, double px2,
			double py2, double px3, double py3, double px4, double py4) {
		boolean flag = false;
		double d = (px2 - px1) * (py4 - py3) - (py2 - py1) * (px4 - px3);
		if (d != 0) {
			double r = ((py1 - py3) * (px4 - px3) - (px1 - px3) * (py4 - py3))
					/ d;
			double s = ((py1 - py3) * (px2 - px1) - (px1 - px3) * (py2 - py1))
					/ d;
			if ((r >= 0) && (r <= 1) && (s >= 0) && (s <= 1)) {
				flag = true;
			}
		}
		return flag;
	}

}
