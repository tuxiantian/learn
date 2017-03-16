package com.tuxt.learn.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import com.ai.frame.logger.Logger;
import com.ai.frame.logger.LoggerFactory;
import com.tuxt.learn.service.IIndexService;

/**
 * 功能：PropertiesUtil 读取propery属性 规则：propery 随时都有可能变更的方在这个里面
 * @author G3-YX-SHIL 2015-02-05
 */
public class PropertiesUtil {
	private static final Logger logger = LoggerFactory.getActionLog(PropertiesUtil.class);
	private static final String encoding = "UTF-8";
	private static PropertiesConfiguration systemconfig = null;
	private static PropertiesConfiguration whitelistconfig = null;
	private static PropertiesConfiguration f5Urlconfig = null;
	private static PropertiesConfiguration systemCommon = null;
	private static PropertiesConfiguration systemNfc = null;
	private static PropertiesConfiguration systemVersion = null;
	private static PropertiesConfiguration redisConfig=null;
	private static PropertiesConfiguration systemVersionIos = null;
	private static Map<String, String> MAP_CACHE = new ConcurrentHashMap<String,String>();
	//cacheType与cachekey中间使用此符号连接
	private static final String CACHE_SPLIT="$";
	
	private static final String SYSTEM="SYSTEM";
	private static final String WHITE_LIST="WHITE_LIST";
	private static final String F5URL="F5URL";
	private static final String COMMON="COMMON";
	private static final String NFCURL="NFCURL";
	private static final String ANDROID_VERSION="ANDROID_VERSION";
	private static final String IOS_VERSION="IOS_VERSION";
	private static final String REDIS="REDIS";
	private static List<String> NO_CACHE_KEY=new ArrayList<String>();

	static {
		systemConfigInit();
		/*whitelistConfigInit();
		f5UrlConfigInit();
		systemCommonInit();
		systemNfcInit();
		systemVersionInit();
		redisConfigInit();*/
		//systemVersionIosInit();
		NO_CACHE_KEY.add("REALNAME_10085");
		NO_CACHE_KEY.add("HTTP_TIMEOUT_TIME_OL");
		NO_CACHE_KEY.add("FTPFILEUPLOADURL");
		NO_CACHE_KEY.add("PC_UPLOAD_URL");
		NO_CACHE_KEY.add("MESSAGE_URL_PATH");
		NO_CACHE_KEY.add("SYSTEM_F5_URL");
	}
	
	public static void systemConfigInit() {
		try {
			systemconfig = new PropertiesConfiguration("config/system.properties");
			systemconfig.setReloadingStrategy(new FileChangedReloadingStrategy());
			systemconfig.setEncoding(encoding);
		} catch (ConfigurationException e) {
			logger.error("!!!!!!!!!!", "",e);
		}
	}

	public static void systemVersionIosInit() {
		try {
			systemVersionIos = new PropertiesConfiguration(
					"./config/system_version_ios.properties");
			systemVersionIos
					.setReloadingStrategy(new FileChangedReloadingStrategy());
			systemVersionIos.setEncoding(encoding);
		} catch (ConfigurationException e) {
			logger.error("!!!!!!!!!!", "",e);
		}
	}

	public static void whitelistConfigInit() {
		try {
			whitelistconfig = new PropertiesConfiguration("./config/whitelist.properties");
			whitelistconfig.setReloadingStrategy(new FileChangedReloadingStrategy());
			whitelistconfig.setEncoding(encoding);
		} catch (ConfigurationException e) {
			logger.error("!!!!!!!!!!", "",e);
		}
	}

	public static void f5UrlConfigInit() {
		try {
			f5Urlconfig = new PropertiesConfiguration("./config/f5_url.properties");
			f5Urlconfig.setReloadingStrategy(new FileChangedReloadingStrategy());
			f5Urlconfig.setEncoding(encoding);
		} catch (ConfigurationException e) {
			logger.error("!!!!!!!!!!", "",e);
		}
	}

