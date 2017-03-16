package com.tuxt.learn.service.impl;

import com.tuxt.learn.dao.impl.BaseDaoImpl;
import com.tuxt.learn.service.IBaseService;

/**
 * @author shilei6@asiainfo.com
 * @since 2015-04-20
 */
public class BaseServiceImpl implements IBaseService  {
	private BaseDaoImpl baseDao;

	public BaseDaoImpl getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDaoImpl baseDao) {
		this.baseDao = baseDao;
	}
	
}
