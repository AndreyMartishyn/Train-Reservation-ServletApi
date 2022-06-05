package ua.martishyn.app.data.service;

import ua.martishyn.app.data.dao.impl.PaginationHelperImpl;
import ua.martishyn.app.data.dao.interfaces.PaginationHelper;

public class PaginationService {
    private final PaginationHelper paginationHelper;

    public PaginationService() {
        this.paginationHelper = new PaginationHelperImpl();
    }

    public <T> int getNumberOfRows(T t) {
        String className = t.getClass().getSimpleName();
        switch (className) {
            case "RoutePointsPageCommand":
                return paginationHelper.getRowsQuantityFromTable("route_stations");
            case "AdminUsersPageCommand":
                return paginationHelper.getRowsQuantityFromTable("users");
            case "StationsPageCommand":
                return paginationHelper.getRowsQuantityFromTable("stations");
            default:
                return 0;
        }
    }

}
