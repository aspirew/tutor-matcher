package com.tutor.matcher.userLogic.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBCPDataSource {
    
    private static BasicDataSource ds = new BasicDataSource();
    
    static {
        ds.setUrl("jdbc:postgresql://host.docker.internal:5432/user_service_db");
        ds.setUsername("postgres");
        ds.setPassword("123");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }
    
    public static Connection getConnection() {
    	try {    		
    		return ds.getConnection();
    	}
    	catch(SQLException e) {
    		throw new RuntimeException(e);
    	}
    }
    
    private DBCPDataSource(){ }
}