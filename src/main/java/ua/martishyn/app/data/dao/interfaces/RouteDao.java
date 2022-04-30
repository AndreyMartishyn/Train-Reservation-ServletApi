package ua.martishyn.app.data.dao.interfaces;

import ua.martishyn.app.data.entities.ComplexRoute;
import ua.martishyn.app.data.entities.SingleRoute;

import java.util.List;
import java.util.Optional;


public interface RouteDao {
 //   Optional<List<ComplexRoute>> getAllRoutes();

    Optional<List<SingleRoute>> getAllIntermediateStationRoutes();

    Optional<SingleRoute> getSingleRoute(int id);

    Optional<List<ComplexRoute>> getAllComplexRoutes();

    boolean createSingleRoute(SingleRoute singleRoute);

    boolean updateSingleRoute(SingleRoute singleRoute);

    boolean deleteSingleRoute(int id);
}
