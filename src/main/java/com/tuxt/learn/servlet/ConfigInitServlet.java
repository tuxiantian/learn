package com.tuxt.learn.servlet;

/**
 * 配置初始化servlet
 */

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.ai.frame.logger.Logger;
import com.ai.frame.logger.LoggerFactory;
import com.tuxt.learn.util.PropertiesUtil;


public class ConfigInitServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getActionLog(ConfigInitServlet.class);
	private static final long serialVersionUID = 1336605823973032785L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			new Thread(new Runnable() {
				public void run() {
					long begin=System.currentTimeMillis();
					PropertiesUtil.initCacheData();
					long end=System.currentTimeMillis();
					logger.error("cache init end", "total time="+(end-begin));
				}
			}).start();
		} catch (Exception e) {
			logger.error("cache init", "error",e);
		}
	}
	
}
