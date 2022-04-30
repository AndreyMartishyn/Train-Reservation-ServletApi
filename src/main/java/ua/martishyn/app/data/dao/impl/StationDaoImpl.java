package ua.martishyn.app.data.dao.impl;

import ua.martishyn.app.data.dao.interfaces.StationDao;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.utils.DataBasePoolManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StationDaoImpl implements StationDao {
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
            while (stationFromResultSet.next()) {
                stationFromDb = getStationFromResultSet(stationFromResultSet);
            }
        } catch (SQLException exception) {
            System.out.println("Unable to get station from db" + exception);
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

    private Station getStationFromResultSet(ResultSet resultSet) throws SQLException {
        return Station.builder()
                .id(resultSet.getInt(1))
                .name(resultSet.getString(2))
                .code(resultSet.getString(3))
                .build();
    }

    @Override
    public Optional<List<Station>> getAll() {
        List<Station> stations = new ArrayList<>();
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_STATIONS, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet stationFromResultSet = preparedStatement.executeQuery();
            while (stationFromResultSet.next()) {
                stations.add(getStationFromResultSet(stationFromResultSet));
            }
        } catch (SQLException exception) {
            System.out.println("Unable to get stations from db" + exception);
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
        } catch (SQLException exception) {
            System.out.println("Something wrong with creating station " + exception);
        }
        return false;
    }

    private void createStationStatement(PreparedStatement preparedStatement, Station station) throws SQLException {
        preparedStatement.setString(1, station.getName());
        preparedStatement.setString(2, station.getCode());
    }

    @Override
    public boolean update(Station station) {
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATION);) {
            connection.setAutoCommit(false);
            createStationStatement(preparedStatement, station);
            preparedStatement.setInt(3, station.getId());
            if (preparedStatement.executeUpdate() > 0) {
                connection.commit();
                return true;
            }
            connection.rollback();
        } catch (SQLException exception) {
            System.out.println("Unable to update station " + exception);
        }
        return false;
    }


    @Override
    public boolean delete(int id) {
        boolean deleted = false;
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STATION)) {
            preparedStatement.setInt(1, id);
            deleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            System.out.println("Something wrong with deleting of station " + exception);
        }
        return deleted;
    }
}
