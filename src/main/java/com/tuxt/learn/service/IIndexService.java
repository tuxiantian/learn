package com.tuxt.learn.service;

import java.util.Map;

public interface IIndexService {

	String getKey(String sourceCode);
	String getPublicKeyFromDb(String sourceCode);
	boolean isAlreadUsed(Map<String, Object> paramData);
	void saveSignInfo(Map<String, Object> paramData);
	Map<String, String> getWebCacheData();
}
