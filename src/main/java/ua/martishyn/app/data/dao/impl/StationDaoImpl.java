package ua.martishyn.app.data.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.dao.interfaces.StationDao;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.utils.DataBasePoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StationDaoImpl implements StationDao {
    private static final Logger log = LogManager.getLogger(StationDaoImpl.class);
    private static final String CREATE_STATION = "INSERT INTO stations VALUES (DEFAULT, ?, ?);";
    private static final String GET_STATION_BY_ID = "SELECT * FROM stations WHERE id = ?;";
    private static final String GET_STATION_BY_NAME = "SELECT * FROM stations WHERE name = ?;";
    private static final String GET_ALL_STATIONS = "SELECT * FROM stations;";
    private static final String UPDATE_STATION = "UPDATE stations SET name = ?, code = ? WHERE id = ?;";
    private static final String DELETE_STATION = "DELETE FROM stations WHERE id =?";

    @Override
    public Optional<Station> getById(int id) {
        Station stationFromDb = null;
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_STATION_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet stationFromResultSet = preparedStatement.executeQuery();
            if (stationFromResultSet.next()) {
                stationFromDb = getStationFromResultSet(stationFromResultSet);
            }
        } catch (SQLException e) {
            log.error("Problems with getting station by id {}", e.toString());
        }
        return Optional.ofNullable(stationFromDb);
    }

    @Override
    public Optional<Station> getByName(String name) {
        Station stationFromDb = null;
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_STATION_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet stationFromResultSet = preparedStatement.executeQuery();
            while (stationFromResultSet.next()) {
                stationFromDb = getStationFromResultSet(stationFromResultSet);
            }
        } catch (SQLException exception) {
            System.out.println("Unable to get station from db" + exception);
        }
        return Optional.ofNullable(stationFromDb);
    }

    @Override
    public Optional<List<Station>> getAll() {
        List<Station> stations = new ArrayList<>();
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_STATIONS)) {
            ResultSet stationFromResultSet = preparedStatement.executeQuery();
            while (stationFromResultSet.next()) {
                stations.add(getStationFromResultSet(stationFromResultSet));
            }
        } catch (SQLException e) {
            log.error("Problems with getting all stations {}", e.toString());
        }
        return Optional.of(stations);
    }

    @Override
    public boolean createStation(Station station) {
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_STATION)) {
            createStationStatement(preparedStatement, station);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.error("Problems with creating station {}", e.toString());
        }
        return false;
    }

    @Override
    public boolean update(Station station) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DataBasePoolManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_STATION);
            connection.setAutoCommit(false);
            createStationStatement(preparedStatement, station);
            preparedStatement.setInt(3, station.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("Problems with updating station {}", e.toString());
            if (connection != null) {
                try {
                    connection.rollback();
                    return false;
                } catch (SQLException exception) {
                    log.error("Problems with transaction {}", e.toString());
                }
            }
        } finally {
            close(connection);
            close(preparedStatement);
        }
        return true;
    }

    private void createStationStatement(PreparedStatement preparedStatement, Station station) throws SQLException {
        preparedStatement.setString(1, station.getName());
        preparedStatement.setString(2, station.getCode());
    }

    private Station getStationFromResultSet(ResultSet resultSet) throws SQLException {
        return Station.builder()
                .id(resultSet.getInt(1))
                .name(resultSet.getString(2))
                .code(resultSet.getString(3))
                .build();
    }

    @Override
    public boolean delete(int id) {
        boolean deleted = false;
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STATION)) {
            preparedStatement.setInt(1, id);
            deleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Problems with deleting station {}", e.toString());
        }
        return deleted;
    }

    private static void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                log.error("Failed closing resource {}", e.toString());
            }
        }
    }
}
