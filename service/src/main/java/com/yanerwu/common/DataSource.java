package com.yanerwu.common;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import org.apache.commons.dbcp.BasicDataSource;

public class DataSource extends BasicDataSource {

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	public void setPassword(String password) {
		super.setPassword(DesCodeUtil.decrypt(password));
	}

	public void setUsername(String username) {
		super.setUsername(DesCodeUtil.decrypt(username));
	}

	public void setUrl(String url) {
		super.setUrl(url);
	}

	public void setDriverClassName(String driverClassName) {
		super.setDriverClassName(driverClassName);
	}
}
