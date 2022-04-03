package ua.martishyn.app.data.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataBasePoolManager {

    private static DataBasePoolManager dbManager;
    private DataSource dataPool;

    public DataBasePoolManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext =(Context) initContext.lookup("java:comp/env");
            dataPool = (DataSource) envContext.lookup("jdbc/traindb");
        } catch (NamingException exception) {
            exception.printStackTrace();
        }
    }

    public static synchronized DataBasePoolManager getInstance() {
        if (dbManager == null) {
            dbManager = new DataBasePoolManager();
        }
        return dbManager;
    }

    public Connection getConnection() throws SQLException {
        return dataPool.getConnection();
    }
}
