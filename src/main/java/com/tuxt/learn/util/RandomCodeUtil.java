package com.tuxt.learn.util;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * 随机码生成
 * 
 * @since 2012-06-04 14:10:12
 */
public class RandomCodeUtil {
	public static String getRandomCode() {
		// 创建一个随机数生成器类
		Random random = new Random();
		StringBuilder randomCode = new StringBuilder();
		// 设置验证码字数
		int codeCount = 6;
		// 设置验证码内容
		char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9' };
		// 随机产生codeCount数字的验证码。
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字。
			String strRand = String.valueOf(codeSequence[random
					.nextInt(codeSequence.length)]);
			// 将产生的六个随机数组合在一起。
			randomCode.append(strRand);
		}
		return randomCode.toString();
	}
	
	public static String getRandomCode(int num) {
		// 创建一个随机数生成器类
		Random random = new Random();
		StringBuilder randomCode = new StringBuilder();
		// 设置验证码字数
		int codeCount = num;
		// 设置验证码内容
		char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9' };
		// 随机产生codeCount数字的验证码。
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字。
			String strRand = String.valueOf(codeSequence[random
					.nextInt(codeSequence.length)]);
			// 将产生的六个随机数组合在一起。
			randomCode.append(strRand);
		}
		return randomCode.toString();
	}
	
	 public static String getCurrentDateYMRHMS(){
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    	return sdf.format(System.currentTimeMillis());
	    }
	 
	 
}


