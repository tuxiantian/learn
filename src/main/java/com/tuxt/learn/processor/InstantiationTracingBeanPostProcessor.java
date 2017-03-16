package com.tuxt.learn.processor;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.tuxt.learn.util.PropertiesUtil;

public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {  
  
	@Override  
    public void onApplicationEvent(ContextRefreshedEvent event) { 
    	//需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。  
    	if(event.getApplicationContext().getParent() == null){
    		try {
    			new Thread(new Runnable() {
    				public void run() {
    					long begin=System.currentTimeMillis();
    					PropertiesUtil.initCacheData();
    					long end=System.currentTimeMillis();
//    					logger.error("cache init end", "total time="+(end-begin));
    				}
    			}).start();
    		} catch (Exception e) {
//    			logger.error("cache init", "error",e);
    		}
    	}
    }
 }  