	public static void systemCommonInit() {
		try {
			systemCommon = new PropertiesConfiguration("./config/system_common.properties");
			systemCommon.setReloadingStrategy(new FileChangedReloadingStrategy());
			systemCommon.setEncoding(encoding);
		} catch (ConfigurationException e) {
			logger.error("!!!!!!!!!!", "",e);
		}
	}

	public static void systemNfcInit() {
		try {
			systemNfc = new PropertiesConfiguration("./config/system_nfc.properties");
			systemNfc.setReloadingStrategy(new FileChangedReloadingStrategy());
			systemNfc.setEncoding(encoding);
		} catch (ConfigurationException e) {
			logger.error("!!!!!!!!!!", "",e);
			
		}
	}
	public static void systemVersionInit() {
		try {
			systemVersion = new PropertiesConfiguration("./config/system_version.properties");
			systemVersion.setReloadingStrategy(new FileChangedReloadingStrategy());
			systemVersion.setEncoding(encoding);
		} catch (ConfigurationException e) {
			logger.error("!!!!!!!!!!", "",e);
			
		}
	}

	public static String getString(String key) {
		String str = "";
		try {
			if(dbCacheSwitch()){
				//如果存在不从缓存获取的key，直接从配置文件中获取
				if(NO_CACHE_KEY.contains(key)){
					str = systemconfig.getString(key);
					if(str!=null&&str.trim().length()>0){
						return str;
					}
				}
				str=getDefaultValue(SYSTEM+CACHE_SPLIT+key);
			}else{
				str = systemconfig.getString(key);
			}
		} catch (Exception ex) {
			logger.error("!!!!!!!!!!", "",ex);
			str = "";
		}
		return str;
	}
	
	public static String getSystemString(String key) {
		String str = "";
		try {
			str = systemconfig.getString(key);
		} catch (Exception ex) {
			logger.error("!!!!!!!!!!", "",ex);
			str = "";
		}
		return str;
	}
	
	public static String getwhitelistString(String key) {
		String str = "";
		try {
			if(dbCacheSwitch()){
				str=getDefaultValue(WHITE_LIST+CACHE_SPLIT+key);
			}else{
				str = whitelistconfig.getString(key);
			}
		} catch (Exception ex) {
			logger.error("!!!!!!!!!!", "",ex);
			str = "";
		}
		return str;
	}

	public static String getf5UrlString(String key) {
		String str = "";
		try {
			if(dbCacheSwitch()){
				str=getDefaultValue(F5URL+CACHE_SPLIT+key);
			}else{
				str = f5Urlconfig.getString(key);
			}
		} catch (Exception ex) {
			logger.error("!!!!!!!!!!", "",ex);
			str = "";
		}
		return str;
	}

	public static String getCommonInitString(String key) {
		String str = "";
		try {
			if(dbCacheSwitch()){
				str=getDefaultValue(COMMON+CACHE_SPLIT+key);
			}else{
				str = systemCommon.getString(key);
			}
		} catch (Exception ex) {
			logger.error("!!!!!!!!!!", "",ex);
			str = "";
		}
		return str;
	}

	public static String getNfcString(String key) {
		String str = "";
		try {
			if(dbCacheSwitch()){
				str=getDefaultValue(NFCURL+CACHE_SPLIT+key);
			}else{
				str = systemNfc.getString(key);
			}
		} catch (Exception ex) {
			logger.error("!!!!!!!!!!", "",ex);
			str = "";
		}
		return str;
	}
	
	public static String getVersionString(String key) {
		String str = "";
		try {
			if(dbCacheSwitch()){
				str=getDefaultValue(ANDROID_VERSION+CACHE_SPLIT+key);
			}else{
				str = systemVersion.getString(key);
			}
		} catch (Exception ex) {
		logger.error("!!!!!!!!!!", "",ex);
			str = "";
		}
		return str;
	}
	public static String getVersionIosString(String key) {
		String str = "";
		try {
			if(dbCacheSwitch()){
				str=getDefaultValue(IOS_VERSION+CACHE_SPLIT+key);
			}else{
				str = systemVersionIos.getString(key);
			}
		} catch (Exception ex) {
			logger.error("!!!!!!!!!!", "",ex);
			str = "";
		}
		return str;
	}
	public static void redisConfigInit() {
		try {
			redisConfig = new PropertiesConfiguration("./config/redis.properties");
			redisConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
			redisConfig.setEncoding(encoding);
		} catch (ConfigurationException e) {
			logger.error("!!!!!!!!!!", "",e);
		}
	}
	
