package com.tuxt.learn.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ai.frame.bean.InputObject;
import com.ai.frame.util.JsonUtil;
import com.alibaba.fastjson.JSONObject;
import com.tuxt.learn.exception.BusiException;
import com.tuxt.learn.util.MsDesPlus;
import com.tuxt.learn.util.Rsa;
import com.tuxt.learn.util.StringUtil;
import com.tuxt.learn.util.httputil.HttpRequesterHandler;

public class IndexControllerTest {
	
	@Test
	public void testValidateSign() throws IOException, BusiException {
		String url="http://localhost:1104/learn/index/validateSign";
		HttpRequesterHandler requesterHandler=new HttpRequesterHandler("1000");
		String busiType="20",requestSource="100201",transactionID=StringUtil.getIndictseq(),billId="15039686014";
		Map<String, String> params=new HashMap<String, String>();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("busiType", busiType);
		jsonObject.put("requestSource", requestSource);
		jsonObject.put("transactionID", transactionID);
		jsonObject.put("billId", billId);
		String encyKey="HSTYW";
		MsDesPlus ms = new MsDesPlus(encyKey);
		params.put("param", ms.encrypt(jsonObject.toJSONString()));
		
		params.put("requestSource", "100201");
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
		System.out.println(sf.format(new Date()));
		String timeTag=sf.format(new Date());
//		timeTag="2017-3-13 09:46:31";//测试会话已失效情形
		System.out.println("timeTag:"+timeTag);
		String privateKey="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCocD+1CdjCzY7fpIsCeTrhkvmnIlyDOt06t4VT4BDxtNdehovZ9fHjRriQV5Jg1WGMCp2C9ZquQZTwAm+WwUWVC0BocB8LoA5fcBn+zworiwnHH4jPf0pFAB3ct3i+hS37DMr+ErtWCvx9/Mw9RkZlke5iWG7ofqYqrZQUZrbjUsDBJarB33raxLTb9Ku9FJlfoOYYvc8ALiCq2TSbxMhM0rlnsS7gBPjNIqepqBmPNEjHBJ36YRftPoWCWqfM5LGUBzqZNRe79gXYlQPWoBefsGNMeaSP0qO/OpoeDpoNq1qOQSyLUWosC49VckqOmZoPwgPSFWs8BCO72vFMVHvtAgMBAAECggEBAJ3M+4JKBU8LcSzIZIsetDEcltMj9/Phs90MjdY0duta9uJRvremGMVKPiLMSBB8F/UxBLvqCM30Fup7/bsIun2VmvO9BvjHLG/xAAmeQfLlQXXRb0SmBhh6sX9US8SB1L/8DjjVp7hGdqI5scY+Djmd6+0q2EzsShR1peMvD2wLUAUQhVcvZk4X6GuchniCLNaH4HKQX32gduWMV6E22nK2G72nI0M9Mc+i3lp0NeMbFfxRWivwZq0/haMdotgG8lNE2MO++3mFRJKVQwlxbNimmZVoe8Ip0SOrs1E49FcbjJ+ceC9/neqX6FfIr5vmQxFDQuBXiTYXU6Nj1Q7VQwECgYEA/jBjJhCK9zxanDWfKtK+dtpvbB1IOvWZGp2LPa6GvkkVp6GfpFKgTkYQ0dEiPHfRUaBWuReFHxnb+JbF4YjTbH3ODKBa0ybSug5CJXuObkqEIomOa7miKsZqQa5Owc2CZCVojuCQilpZbroV7EWnpuOWM6gAEc3/vLBh5RjlaGECgYEAqaN2R/xadCKlRrmTmH5tOnVt9JbYbdCxVz/EaEOuyMWV3aF2/5jw3a0ASB5PV3Pt2xe9DWq1RJh8TDcLSft6H5f3KuriwqIggquNqQy4yonWD0tR9DyxzBS0VJ0HepYaQOwdWvrZxMy+P5iUYlXUV6BkEVPG3190evJ1PB9gjw0CgYAjm+m34mdlXUogSg7DNATEp7MmRS/iOpe5N8rS2Ek+DQUAl87CeWTFsmEW06JlMC/drIWZpmbaxYegWpbcEovzzef4stombHDm6apwj8+TOdMq93RBOR8zuNoX23BjDAQxPWBZa7Iz/5Y5wc3ibJug8I8RK37e/6nM6DS+oG864QKBgC/k1bU3OzaEvZL9O/2W3DHgC549pHdEr30JpOMnEMbgehJrztQVExQTDvxNimsPcP0lc5vBH9JxFs7Rz58gn/chiYgVbtYyvekGlGVBET8lsUj6I/ZH2I7c3ZjyCLWEgX8I6rTQFAGrKJ997Y7h2qSuxPHbbE+OfK1lnNHPrgxVAoGBAOP9O8D0HQM7me7WsDlD76CPn9cDFaiHiWS56yMm2VPPK3P818vJ189Yrj2AbxxyaZFwosKgL+XHAdurSsR2HdbPEuWMpah/Fs7bpeAj4SF88AhTbpg1fIIlReIhVroU/CyPfN/2vMISCEyljVmVmeedbIDQpI5LqEmOOfMKq8jN";
		String signature=String.format("%s%s%s%s|%s", busiType,requestSource,transactionID,billId,timeTag);
		
		params.put("signature", java.net.URLEncoder.encode(new Rsa.Encoder(privateKey).encode(signature)));
		InputObject inputObject=new InputObject();
		inputObject.getParams().putAll(params);
		
		String resultJson=requesterHandler.sendRestRequestJSON(url, JsonUtil.convertObject2Json(inputObject), 10000);
		System.out.println(resultJson);
	}

}
