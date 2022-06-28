package ua.martishyn.app.data.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.dao.impl.DaoPaginationHelperImpl;
import ua.martishyn.app.data.dao.interfaces.DaoPaginationHelper;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class that works with commands and return
 * appropriate invocation of method
 */

public class PaginationService {
    private static final Logger log = LogManager.getLogger(PaginationService.class);
    private final DaoPaginationHelper paginationHelper;

    public PaginationService() {
        paginationHelper = new DaoPaginationHelperImpl();
    }

    /**
     * Generic <T> function. Based on @currentpage and @entriesPerPage
     * and Command Class passed, gets service class from @getServiceClassBasedOnCommand(command)
     * and executes query from service class.
     *
     * @param command Class, request, entriesPerPage value
     */
    public <T> void makePagination(ICommand command, HttpServletRequest request, int entriesPerPage) {
        int currentPage = getCurrentPage(request);
        int offSet = (currentPage - 1) * entriesPerPage;
        Object serviceClassBasedOnCommand = getServiceClassBasedOnCommand(command);
        Method getEntriesSubList;
        List<T> entriesPaginated = new ArrayList<>();
        try {
            getEntriesSubList = serviceClassBasedOnCommand.getClass().getDeclaredMethod("makeEntriesSubList", int.class, int.class);
            entriesPaginated =
                    (List<T>) getEntriesSubList.invoke(serviceClassBasedOnCommand, offSet, entriesPerPage);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.error("Something happened when getting method ");
        }
        if (entriesPaginated.isEmpty()) {
            request.setAttribute("noEntries", true);
        } else {
            int numOfEntries = getNumberOfRows(command);
            int numOfPages = (int) Math.ceil(numOfEntries * 1.0
                    / entriesPerPage);
            request.setAttribute("noOfPages", numOfPages);
            request.setAttribute("paginatedEntries", entriesPaginated);
            request.setAttribute("currentPage", currentPage);
        }
    }

    /**
     * Returns page number from request.
     *
     * @param request passed
     * @return integer value. If null - return 1.
     */
    public int getCurrentPage(HttpServletRequest request) {
        return Optional.ofNullable(request.getParameter("currentPage"))
                .map(String::toString)
                .map(Integer::parseInt)
                .orElse(1);
    }

    /**
     * Generic <T> method based on reflection. We get class name and based
     * Upon class we get implementation
     *
     * @param command class in which method is invoked
     * @return value from paginationHelper class or 0, if class not supported
     */
    public int getNumberOfRows(ICommand command) {
        String className = command.getClass().getSimpleName();
        switch (className) {
            case "RoutePointsPageCommand":
                return paginationHelper.getRowsQuantityFromTable("route_stations");
            case "AdminUsersPageCommand":
                return paginationHelper.getRowsQuantityFromTable("users");
            case "StationsPageCommand":
                return paginationHelper.getRowsQuantityFromTable("stations");
            case "WagonsPageCommand":
                return paginationHelper.getRowsQuantityFromTable("train_wagons");
            default:
                return 0;
        }
    }

    /**
     * Returns Service Object based on Command Class passed
     * If not found - return "not supported"
     *
     * @param command class
     * @return Object
     */

    public Object getServiceClassBasedOnCommand(ICommand command) {
        String className = command.getClass().getSimpleName();
        switch (className) {
            case "RoutePointsPageCommand":
                return new RouteService();
            case "AdminUsersPageCommand":
                return new UserService();
            case "StationsPageCommand":
                return new StationService();
            case "WagonsPageCommand":
                return new WagonService();
            default:
                return "Not supported";
        }
    }
}
