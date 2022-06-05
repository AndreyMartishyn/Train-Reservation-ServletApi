package ua.martishyn.app.data.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.dao.interfaces.PaginationHelper;
import ua.martishyn.app.data.utils.db_pool.DataBasePoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaginationHelperImpl implements PaginationHelper {
    private static final Logger log = LogManager.getLogger(PaginationHelperImpl.class);
    private static final String GET_ROWS_COUNT = "SELECT COUNT(id) FROM %s";


    @Override
    public int getRowsQuantityFromTable(String tableName) {
        String sql = String.format(GET_ROWS_COUNT, tableName);
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException exception) {
            log.error("Problems with getting row count for {}, {}",tableName, exception.toString());

        }
        return 0;
    }
}
