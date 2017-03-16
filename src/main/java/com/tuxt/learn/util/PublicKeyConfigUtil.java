package com.tuxt.learn.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * 公钥存储类  
 * @author G3-YX-SHIL
 *
 */
public class PublicKeyConfigUtil implements Serializable {
	private static final long serialVersionUID = 370375525118408703L;
	private  static final Map<String,String> map=new HashMap<String,String> ();
	/**
	 * 根据省编码获取公钥
	 * @param provCode
	 * @return
	 */
	public static String getPublicKey(String provCode){
			return map.get(provCode);
	}
	/**
	 * 以省编码为key，放公钥到内存中
	 * @param provCode
	 * @param publicKey
	 * @return
	 */
	public static String setPublicKey(String provCode,String publicKey){
			return map.put(provCode, publicKey);
	}
	/**
	 * 移出失效的公钥
	 * @param provCode
	 */
	public static void remove(String provCode){
			map.remove(provCode);
	}
}
