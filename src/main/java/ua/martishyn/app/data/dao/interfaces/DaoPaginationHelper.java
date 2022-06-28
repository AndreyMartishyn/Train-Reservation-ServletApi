package ua.martishyn.app.data.dao.interfaces;

/**
 * API that allows to get number of ID-ROWS from table
 */

public interface DaoPaginationHelper {

    /**
     * Main function that allows to get number of ID-ROWS from table
     *
     * @param tableName
     * @return value > 0 or 0, if now rows found
     */
    Integer getRowsQuantityFromTable(String tableName);
}
