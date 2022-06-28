package ua.martishyn.app.data.service;

import ua.martishyn.app.data.dao.impl.WagonDaoImpl;
import ua.martishyn.app.data.dao.interfaces.WagonDao;
import ua.martishyn.app.data.entities.Route;
import ua.martishyn.app.data.entities.TicketFormDto;
import ua.martishyn.app.data.entities.Wagon;
import ua.martishyn.app.data.entities.enums.ComfortClass;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WagonService {
    private final WagonDao wagonDao;
    private final RouteService routeService;
    private final DataInputValidator dataInputValidator;

    public WagonService() {
        wagonDao = new WagonDaoImpl();
        routeService = new RouteService();
        dataInputValidator = new DataInputValidatorImpl();
    }

    public List<Wagon> getAllWagons() {
        return wagonDao.getAllWagons();
    }

    public List<Wagon> makeEntriesSubList(int offSet, int entriesPerPage) {
        return wagonDao.getAllWagonsPaginated(offSet, entriesPerPage).orElse(Collections.emptyList());
    }

    public List<Integer> getRouteIds() {
        Optional<List<Route>> allRoutes = routeService.getAllRoutes();
        return allRoutes.get().stream()
                .map(Route::getId)
                .collect(Collectors.toList());
    }

    public Optional<List<Wagon>> getWagonsByClass(TicketFormDto ticketFormDto) {
        return wagonDao.getWagonsByClass(ticketFormDto.getComfortClass());
    }

    public Optional<Wagon> getWagonById(int selectedWagon) {
        return wagonDao.getWagonById(selectedWagon);
    }


    public boolean updateWagonFromRequestData(HttpServletRequest request) {
        Optional<Wagon> wagonById = wagonDao.getWagonById(Integer.parseInt(request.getParameter("id")));
        if (wagonById.isPresent()) {
            wagonById.get().setType(ComfortClass.valueOf(request.getParameter("type")));
            wagonById.get().setPrice(Integer.parseInt(request.getParameter("price")));
            wagonById.get().setNumOfSeats(Integer.parseInt(request.getParameter("seats")));
        }
        return wagonDao.updateWagon(wagonById.get());
    }

    public boolean createWagonFromRequestData(HttpServletRequest request) {
        return wagonDao.createWagon(getWagonFromRequest(request));
    }

    public void updateWagon(Wagon wagon) {
        wagonDao.updateWagon(wagon);
    }

    public void deleteWagonById(int id) {
        wagonDao.deleteWagon(id);
    }

    public Wagon getWagonFromRequest(HttpServletRequest request) {
        return Wagon.builder()
                .routeId(Integer.parseInt(request.getParameter("routeId")))
                .type(ComfortClass.valueOf(request.getParameter("type")))
                .numOfSeats(Integer.parseInt(request.getParameter("seats")))
                .price(Integer.parseInt(request.getParameter("price")))
                .build();
    }

    public boolean isWagonInputValid(HttpServletRequest request) {
        String seats = request.getParameter("seats").trim();
        if (!dataInputValidator.isValidNumInput(seats)) {
            request.setAttribute("wrongSeats", true);
            return false;
        }
        String price = request.getParameter("price").trim();
        if (!dataInputValidator.isValidNumInput(price)) {
            request.setAttribute("wrongPrice", true);
            return false;
        }
        return true;
    }

}


