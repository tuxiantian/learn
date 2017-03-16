package com.tuxt.learn.util;

import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @author: shilei6@asiainfo.com
 * @version: 2011-9-16
 */
public class PropertyConfigurer extends
		org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {

	protected void loadProperties(Properties props) throws IOException {
		super.loadProperties(props);
		try {
			// Mysql jdbc
			String jdbcUrl = props.getProperty("jdbcUrl").trim();
			String decryJdbcUrl = new String(EncryptionUtil.decode(EncryptionUtil.hex2byte(jdbcUrl.trim()), Constants.DBKEYEES.getBytes()));
			props.setProperty("jdbcUrl", decryJdbcUrl);
			// Mysql username
			String username = props.getProperty("username").trim();
			String decryUserame = new String(EncryptionUtil.decode(EncryptionUtil.hex2byte(username.trim()), Constants.DBKEYEES.getBytes()));
			props.setProperty("username", decryUserame);
			// Mysql password
			String password = props.getProperty("password").trim();
			String decryPassword = new String(EncryptionUtil.decode(EncryptionUtil.hex2byte(password.trim()), Constants.DBKEYEES.getBytes()));
			props.setProperty("password", decryPassword);
			
			// Oracle jdbc
			/*String o_jdbcUrl = props.getProperty("o_jdbcUrl").trim();
			String decryoJdbcUrl = new String(EncryptionUtil.decode(EncryptionUtil.hex2byte(o_jdbcUrl.trim()), Constants.DBKEYEES.getBytes()));
			props.setProperty("o_jdbcUrl", decryoJdbcUrl);
			
			// Oracle username
			String o_userame = props.getProperty("o_userame").trim();
			String decryoUserame = new String(EncryptionUtil.decode(EncryptionUtil.hex2byte(o_userame.trim()), Constants.DBKEYEES.getBytes()));
			props.setProperty("o_userame", decryoUserame);
			
			String o_password = props.getProperty("o_password").trim();
			String decryoPassword = new String(EncryptionUtil.decode(EncryptionUtil.hex2byte(o_password.trim()), Constants.DBKEYEES.getBytes()));
			props.setProperty("o_password", decryoPassword);*/
			
		} catch (Exception e) {
			logger.error("decode password in properties error!", e);
		}
	}
}