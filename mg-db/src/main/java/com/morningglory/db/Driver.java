package com.morningglory.db;

import com.morningglory.db.engine.Constants;
import com.morningglory.db.jdbc.JdbcConnection;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class Driver implements java.sql.Driver{

    private static final ThreadLocal<Connection> DEFAULT_CONNECTION =
            new ThreadLocal<>();
    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        if (StringUtils.isBlank(url)) {
            throw new SQLException("url is null");
        }

        if (url.equals(Constants.DEFAULT_URL)) {
            return DEFAULT_CONNECTION.get();
        } else if (url.startsWith(Constants.START_URL)) {
            //return new JdbcConnection();
            return null;
        }else {
            return null;
        }
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return false;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 0;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
