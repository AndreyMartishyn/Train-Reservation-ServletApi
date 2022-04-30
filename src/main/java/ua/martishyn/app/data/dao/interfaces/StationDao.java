package ua.martishyn.app.data.dao.interfaces;

import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.entities.User;

import java.util.List;
import java.util.Optional;

public interface StationDao {
    Optional<Station> getById(int id);

    Optional<Station> getByName(String name);


    Optional<List<Station>> getAll();

    boolean createStation(Station station);

    boolean update(Station station);

    boolean delete(int id);
}