	public static String getRedisString(String key) {
		String str = "";
		try {
			if(dbCacheSwitch()){
				str=getDefaultValue(REDIS+CACHE_SPLIT+key);
			}else{
				str = redisConfig.getString(key);
			}
		} catch (Exception ex) {
			logger.error("!!!!!!!!!!", "",ex);
			str = "";
		}
		return str;
	}
	/**
	 * 根据key从缓存中获取value如果为null返回空字符串
	 * @param key
	 * @return
	 */
	private static String getDefaultValue(String key){
		String value="";
		try {
			value=MAP_CACHE.get(key);
			if(value==null){
				value="";
			}
		} catch (Exception e) {
			logger.error("!!!!!!!!!!", "",e);
			value="";
		}
		return value;
	}
	
	/**
	 * 获取缓存开关，system.properties 
	 * DB_CACHE_SWITCH ON:走db缓存，其他值走properties缓存
	 * @return
	 */
	private static boolean dbCacheSwitch(){
		boolean isdbCache=false;
		try {
			String str = systemconfig.getString("DB_CACHE_SWITCH");
			//如果开关为打开则读取db缓存
			if("ON".equalsIgnoreCase(str)){
				if(MAP_CACHE.size()==0){
					logger.error("dbCacheSwitch-initCacheData", "#####start#####");
					initCacheData();
					logger.error("dbCacheSwitch-initCacheData", "#####end#####");				
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("获取缓存开关异常", "DB_CACHE_SWITCH",e);
		}
		return isdbCache;
	}
	
	public static void initCacheData(){
		IIndexService indexService=(IIndexService) SpringApplicationUtil.getBean("indexService");
		Map<String, String> data=indexService.getWebCacheData();
		if(!data.isEmpty()){
			MAP_CACHE.clear();
			MAP_CACHE.putAll(data);
			logger.error("初始化web缓存数据","done!!!!!");
		}else{
			logger.error("web缓存数据加载失败！","");
		}
	}
	
	public static void refreshCacheData(){
		logger.error("刷新web缓存数据","start!!!!!");
		IIndexService indexService=(IIndexService) SpringApplicationUtil.getBean("indexService");
		Map<String, String> data=indexService.getWebCacheData();
		Set<String> oldSet=MAP_CACHE.keySet();
		Set<String> newSet=data.keySet();
		logger.error("old cache count:",oldSet.size()+"!!!");
		logger.error("new cache count:",newSet.size()+"!!!");
		MAP_CACHE.putAll(data);
		logger.error("刷新web缓存数据","done!!!!!");
		for(String key : oldSet){
			if(!newSet.contains(key)){
				MAP_CACHE.remove(key);
				logger.error("刷新web缓存数据,删除", key);
			}
		}

	}
	
	/**
	 * 获取允许刷新web端缓存主机白名单
	 * @return
	 */
	public static String getCacheWhiteList(){
		if(MAP_CACHE.size()==0){
			initCacheData();
		}
		return getDefaultValue(SYSTEM+CACHE_SPLIT+"WEB_CACHE_WHITE_LIST");
	}
	/**
	 * 根据type与key获取value
	 * @param cacheType
	 * @param key
	 * @return
	 */
	public static String getCacheDataByKey(String cacheType,String key){
		if(dbCacheSwitch()){
			logger.error("system.properties DB_CACHE_SWITCH", "ON");
		}else{
			logger.error("system.properties DB_CACHE_SWITCH", "OFF");
		}
		return getDefaultValue(cacheType+CACHE_SPLIT+key);
	}
	
}
