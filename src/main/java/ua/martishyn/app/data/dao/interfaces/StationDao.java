package ua.martishyn.app.data.dao.interfaces;

import ua.martishyn.app.data.entities.Station;

import java.util.List;
import java.util.Optional;

public interface StationDao {
    Optional<Station> getById(int id);

    Optional<List<Station>> getAll();

    Optional<Station> getByName(String name);

    boolean createStation(Station station);

    boolean update(Station station);

    boolean delete(int id);
}
