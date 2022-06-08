package ua.martishyn.app.data.dao.interfaces;

import ua.martishyn.app.data.entities.Route;
import ua.martishyn.app.data.entities.RoutePoint;

import java.util.List;
import java.util.Optional;


public interface RouteDao {

    Optional<List<RoutePoint>> getAllRoutePointsPaginated(int offset, int limit);

    Optional<RoutePoint> getRoutePoint(int id, int stationId);

    Optional<List<Route>> getAllRoutes();

    boolean createRoutePoint(RoutePoint routePoint);

    boolean updateRoutePoint(RoutePoint routePoint);

    boolean deleteSingleRoute(int id, int stationId);

}
