package com.tuxt.learn.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.frame.bean.InputObject;
import com.ai.frame.bean.OutputObject;
import com.ai.frame.logger.Logger;
import com.ai.frame.logger.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.tuxt.learn.service.IIndexService;
import com.tuxt.learn.util.Constants;
import com.tuxt.learn.util.MsDesPlus;
import com.tuxt.learn.util.PropertiesUtil;
import com.tuxt.learn.util.PublicKeyConfigUtil;
import com.tuxt.learn.util.Rsa;
import com.tuxt.learn.util.StringUtil;

@Controller
@RequestMapping(value="/index")
public class IndexController {

	private static final Logger logger = LoggerFactory.getActionLog(IndexController.class);

	
	@Autowired
	IIndexService indexService;
	
	@ResponseBody
	@RequestMapping(value="/refreshCache",method=RequestMethod.GET)
	public OutputObject refreshCache() {
		try {
			PropertiesUtil.refreshCacheData();
			return new OutputObject(Constants.RETURN_CODE.SUCCESS_CODE, "success");
		} catch (Exception e) {
			logger.error("", "refreshCache", e);
			return new OutputObject(Constants.RETURN_CODE.COMM_ERR_CODE, "error");
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/validateSign",method=RequestMethod.POST)
	public OutputObject validateSign(@RequestBody InputObject in) {
		try {
			// 解密参数值
			String param =String.valueOf(in.getParams().get("param"));
			// 请求来源编码
			String requestSource = String.valueOf(in.getParams().get("requestSource"));
			String encyKey = indexService.getKey(requestSource);// 获取对应请求来源编码的key

			MsDesPlus ms = new MsDesPlus(encyKey);
			Map map = JSON.parseObject(ms.decrypt(param), Map.class);

			// 解密后明文放入整体入参中
			in.getParams().putAll(map);

			String rsaParam = PropertiesUtil.getCommonInitString("RSA_PARAM_FORCE_"	+ requestSource);// 判断是否需要rsa解密

			if (!"".equals(rsaParam) && null != rsaParam) {

				String[] rsaParamArr = rsaParam.split("\\|");

				StringBuilder mingwenSb = new StringBuilder();
				for (String rsaParamKey : rsaParamArr) {
					mingwenSb.append(in.getParams().get(rsaParamKey));
				}

				String mingwenStr  = mingwenSb.toString();

				logger.info("","mingwenStr = "+mingwenStr);

				boolean signatureVerification = signatureForceRsa(in,mingwenStr, requestSource);// 第一次验证
				if (!signatureVerification) {// 如果验签失败更新公钥再验证一次
					PublicKeyConfigUtil.remove(requestSource);// 移除内存的公钥从接口更新
					signatureVerification = signatureForceRsa(in,mingwenStr, requestSource);
				}

				if (signatureVerification) {// 验签通过 跳转页面
					String timeTag =String.valueOf(in.getParams().get("timeTag"));
					//判断时间戳是否有效  是否在配置的时间范围之内
					if(!isInInterVal(timeTag,PropertiesUtil.getCommonInitString("SIGNATURE_ENABLE_TIME")))
					{
						logger.error("验签时间戳超过"+PropertiesUtil.getCommonInitString("SIGNATURE_ENABLE_TIME")+"分钟","");
						return new OutputObject(Constants.RETURN_CODE.COMM_ERR_CODE,"会话已失效");
					}else 
					{
						//如果验签时间戳在有效时间之内、判断是否已使用 如果已使用并且时间戳一致则认为该签名已使用 如果时间戳不一致 则认为请求的签名为重复签名,更新该签名串
						if(indexService.isAlreadUsed(in.getParams()))
						{
							//该条验签数据已被使用过
							return new OutputObject(Constants.RETURN_CODE.COMM_ERR_CODE, "会话已失效");
						}else
						{							
							///该条验签数据未使用  保存验签信息到后台DB中 记录保存时间 保存状态为‘未使用’  如果后台DB中已经存在请求来源+流水号+选号号码关联的验签数据 则不保存
							indexService.saveSignInfo(in.getParams());

							OutputObject out = new OutputObject(Constants.RETURN_CODE.SUCCESS_CODE, "数据验签通过");
							out.getBean().put("renovateTime",PropertiesUtil.getCommonInitString("PC_RENOVATE_TIME"));
							out.getBean().put("renovateTimeCount",PropertiesUtil.getCommonInitString("PC_RENOVATE_TIME_COUNT"));
							out.getBean().put("systemF5Url",PropertiesUtil.getString("SYSTEM_F5_URL"));
							Set<String> keysSet = map.keySet();
							for (String key : keysSet) {
								out.getBean().put(key, (String) map.get(key));
							}

							//返回验签时间戳 用于提交时识别验签串
							out.getBean().put("timeTag", timeTag);
							//省端流水号替换成系统流水号  保证每次发起请求 系统都是第一次处理业务 
							out.getBean().put("transactionIDProv", out.getBean().get("transactionID"));
							out.getBean().put("transactionID",StringUtil.getIndictseq());

							return out;
						}
					}

				} else {// 验签不通过 提示网厅
					return new OutputObject(Constants.RETURN_CODE.COMM_ERR_CODE,"数据验签不通过，请提交合法数据");
				}
			}else
			{
				return new OutputObject(Constants.RETURN_CODE.COMM_ERR_CODE,"非法接入方");
			}
		} catch (Exception e) {
			logger.error("!!!!!!!!!!", "",e);
			return new OutputObject(Constants.RETURN_CODE.UNKOWN_ERR_CODE,"error");
		}
	}

	private  boolean isInInterVal(String timeTag,String timeInterval){
		boolean isInterval = false;
		try {
			int interval = Integer.parseInt(timeInterval);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");

			Date beginDate = sf.parse(timeTag);
			Date nowDate = new Date();
			Date endDate = sf.parse(sf.format(nowDate));

			long  between = endDate.getTime() - beginDate.getTime();
			double result = between * 1.0 / (1000 * 60); 
			if(result < interval){
				isInterval=true;
			}
		}
		catch(Exception e)
		{
			logger.error("nfcTimeTag", "时间戳解析错误", e);
		}

		return isInterval;
	}
	
	private boolean signatureForceRsa(InputObject in, String mingwen,
			String requestSource) {
		boolean flag = false;

		String publicKey = getPublicKey(requestSource);// 获取公钥
		logger.info("","publicKey" + publicKey);
		String signature="";
		if ("".equals(publicKey)|| null == publicKey) {// 获取公钥失败
			flag = false;
		} else {
			try {
				signature = java.net.URLDecoder.decode(String.valueOf(in.getParams().get("signature")));

				String afterDecry = new Rsa.Decoder(publicKey).decode(signature);

				logger.info("",requestSource + "signatureRsa:"+"验签明文----:------" + mingwen);
				logger.info("",requestSource + "signatureRsa:----公钥-----公钥" + publicKey);
				logger.info("",requestSource + "signatureRsa:-----验签数据 -----" + signature);
				logger.info("",requestSource + "signatureRsa:----解密后的数据----- " + afterDecry);
				//省端签名串格式为 ########|timeTag
				String compareStr = afterDecry.split("\\|")[0];
				in.getParams().put("timeTag", afterDecry.split("\\|")[1]);

				if (mingwen.equals(compareStr)) {
					PublicKeyConfigUtil.setPublicKey(requestSource, publicKey);
					flag = true;
				}
			} catch (Exception e) {
				logger.info("","signatureRsa-----" + publicKey);
				logger.info("","signatureRsa-----" + signature);
				logger.error("signatureRsa", "数据验签失败", e);
				flag = false;
			}
		}
		return flag;
	}
	
	private String getPublicKey(String requestSource) {
		String publicKey = PublicKeyConfigUtil.getPublicKey(requestSource);
		if (null == publicKey || "".equals(publicKey)) {
			PublicKeyConfigUtil.setPublicKey(requestSource, indexService.getPublicKeyFromDb(requestSource));
		}
		return publicKey;
	}
}
