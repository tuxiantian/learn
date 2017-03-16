package com.tuxt.learn.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.InitBinder;

import com.ai.frame.logger.Logger;
import com.ai.frame.logger.LoggerFactory;
import com.tuxt.learn.service.IIndexService;
import com.tuxt.learn.util.DateUtil;
import com.tuxt.learn.util.PropertiesUtil;

public class IndexServiceImpl extends BaseServiceImpl implements IIndexService{

	private final Logger logger = LoggerFactory.getServiceLog(IndexServiceImpl.class);
	private static final String NAME_SPACE = "pushKeyMapper";
	
	@Override
	public String getKey(String sourceCode) {
		Map<String, Object> paramData=new HashMap<String, Object>();
		paramData.put("provCode", sourceCode);
		return String.valueOf(getBaseDao().get(NAME_SPACE, "queryKey", paramData).get("pushKey"));
	}

	@Override
	public String getPublicKeyFromDb(String sourceCode) {
		Map<String, Object> paramData=new HashMap<String, Object>();
		paramData.put("provCode", sourceCode);
		return String.valueOf(getBaseDao().get(NAME_SPACE, "queryRsaKey", paramData).get("publicKey"));
	}
	
	@Override
	public Map<String, String> getWebCacheData(){
		List<Map<String, Object>> list = getBaseDao().query(NAME_SPACE, "getWebCacheData");
		Map<String, String> data = null;
		if (!list.isEmpty()) {
			data = new HashMap<>();
			for (Map<String, Object> map : list) {
				String cacheKey = String.valueOf(map.get("cacheKey"));
				String cacheValue = String.valueOf(map.get("cacheValue"));
				String cacheType = String.valueOf(map.get("cacheType"));
				if (cacheType != null && cacheType.trim().length() > 0) {
					data.put(cacheType.toUpperCase() + "$" + cacheKey, cacheValue);
				} else {
					data.put(cacheKey, cacheValue);
				}
			}
		}
		return data;
	}
	
	@Override
	public void saveSignInfo(Map<String, Object> paramData){
		getBaseDao().insert(NAME_SPACE, "saveSignInfo", paramData);
	}
	
	@Override
	public boolean isAlreadUsed(Map<String, Object> paramData){
		List<Map<String, Object>> list=getBaseDao().query(NAME_SPACE, "querySignInfo", paramData);
		if (list.size()>0) {
			Map<String, Object> data=list.get(0);
			if("0".equals(data.get("state"))){
				
				Date insertTime = DateUtil.string2Date(String.valueOf(data.get("createDate")),"yyyy-MM-dd HH:mm:ss");
				Date currentTime = DateUtil.string2Date(String.valueOf(new Date()),"yyyy-MM-dd HH:mm:ss");
				String timeInterval=(String)paramData.get("timeInterval");
				
				//提交时 判断对应的验签时间是否超时
				if(isInInterVal(insertTime,currentTime,timeInterval))
				{
					return true;
				}else
				{
					//时间超时的话 更新验签数据的状态 0:未使用 1:已使用 2:提交时、时间已超时
					paramData.put("STATE", "2");
					paramData.put("STATE_DESC", "提交时已超时");
					updateSignState(paramData);
					return false;
				}
			}
		}
		return false;
	}
	
	private void updateSignState(Map<String, Object> paramData) {
		getBaseDao().update(NAME_SPACE, "updateSignState", paramData);
	}
	
	private  boolean isInInterVal(Date insertTime,Date currentTime,String timeInterval){
		boolean isInterval = false;
		try {
			int interval = Integer.parseInt(timeInterval);

			long  between = currentTime.getTime() - insertTime.getTime();
			double result = between * 1.0 / (1000 * 60); 
			if(result < interval){
				isInterval=true;
			}
		}
		catch(Exception e)
		{
			logger.error("isInInterVal","时间戳解析错误", e);
		}

		return isInterval;
	}
	
	public static void main(String[] args) {
		String a="涂贤田",b="412829199109093219";
		StringBuilder stringBuilder=new StringBuilder();
		StringBuilder stringBuilder2=new StringBuilder();
		stringBuilder.append(a.substring(0, 1));
		for (int i = 0; i < a.length()-1; i++) {
			stringBuilder.append("*");
		}
		System.out.println(stringBuilder.toString());
		String result=b.substring(0, 6)+"********"+b.substring(b.length()-4, b.length()-1)+"*";
		System.out.println(result);
		if (result.equals("412829********321*")) {
			System.out.println(true);
		}
		
	}
}
