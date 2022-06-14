package ua.martishyn.app.data.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.dao.interfaces.DaoPaginationHelper;
import ua.martishyn.app.data.utils.db_pool.DataBasePoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of Pagination-Helper API
 */

public class DaoPaginationHelperImpl implements DaoPaginationHelper {
    private static final Logger log = LogManager.getLogger(DaoPaginationHelperImpl.class);
    private static final String GET_ROWS_COUNT = "SELECT COUNT(id) FROM %s";

    /**
     * @param tableName
     * @return value>0 or 0, if now rows found
     */
    @Override
    public Integer getRowsQuantityFromTable(String tableName) {
        String sql = String.format(GET_ROWS_COUNT, tableName);
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException exception) {
            log.error("Problems with getting row count for {}, {}", tableName, exception.toString());

        }
        return 0;
    }
}
