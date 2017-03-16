package com.tuxt.learn.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	public static final String DBKEYEES="ZYZXRGSH";
	/*********system dao  *************/
	public static final int DAO_BATCH_NUMBER_UPDATE=100;
	public static final int DAO_BATCH_NUMBER_INSERT=100;
	public static final int DAO_BATCH_NUMBER_DELETE=100;


	public static final int PARAM_LIMLT_NUM=4;//文件中至少有4个参数Seq CustName CustCertNo BillId
	public static final int PARAM_MAX_NUM=10;//文件中最多有10个参数Seq CustName CustCertNo BillId

	public static final boolean XHSBATCH_SUCESS_FLAG=true;//第三方验证成功
	public static final boolean XHSBATCH_FALSE_FLAG=false;//第三方验证失败

	public static final String RETURN＿FILE_FAILE_FLAG="1";//验证文件格式错误
	public static final String RETURN＿FILE_SUCESS_FLAG="0";//验证文件格式正确

	public static final String XHSBATCH_FLAG="1";//新华社文件线下处理
	public static final String GZTBATCH_FLAG="2";//国政通实时处理

	public static final int CREATE_FILE_NUM=100;//批量查验数量
	public static final String FILENAMELIST="filelist.txt ";
	public static final int FILE_COUNT_NUM=10000;//每个文件的记录数

	public static final String BATCH_UPLOAD_FILE_FLAG="0";//是否立即写反馈文件0立即 1查验完毕


	/*****实时接口的调用参数****/
	public static final int BACTH_SEND_COUNT_MUMBER=100;//默认一次发送100条
	public static final int BACTH_THREAD_COUNT_MUMBER=2000;//20000条按1000条进行拆分 启动线程数20000/2000
	public static final int ONE_FILE_COUNT=20000;//批量查验数量

	public interface RETURN_CODE {
		String SUCCESS_CODE = "0000";
		String COMM_ERR_CODE = "2999";
		String UNKOWN_ERR_CODE = "9999";
	}
	
	/*******角色******/
	public static abstract interface ROLE_CODE
	  {
	     public static final String AUDITOR = "1"; //审核人
	     public static final String AUDITOR_ZJ = "2"; //审核专家
	     public static final String FeedBack = "3"; //省公司反馈人员
	     public static final String OPERATION = "4"; //运营人员
	     public static final String ADMIN = "5"; //管理员
	  }
	/*******工单状态******/
	public static abstract interface WORKSTATUS{
		public static final String BEFOREAUDIT="0";//待审核
		public static final String PASS="1";//通过
		public static final String NOPASS="2";//不通过
		public static final String NOSURE="3";//待定
		public static final String BEFOREFEEDBACK="4";//待反馈
		public static final String APPENDREGISTER="5";//补登记
	}
	/*******sdk工单状态******/
	public static abstract interface STANDARD_ORDER_STATUS{
		public static final String BEFOREAUDIT="0";//待审核
		public static final String PASS="1";//通过
		public static final String NOPASS="2";//不通过
	}
	/*******sdk工单审核方式******/
	public static abstract interface STANDARD_ORDER_AUDIT_WAY{
		public static final String SYSTEM_AUDIT="0";//系统审核
		public static final String PERSON_AUDIT="1";//人工审核
	}
	/**
	 * 业务类型
	 * @author tuxiantian@163.com
	 *	名：XINRUWANG 值：11 描述：新入网
	 *	名：BUDENGJI 值：10 描述：补登记
	 *	名：ZIZHUKAJIHUO 值：9 描述：自助卡激活
	 *	名：SHENGNEIBUKA 值：12 描述：省内补卡
	 */
	public static abstract interface BUSY_TYPE{

		public static final String XINRUWANG="11";//新入网
		public static final String BUDENGJI="10";//补登记
		public static final String ZIZHUKAJIHUO="9";//自助卡激活
		public static final String SHENGNEIBUKA="12";//省内补卡
		public static final String DAIBANBUKA="13";//省内代办补卡
		public static final String YIDIBUKA="14";//省际异地补卡
		public static final String ZHENGHE="15";//政和
		public static final String ZHENGQI="16";//政企
	}
	//省编码
	public static final Map<String,String> provinceMap=new HashMap<String,String>();
	//工单状态
	public static final Map<String,String> workStatus = new HashMap<String, String>();
	//sdk工单状态
	public static final Map<String,String> standardOrderStatus = new HashMap<String, String>();
	//sdk工单审核方式
	public static final Map<String,String> standardOrderAuditWay = new HashMap<String, String>();
	//业务类型
	public static final Map<String,String> businessType = new HashMap<String, String>();
	//审核意见
	public static final Map<String,String> auditOpitionType = new HashMap<String, String>();
	// 业务类型
	public static final Map<String,String> busiType = new HashMap<String, String>();
	// 业务类型
	public static final Map<String,String> busiTypeWei = new HashMap<String, String>();
	// 人工审核类型(app)
	public static final Map<String,String> opinionType = new HashMap<String, String>();
	// 人工审核类型(微信和网页)
	public static final Map<String,String> wechatAndWebOpinionType = new HashMap<String, String>();
	// 机器审核状态
	public static final Map<String,String> robotAudit = new HashMap<String, String>();
	//受理渠道
	public static final Map<String,String> acceptChannel= new HashMap<String, String>();
	//用户综合信息查询审核意见
	public static final Map<String,String> yhzhxxcx_opinionType= new HashMap<String, String>();

	//证据类型
	public static final Map<String,String> certificateType= new HashMap<String, String>();

	public static final Map<String,String> mbAuditType = new HashMap<String, String>();

	// 人像信息返回结果
	public static final Map<String, String> portraitInformationResultType = new HashMap<String, String>();
	//漫游新疆 请求渠道
	public static final Map<String, String> requestChannel= new HashMap<String, String>();
	// 标准工单业务类型
	public static final Map<String, String> standardBusiType= new HashMap<String, String>();
	// 语音提取比对结果
	public static final Map<String, String> textReuslt= new HashMap<String, String>();

	/**
	 * 调用发送异网短信接口返回码定义
	 * */
	public static abstract interface OTHERNET_MSG{

		public static final String OK="0";//成功
		public static final String OUTFLOW="8";//流量超过限制
		public static final String REPORTOK="DELIVRD";//状态报告成功
		public static final String INSIDE="inside";//短信来源，表示该短信为批量发送
	}

	//发送异网短信接口返回码对应信息
	public static final Map<String, String> otherNetMsgMap = new HashMap<String, String>();

	static{
		acceptChannel.put("0", "网页");
		acceptChannel.put("1", "微信");
		acceptChannel.put("2", "app");
		acceptChannel.put("3", "网上营业厅");
		acceptChannel.put("4", "阳光高考");

		provinceMap.put("100", "北京");
		provinceMap.put("220", "天津");
		provinceMap.put("210", "上海");
		provinceMap.put("230", "重庆");
		provinceMap.put("311", "河北");
		provinceMap.put("371", "河南");
		provinceMap.put("871", "云南");
		provinceMap.put("240", "辽宁");
		provinceMap.put("451", "黑龙江");
		provinceMap.put("731", "湖南");
		provinceMap.put("551", "安徽");
		provinceMap.put("531", "山东");
		provinceMap.put("991", "新疆");
		provinceMap.put("250", "江苏");
		provinceMap.put("571", "浙江");
		provinceMap.put("791", "江西");
		provinceMap.put("270", "湖北");
		provinceMap.put("771", "广西");
		provinceMap.put("931", "甘肃");
		provinceMap.put("351", "山西");
		provinceMap.put("471", "内蒙古");
		provinceMap.put("290", "陕西");
		provinceMap.put("431", "吉林");
		provinceMap.put("591", "福建");
		provinceMap.put("851", "贵州");
		provinceMap.put("200", "广东");
		provinceMap.put("971", "青海");
		provinceMap.put("891", "西藏");
		provinceMap.put("280", "四川");
		provinceMap.put("951", "宁夏");
		provinceMap.put("898", "海南");

		workStatus.put("0", "待审核");
		workStatus.put("1", "已通过");
		workStatus.put("2", "不通过");
		workStatus.put("3", "待定");
		workStatus.put("4", "待反馈");
		workStatus.put("5", "补登记");

		standardOrderStatus.put("0", "待审核");
		standardOrderStatus.put("1", "已通过");
		standardOrderStatus.put("2", "不通过");
		standardOrderStatus.put("3", "系统审核");

		businessType.put("1", "疑似审核");
		businessType.put("2", "抽样审核");
		businessType.put("3", "定位审核");

		auditOpitionType.put("1", "黑白照片");
		auditOpitionType.put("2", "手机，电脑拍摄");
		auditOpitionType.put("3", "PS图");
		auditOpitionType.put("4", "反面一致");
		auditOpitionType.put("5", "其他");
		auditOpitionType.put("6", "工单反馈");

		busiTypeWei.put("11", "新入网");
		busiTypeWei.put("10", "补登记");
		busiTypeWei.put("9", "自助卡激活");

		busiType.put("1", "新入网");
		busiType.put("2", "补登记");
		busiType.put("0", "自助卡激活");
		busiType.put("11", "新入网");
		busiType.put("10", "补登记");
		busiType.put("9", "自助卡激活");
		busiType.put("12", "省内补卡");
		busiType.put("13", "省内代办补卡");
		busiType.put("14", "省际异地补卡");
		busiType.put("15", "政和");
		busiType.put("16", "政企车联网");

		opinionType.put("1", "通过");
		opinionType.put("2", "手机照片");
		opinionType.put("3", "复印件");
		opinionType.put("4", "使用同一张身份证拍摄反面信息");
		opinionType.put("5", "使用同一张身份证拍摄反面信息");
		opinionType.put("6", "临时证件");
		opinionType.put("7", "PS证件");
		opinionType.put("8", "其他");
		opinionType.put("9", "电脑照片");
		opinionType.put("10", "正反面不符");
		opinionType.put("11", "无照片");
		opinionType.put("12", "正面复印件");
		opinionType.put("13", "反面复印件");
		opinionType.put("14", "正面ps证件");
		opinionType.put("15", "反面ps证件");
		opinionType.put("16", "正面电脑照片");
		opinionType.put("17", "反面电脑照片");
		opinionType.put("18", "公安系统验证不通过");
		opinionType.put("19", "未上传手持证件");
		opinionType.put("20", "未上传身份证原件");
		opinionType.put("21", "身份证头像和手持头像不一致");
		opinionType.put("22", "头像不清晰");
		opinionType.put("25", "人像一致");
		opinionType.put("26", "人像不一致");
		opinionType.put("27", "调用接口超时");
		opinionType.put("X", "省端校验不通过");

		yhzhxxcx_opinionType.put("1", "通过");
		yhzhxxcx_opinionType.put("2", "人证比对不是同一人");
		yhzhxxcx_opinionType.put("3", "非我公司认定的有效身份证件");
		yhzhxxcx_opinionType.put("4", "复印件");
		yhzhxxcx_opinionType.put("5", "电脑照片");
		yhzhxxcx_opinionType.put("6", "手机照片");
		yhzhxxcx_opinionType.put("7", "PS照");
		yhzhxxcx_opinionType.put("8", "其他");


		wechatAndWebOpinionType.put("18", "通过");
		wechatAndWebOpinionType.put("19", "未上传手持证件");
		wechatAndWebOpinionType.put("20", "未上传身份证原件");
		wechatAndWebOpinionType.put("21", "身份证头像和手持头像不一致");
		wechatAndWebOpinionType.put("22", "头像不清晰");
		wechatAndWebOpinionType.put("23", "公安部头像不合格");
		wechatAndWebOpinionType.put("24", "其他");


		robotAudit.put("0", "成功");
		robotAudit.put("1", "复印件");
		robotAudit.put("2", "翻拍手机照片");
		robotAudit.put("3", "翻拍电脑照片");
		robotAudit.put("4", "复印件生成器");
		robotAudit.put("5", "PS证件");
		robotAudit.put("6", "临时身份证");

		mbAuditType.put("0", "未审核");
		mbAuditType.put("1", "已通过");
		mbAuditType.put("-1", "无效");
		mbAuditType.put("-2", "疑难工单");
		certificateType.put("1", "身份证");

		portraitInformationResultType.put("0", "成功");
		portraitInformationResultType.put("1", "手持证件照或视频无人像");
		portraitInformationResultType.put("2", "手持证件照人像模糊");
		portraitInformationResultType.put("3", "公安部照片无人像");
		portraitInformationResultType.put("4", "公安部照片人像模糊");
		portraitInformationResultType.put("-1", "调用失败");

		otherNetMsgMap.put("-1","未知错误");
		otherNetMsgMap.put("0","成功");
		otherNetMsgMap.put("1","消息结构错");
		otherNetMsgMap.put("2","非法源IP地址");
		otherNetMsgMap.put("3","认证错误");
		otherNetMsgMap.put("4","协议版本错误");
		otherNetMsgMap.put("6","短信内容超过规定的长度");
		otherNetMsgMap.put("8","流量控制错，超出最高流量");
		otherNetMsgMap.put("12","用户账号未登录");
		otherNetMsgMap.put("13","目标号码在黑名单中");
		otherNetMsgMap.put("15","通道不支持");
		otherNetMsgMap.put("22","客户账号已经被关闭");
		otherNetMsgMap.put("23","客户账号状态错误");
		otherNetMsgMap.put("24","客户账号余额不足");
		otherNetMsgMap.put("25","内容包含敏感词");
		otherNetMsgMap.put("26","模版过滤失败");
		otherNetMsgMap.put("27","长短信拆分条数过多");
		otherNetMsgMap.put("28","模版过滤失败");
		otherNetMsgMap.put("29","错误号码");
		otherNetMsgMap.put("MX:0001","签名匹配规则不成功");
		otherNetMsgMap.put("MX:0002","向上级通道提交短信失败");
		otherNetMsgMap.put("MX:0003","单个手机号码当天下行条数超过上限(长短信算1条)");
		otherNetMsgMap.put("MX:0004","短信内容中包含敏感词(HTTP对应错误码：25)");
		otherNetMsgMap.put("MX:0005","模版过滤失败(签名未报备)(HTTP对应错误码：26)");
		otherNetMsgMap.put("MX:0006","通道敏感词");
		otherNetMsgMap.put("MX:0007","客户投诉黑名单");
		otherNetMsgMap.put("MX:0008","根据签名无法匹配到通道");
		otherNetMsgMap.put("MX:0009","目标号码在网关黑名单中");
		otherNetMsgMap.put("MX:0010","目标号码不符合手机号码规范");
		otherNetMsgMap.put("MX:0011","目标号码在禁1年黑名单中");
		otherNetMsgMap.put("MX:0012","目标号码在客户退订黑名单中");
		otherNetMsgMap.put("MX:0013","目标号码在网关黑名单中");

		requestChannel.put("cx", "彩信");
		requestChannel.put("h5", "网页");

		standardOrderAuditWay.put("0", "系统审核");
		standardOrderAuditWay.put("1", "人工审核");

		standardBusiType.put("cloudCard", "跨区补卡");
		standardBusiType.put("sdkInfo", "SDK");
		standardBusiType.put("gfx", "高风险");
		standardBusiType.put("yhxxzhcx", "综合工单");
		standardBusiType.put("21", "实名认证激活");
		standardBusiType.put("22", "校园售卡激活");
		standardBusiType.put("11", "网上售卡");
		standardBusiType.put("15", "政和");
		standardBusiType.put("16", "政企");
		standardBusiType.put("25", "广东视频认证");

		textReuslt.put("0","比对成功");
		textReuslt.put("1","比对失败");
		textReuslt.put("2","调用失败");

	}

}
