package ua.martishyn.app.data.dao.interfaces;

import ua.martishyn.app.data.entities.RoutePoint;
import ua.martishyn.app.data.entities.Wagon;

import java.util.List;
import java.util.Optional;

public interface WagonDao {

    List<Wagon> getAllWagons();

    Optional<List<Wagon>> getAllWagonsPaginated(int offset, int limit);

    Optional<List<Wagon>> getWagonsByClass(String comfortClass);

    Optional<Wagon> getWagonById(int id);

    boolean createWagon(Wagon wagon);

    boolean updateWagon(Wagon wagon);

    void deleteWagon(int id);
}
