package ua.martishyn.app.data.dao.impl;

import ua.martishyn.app.data.dao.interfaces.RouteDao;
import ua.martishyn.app.data.dao.interfaces.StationDao;
import ua.martishyn.app.data.dao.interfaces.TrainAndModelDao;
import ua.martishyn.app.data.entities.ComplexRoute;
import ua.martishyn.app.data.entities.SingleRoute;
import ua.martishyn.app.data.entities.Station;
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
    private static final String GET_ALL_SINGLE_ROUTES = "SELECT * FROM route_stations;";
    private static final String GET_SINGLE_ROUTE_BY_ID = "SELECT * FROM route_stations where id = ?;";
    private static final String ADD_SINGLE_ROUTE = "INSERT INTO route_stations VALUES (?, ?, ?, ? , ?);";
    private static final String DELETE_SINGLE_ROUTE = "DELETE FROM route_stations WHERE id = ?;";
    private static final String UPDATE_SINGLE_ROUTE = "UPDATE route_stations set id = ?, train_id = ?," +
            "station_id = ?, arrival = ?, departure = ?;";
    private static final DateFormat formatPattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final StationDao stationDao = new StationDaoImpl();
    private static final TrainAndModelDao trainDao = new TrainModelDaoImpl();

    @Override
    public Optional<List<SingleRoute>> getAllIntermediateStationRoutes() {
        List<SingleRoute> routeParts = new ArrayList<>();
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_SINGLE_ROUTES);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                routeParts.add(getRoutePartFromResultSet(resultSet));
            }

        } catch (SQLException | ParseException exception) {
            System.out.println("problems with getting list or parsing date format " + exception);
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
            System.out.println("problems with getting single route " + e);
        }
        return Optional.ofNullable(singleRoute);
    }

    @Override
    public Optional<List<ComplexRoute>> getAllComplexRoutes() {
        List<ComplexRoute> complexRouteList = new ArrayList<>();
        ComplexRoute complexRoute;
        int routeId;
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_SINGLE_ROUTES);
             ResultSet routeResultSet = preparedStatement.executeQuery();) {
            while (routeResultSet.next()) {
                routeId = routeResultSet.getInt(1);
                complexRoute = getComplexRoute(complexRouteList,  routeId);
                if (complexRoute == null) {
                    complexRoute = new ComplexRoute();
                    complexRoute.setId(routeId);
                    complexRoute.setTrain(trainDao.getTrain(routeResultSet.getInt("train_id")).get());
                    complexRouteList.add(complexRoute);
                }
                addIntermediateStations(complexRoute, routeResultSet);
            }
        } catch (SQLException | ParseException e) {
            System.out.println("Something wrong with making ComplexRoutes");
        }
        return Optional.of(complexRouteList);
    }

    private void addIntermediateStations(ComplexRoute complexRoute, ResultSet routeResultSet) throws SQLException, ParseException {
        Optional<Station> station = stationDao.getById(routeResultSet.getInt("station_id"));
        Date arrivalDate = formatPattern.parse(routeResultSet.getString("arrival"));
        Date departureDate = formatPattern.parse(routeResultSet.getString("departure"));
        station.ifPresent(value -> complexRoute.addIntermediateStation(value, arrivalDate, departureDate));

    }

    private ComplexRoute getComplexRoute(List<ComplexRoute> complexRouteList, int routeId) {
        for (ComplexRoute  route : complexRouteList) {
            if (route.getId() == routeId){
                return route;
            }
        }
        return null;
    }

    @Override
    public boolean createSingleRoute(SingleRoute singleRoute) {
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_SINGLE_ROUTE);) {
            createRouteStatement(preparedStatement, singleRoute);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with creating new single route " + e);
            return false;
        }
        return false;
    }

    @Override
    public boolean updateSingleRoute(SingleRoute singleRoute) {
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SINGLE_ROUTE);) {
            connection.setAutoCommit(false);
            createRouteStatement(preparedStatement, singleRoute);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Something with updating single route " + e);
            return false;
        }
        return false;
    }

    private void createRouteStatement(PreparedStatement preparedStatement, SingleRoute singleRoute) throws SQLException {
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
        } catch (SQLException exception) {
            System.out.println("Something wrong with single route deleting");
        }
        return false;
    }

    private SingleRoute getRoutePartFromResultSet(ResultSet resultSet) throws SQLException, ParseException {
        return SingleRoute.builder()
                .id(resultSet.getInt("id"))
                .trainId(resultSet.getInt("train_id"))
                .stationId(resultSet.getInt("station_id"))
                .arrivalDate(formatPattern.parse(resultSet.getString(4)))
                .departureDate(formatPattern.parse(String.valueOf(resultSet.getString(5))))
                .build();
    }

//    private ComplexRoute getRouteFromResultSet(ResultSet routesResultSet) throws SQLException {
//        ComplexRoute route = new ComplexRoute();
//        route.setId(routesResultSet.getInt(1));
//        Optional<Train> trainFromDb = trainAndModelDao.getTrain(routesResultSet.getInt(2));
//        trainFromDb.ifPresent(route::setTrain);
//        List<ComplexRoute.IntermediateStation>
//        route.setIntermediateStations(routesResultSet.getObject(3, List<>));
//        Train train = ;
//
//    }
}

//@Override
//    public Optional<List<ComplexRoute>> getAllRoutes() {
//        List<ComplexRoute> routeList = new ArrayList<>();
//        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(GET_ROUTE_PARTS);) {
//            ResultSet routesResultSet = preparedStatement.executeQuery();
//            while (routesResultSet.next()) {
//           //     routeList.add(getRouteFromResultSet(routesResultSet));
//            }
//        } catch (SQLException exception) {
//            exception.printStackTrace();
//        }
//
//        return Optional.empty();
//    }
