/**
 * Description: 
 * Author: caopeng
 * Creation time: 2017年3月13日 下午2:36:30
 * (C) Copyright 2013-2016, deamoncao.
 * All rights reserved.
 */
package dc.toolkit.lbs.key;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dc.toolkit.lbs.util.PropertiesManager;

public class MapManager {
	
//	private static final String KEY_PATH = MapManager.class.getResource("").getPath();
//	private static final String BD_KEY_PATH = KEY_PATH + "bd_keys.txt";
//	private static final String AMAP_KEY_PATH = KEY_PATH + "amap_keys.txt";
//	private static final String MAP_URL_PATH= KEY_PATH + "map_url.properties";
	private static final String BD_KEY_PATH = "/conf/bd_keys.txt";
	private static final String AMAP_KEY_PATH = "/conf/amap_keys.txt";
	private static final String MAP_URL_PATH= "/conf/map_url.properties";
	private static final String AMAP_GEO_URL_NM="amapGeoUrl";
	private static final String AMAP_REGEO_URL_NM="amapRegeoUrl";
	private static final String BD_GEO_URL_NM="bdGeoUrl";
	private static final String BD_REGEO_URL_NM="bdRegeoUrl";
	private static MapManager singleton;
	private List<String> amapKeyList;
	private List<String> bdKeyList;
	private String amapGeoUrl;
	private String amapRegeoUrl;
	private String bdGeoUrl;
	private String bdRegeoUrl;
	/* 高德货车路径规划*/
	private String amapTruckRoutUrl;
	
	private MapManager(){
		loadMapUrl();
		loadBdKeys();
		loadAmapKeys();
	}
	
    public static MapManager getInstance(){
        if (singleton == null) {
            synchronized (MapManager.class) {
                if (singleton == null) {
                    singleton = new MapManager();
                    return singleton;
                }
            }
        }
        return singleton;
    }
	
	
	/**
	 * 
	 * Description: 加载百度的key
	 * Author: caopeng
	 * Creation time: 2017年3月13日 下午2:43:13
	 *
	 * @return
	 */
	private void loadBdKeys(){
		bdKeyList = loadKeyFile(BD_KEY_PATH);	
	}
	
	/**
	 * 
	 * Description: 加载高德地图的key
	 * Author: caopeng
	 * Creation time: 2017年3月13日 下午2:43:57
	 *
	 * @return
	 */
	private void loadAmapKeys(){
		amapKeyList = loadKeyFile(AMAP_KEY_PATH);	
	}
	
	/**
	 * 
	 * Description: 加载高德/百度访问的地址URL
	 * Author: caopeng
	 * Creation time: 2017年3月16日 上午9:49:51
	 *
	 */
	private void loadMapUrl(){
		String pathNm = MAP_URL_PATH;
		amapGeoUrl = "http://restapi.amap.com/v3/geocode/geo";
		amapRegeoUrl = "http://restapi.amap.com/v3/geocode/regeo";
		bdGeoUrl = "http://api.map.baidu.com/geocoder/v2/";
		bdRegeoUrl = "http://api.map.baidu.com/geocoder/v2/";
		amapTruckRoutUrl = "https://restapi.amap.com/v4/direction/truck";
//		amapGeoUrl = PropertiesManager.getInistances().getProperty(pathNm,AMAP_GEO_URL_NM);
//		amapRegeoUrl = PropertiesManager.getInistances().getProperty(pathNm,AMAP_REGEO_URL_NM);
//		bdGeoUrl = PropertiesManager.getInistances().getProperty(pathNm,BD_GEO_URL_NM);
//		bdRegeoUrl = PropertiesManager.getInistances().getProperty(pathNm,BD_REGEO_URL_NM);
	}

	/**
	 * 
	 * Description: 加载Key文件
	 * Author: caopeng
	 * Creation time: 2017年3月13日 下午2:42:53
	 *
	 * @param keyPathName
	 * @return
	 */
	private List<String> loadKeyFile(String keyPathName){
		long startTm = System.currentTimeMillis();
		int count = 0;
		List<String> keyList = new ArrayList<>();
		try{
//			InputStream is = new FileInputStream(keyPathName);
			InputStream is = MapManager.class.getResourceAsStream(keyPathName);
			BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			while(br.ready()){
				count++;
				String lines = br.readLine();
				String[] tokens = lines.split("[\t ]+");
				keyList.add(tokens[0]);			
			}
			if(is!=null){
				// 关闭
				br.close();
				is.close();
			}
		}catch(IOException e){
			System.err.println(String.format(Locale.getDefault(),"%s: load define dict failure!",keyPathName));
		}
		System.out.println(String.format(Locale.getDefault(),"define dict %s load finiash,total words:%d,spend times:%dms",
				keyPathName,count,System.currentTimeMillis() - startTm));
		
		return keyList;
	}

	public List<String> getAmapKeyList() {
		return amapKeyList;
	}

	public List<String> getBdKeyList() {
		return bdKeyList;
	}

	public String getAmapGeoUrl() {
		return amapGeoUrl;
	}

	public String getAmapRegeoUrl() {
		return amapRegeoUrl;
	}

	public String getBdGeoUrl() {
		return bdGeoUrl;
	}

	public String getBdRegeoUrl() {
		return bdRegeoUrl;
	}

	public String getAmapTruckRoutUrl() {
		return amapTruckRoutUrl;
	}
	
}
