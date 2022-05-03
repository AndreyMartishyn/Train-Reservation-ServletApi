package ua.martishyn.app.data.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.dao.interfaces.RouteDao;
import ua.martishyn.app.data.dao.interfaces.StationDao;
import ua.martishyn.app.data.dao.interfaces.TrainAndModelDao;
import ua.martishyn.app.data.entities.ComplexRoute;
import ua.martishyn.app.data.entities.SingleRoute;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.entities.Train;
import ua.martishyn.app.data.utils.DataBasePoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class RouteDaoImpl implements RouteDao {
    private static final Logger log = LogManager.getLogger(RouteDaoImpl.class);
    private static final String GET_ALL_SINGLE_ROUTES = "SELECT * FROM route_stations;";
    private static final String GET_SINGLE_ROUTE_BY_ID = "SELECT * FROM route_stations where id = ?;";
    private static final String ADD_SINGLE_ROUTE = "INSERT INTO route_stations VALUES (?, ?, ?, ? , ?);";
    private static final String DELETE_SINGLE_ROUTE = "DELETE FROM route_stations WHERE id = ?;";
    private static final String UPDATE_SINGLE_ROUTE = "UPDATE route_stations set id = ?, train_id = ?," +
            "station_id = ?, arrival = ?, departure = ?;";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";


    @Override
    public Optional<List<SingleRoute>> getAllIntermediateStationRoutes() {
        List<SingleRoute> routeParts = new ArrayList<>();
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_SINGLE_ROUTES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                routeParts.add(getRoutePartFromResultSet(resultSet));
            }
        } catch (SQLException | ParseException exception) {
            log.error("Problems with getting list of intermediate stations {}", exception.toString());
        }
        return Optional.of(routeParts);
    }

    @Override
    public Optional<SingleRoute> getSingleRoute(int id) {
        SingleRoute singleRoute = null;
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_SINGLE_ROUTE_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                singleRoute = getRoutePartFromResultSet(resultSet);
            }
        } catch (SQLException | ParseException e) {
            log.error("Problems with getting single route {}", e.toString());
        }
        return Optional.ofNullable(singleRoute);
    }

    @Override
    public Optional<List<ComplexRoute>> getAllComplexRoutes() {
        TrainAndModelDao trainDao = new TrainModelDaoImpl();
        List<ComplexRoute> complexRouteList = new ArrayList<>();
        ComplexRoute complexRoute;
        int routeId;
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_SINGLE_ROUTES)) {
            ResultSet routeResultSet = preparedStatement.executeQuery();
            while (routeResultSet.next()) {
                routeId = routeResultSet.getInt(1);
                complexRoute = getComplexRoute(complexRouteList, routeId);
                if (complexRoute == null) {
                    complexRoute = new ComplexRoute();
                    complexRoute.setId(routeId);
                    Optional<Train> train = trainDao.getTrain(routeResultSet.getInt("train_id"));
                    if (train.isPresent()) {
                        complexRoute.setTrain(train.get());
                    }
                    complexRouteList.add(complexRoute);
                }
                addIntermediateStations(complexRoute, routeResultSet);
            }
        } catch (SQLException | ParseException e) {
            log.error("Problems with getting list of routes {}", e.toString());
        }
        return Optional.of(complexRouteList);
    }

    private void addIntermediateStations(ComplexRoute complexRoute, ResultSet routeResultSet) throws SQLException, ParseException {
        DateFormat formatPattern = new SimpleDateFormat(DATE_FORMAT);
        StationDao stationDao = new StationDaoImpl();
        Optional<Station> station = stationDao.getById(routeResultSet.getInt("station_id"));
        Date arrivalDate = formatPattern.parse(routeResultSet.getString("arrival"));
        Date departureDate = formatPattern.parse(routeResultSet.getString("departure"));
        station.ifPresent(value -> complexRoute.addIntermediateStation(value, arrivalDate, departureDate));

    }

    private ComplexRoute getComplexRoute(List<ComplexRoute> complexRouteList, int routeId) {
        for (ComplexRoute route : complexRouteList) {
            if (route.getId() == routeId) {
                return route;
            }
        }
        return null;
    }

    @Override
    public boolean createSingleRoute(SingleRoute singleRoute) {
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_SINGLE_ROUTE)) {
            createRouteStatement(preparedStatement, singleRoute);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Problems when creating single route {}", e.toString());
            return false;
        }
        return false;
    }

    @Override
    public boolean updateSingleRoute(SingleRoute singleRoute) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = DataBasePoolManager.getInstance().getConnection();
            preparedStatement = con.prepareStatement(UPDATE_SINGLE_ROUTE);
            con.setAutoCommit(false);
            createRouteStatement(preparedStatement, singleRoute);
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            log.error("Problems with creating single route {}", e.toString());
            if (con != null) {
                try {
                    con.rollback();
                    return false;
                } catch (SQLException exception) {
                    log.error("Problems with transaction {}", e.toString());
                }
            }
        } finally {
            close(con);
            close(preparedStatement);
        }
        return true;
    }

    private void createRouteStatement(PreparedStatement preparedStatement, SingleRoute singleRoute) throws SQLException {
        DateFormat formatPattern = new SimpleDateFormat(DATE_FORMAT);
        preparedStatement.setInt(1, singleRoute.getId());
        preparedStatement.setInt(2, singleRoute.getTrainId());
        preparedStatement.setInt(3, singleRoute.getStationId());
        preparedStatement.setString(4, formatPattern.format(singleRoute.getArrival()));
        preparedStatement.setString(5, formatPattern.format(singleRoute.getDeparture()));
    }

    @Override
    public boolean deleteSingleRoute(int id) {
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SINGLE_ROUTE)) {
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Problems with deleting single route {}", e.toString());
        }
        return false;
    }

    private SingleRoute getRoutePartFromResultSet(ResultSet resultSet) throws SQLException, ParseException {
        DateFormat formatPattern = new SimpleDateFormat(DATE_FORMAT);
        return SingleRoute.builder()
                .id(resultSet.getInt("id"))
                .trainId(resultSet.getInt("train_id"))
                .stationId(resultSet.getInt("station_id"))
                .arrivalDate(formatPattern.parse(resultSet.getString(4)))
                .departureDate(formatPattern.parse(String.valueOf(resultSet.getString(5))))
                .build();
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