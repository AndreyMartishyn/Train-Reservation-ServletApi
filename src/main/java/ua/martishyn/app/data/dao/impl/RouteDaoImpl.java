package ua.martishyn.app.data.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.dao.interfaces.RouteDao;
import ua.martishyn.app.data.dao.interfaces.StationDao;
import ua.martishyn.app.data.dao.interfaces.TrainAndModelDao;
import ua.martishyn.app.data.entities.Route;
import ua.martishyn.app.data.entities.RoutePoint;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.entities.Train;
import ua.martishyn.app.data.utils.db_pool.DataBasePoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RouteDaoImpl implements RouteDao {
    private static final Logger log = LogManager.getLogger(RouteDaoImpl.class);
    private static final String GET_ALL_ROUTES = "SELECT * FROM route_stations";
    private static final String GET_ALL_ROUTEPOINTS = "SELECT * FROM route_stations limit %d, %d";
    private static final String GET_ROUTEPOINT = "SELECT * FROM route_stations where id = ? and station_id = ?;";
    private static final String ADD_ROUTEPOINT = "INSERT INTO route_stations VALUES (?, ?, ?, ? , ?);";
    private static final String DELETE_ROUTEPOINT = "DELETE FROM route_stations WHERE id = ? AND station_id = ?;";
    private static final String UPDATE_ROUTEPOINT = "UPDATE route_stations set train_id = ?," +
            "station_id = ?, arrival = ?, departure = ? WHERE id= ? AND station_id =?;";

    @Override
    public Optional<List<RoutePoint>> getAllRoutePointsPaginated(int offset, int limit) {
        List<RoutePoint> routeParts = new ArrayList<>();
        String paginatedSql = String.format(GET_ALL_ROUTEPOINTS, offset, limit);
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(paginatedSql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                routeParts.add(getRoutePointFromResultSet(resultSet));
            }
        } catch (SQLException exception) {
            log.error("Problems with getting list of intermediate stations {}", exception.toString());
        }
        return Optional.of(routeParts);
    }

    @Override
    public Optional<RoutePoint> getRoutePoint(int id, int stationId) {
        RoutePoint routePoint = null;
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ROUTEPOINT)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, stationId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                routePoint = getRoutePointFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            log.error("Problems with getting single route {}", e.toString());
        }
        return Optional.ofNullable(routePoint);
    }

    @Override
    public Optional<List<Route>> getAllRoutes() {
        TrainAndModelDao trainDao = new TrainModelDaoImpl();
        List<Route> routeList = new ArrayList<>();
        Route route;
        int routeId;
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ROUTES)) {
            ResultSet routeResultSet = preparedStatement.executeQuery();
            while (routeResultSet.next()) {
                routeId = routeResultSet.getInt(1);
                route = getRoute(routeList, routeId);
                if (route == null) {
                    route = new Route();
                    route.setId(routeId);
                    Optional<Train> train = trainDao.getTrain(routeResultSet.getInt("train_id"));
                    if (train.isPresent()) {
                        route.setTrain(train.get());
                    }
                    routeList.add(route);
                }
                addIntermediateStations(route, routeResultSet);
            }
        } catch (SQLException e) {
            log.error("Problems with getting list of routes {}", e.toString());
        }
        return Optional.of(routeList);
    }

    private void addIntermediateStations(Route route, ResultSet routeResultSet) throws SQLException {
        StationDao stationDao = new StationDaoImpl();
        Optional<Station> station = stationDao.getById(routeResultSet.getInt("station_id"));
        LocalDateTime arrivalDate = routeResultSet.getObject("arrival", LocalDateTime.class);
        LocalDateTime departureDate = routeResultSet.getObject("departure", LocalDateTime.class);
        station.ifPresent(value -> route.addIntermediateStation(value, arrivalDate, departureDate));

    }

    private Route getRoute(List<Route> routeList, int routeId) {
        for (Route route : routeList) {
            if (route.getId() == routeId) {
                return route;
            }
        }
        return null;
    }

    @Override
    public boolean createRoutePoint(RoutePoint routePoint) {
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_ROUTEPOINT)) {
            createRouteStatement(preparedStatement, routePoint);
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
    public boolean updateRoutePoint(RoutePoint routePoint) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = DataBasePoolManager.getInstance().getConnection();
            preparedStatement = con.prepareStatement(UPDATE_ROUTEPOINT);
            con.setAutoCommit(false);
            updateRoutePointStatement(preparedStatement, routePoint);
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            log.error("Problems with updating single route {}", e.toString());
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

    private void updateRoutePointStatement(PreparedStatement preparedStatement, RoutePoint routePoint) throws SQLException {
        preparedStatement.setInt(1, routePoint.getTrainId());
        preparedStatement.setInt(2, routePoint.getStationId());
        preparedStatement.setObject(3, routePoint.getArrival());
        preparedStatement.setObject(4, routePoint.getDeparture());
        preparedStatement.setInt(5, routePoint.getId());
        preparedStatement.setInt(6, routePoint.getStationId());

    }

    private void createRouteStatement(PreparedStatement preparedStatement, RoutePoint routePoint) throws SQLException {
        preparedStatement.setInt(1, routePoint.getId());
        preparedStatement.setInt(2, routePoint.getTrainId());
        preparedStatement.setInt(3, routePoint.getStationId());
        preparedStatement.setObject(4, routePoint.getArrival());
        preparedStatement.setObject(5, routePoint.getDeparture());
    }

    @Override
    public boolean deleteSingleRoute(int id, int stationId) {
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROUTEPOINT)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, stationId);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Problems with deleting single route {}", e.toString());
        }
        return false;
    }


    private RoutePoint getRoutePointFromResultSet(ResultSet resultSet) throws SQLException {
        return RoutePoint.builder()
                .id(resultSet.getInt("id"))
                .trainId(resultSet.getInt("train_id"))
                .stationId(resultSet.getInt("station_id"))
                .arrivalDate(resultSet.getObject(4, LocalDateTime.class))
                .departureDate(resultSet.getObject(5, LocalDateTime.class))
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