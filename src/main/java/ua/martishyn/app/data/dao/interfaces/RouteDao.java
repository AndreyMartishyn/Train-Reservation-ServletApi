package ua.martishyn.app.data.dao.interfaces;

import ua.martishyn.app.data.entities.Route;
import ua.martishyn.app.data.entities.RoutePoint;

import java.util.List;
import java.util.Optional;


public interface RouteDao {
    Optional<List<RoutePoint>> getAllIntermediateStationRoutes();

    Optional<RoutePoint> getSingleRoute(int id, int stationId);

    Optional<List<Route>> getAllComplexRoutes();

    boolean createSingleRoute(RoutePoint routePoint);

    boolean updateSingleRoute(RoutePoint routePoint);

    boolean deleteSingleRoute(int id, int stationId);
}
