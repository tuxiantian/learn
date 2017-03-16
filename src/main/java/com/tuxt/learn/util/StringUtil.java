package com.tuxt.learn.util;

import java.util.Date;

public class StringUtil {

	public static String getIndictseq() {
		// 流水号：4位平台机构编码+14位组包时间YYYYMMDDHH24MMSS+6位流水号（定长），序号随机
		StringBuilder indictseq = new StringBuilder("1085");
		String dateString = DateUtil.formatString(new Date(), "yyyyMMddHHmmss");
		indictseq.append(dateString);
		String smsCode = RandomCodeUtil.getRandomCode(6);
		indictseq.append(smsCode);
		return indictseq.toString();
	}
}
