package ua.martishyn.app.data.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.dao.interfaces.WagonDao;
import ua.martishyn.app.data.entities.Wagon;
import ua.martishyn.app.data.entities.enums.ComfortClass;
import ua.martishyn.app.data.utils.db_pool.DataBasePoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WagonDaoImpl implements WagonDao {
    private static final Logger log = LogManager.getLogger(WagonDaoImpl.class);
    private static final String CREATE_WAGON = "INSERT INTO train_wagons VALUES (DEFAULT, ?,?,?,?)";
    private static final String GET_WAGONS_BY_CLASS = "SELECT * FROM train_wagons WHERE comfort_class = ?;";
    private static final String GET_WAGONS_PAGINATED = "SELECT * FROM train_wagons limit %d, %d;";
    private static final String GET_WAGON_BY_ID = "SELECT * FROM train_wagons WHERE id = ?;";
    private static final String GET_ALL_WAGONS = "SELECT * FROM train_wagons;";
    private static final String UPDATE_WAGON = "UPDATE train_wagons SET route_id=?," +
            " comfort_class= ?, seats = ?, price =?  WHERE id = ?";
    private static final String DELETE_WAGON = "DELETE FROM train_wagons  WHERE id = ?";

    @Override
    public Optional<List<Wagon>> getAllWagonsPaginated(int offset, int limit) {
        List<Wagon> stations = new ArrayList<>();
        String sql = String.format(GET_WAGONS_PAGINATED, offset, limit);
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet wagonResultSet = preparedStatement.executeQuery();
            while (wagonResultSet.next()) {
                stations.add(getWagonFromResultSet(wagonResultSet));
            }
        } catch (SQLException e) {
            log.error("Problems with getting all wagons {}", e.toString());
        }
        return Optional.of(stations);
    }

    @Override
    public boolean createWagon(Wagon wagon) {
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_WAGON)) {
            createWagonStatement(preparedStatement, wagon);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.error("Problems with creating wagon {}", e.toString());
        }
        return false;
    }

    private void createWagonStatement(PreparedStatement preparedStatement, Wagon wagon) throws SQLException {
        preparedStatement.setInt(1, wagon.getRouteId());
        preparedStatement.setString(2, wagon.getType().name());
        preparedStatement.setInt(3, wagon.getNumOfSeats());
        preparedStatement.setInt(4, wagon.getPrice());
    }

    @Override
    public List<Wagon> getAllWagons() {
        List<Wagon> wagons = new ArrayList<>();
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_WAGONS)) {
            ResultSet coachesFromResultSet = preparedStatement.executeQuery();
            while (coachesFromResultSet.next()) {
                wagons.add(getWagonFromResultSet(coachesFromResultSet));
            }
        } catch (SQLException e) {
            log.error("Problems with getting all train_wagons {}", e.toString());
        }
        return wagons;
    }

    @Override
    public Optional<List<Wagon>> getWagonsByClass(String comfortClass) {
        List<Wagon> wagons = new ArrayList<>();
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_WAGONS_BY_CLASS)) {
            preparedStatement.setString(1, comfortClass);
            ResultSet coachesFromResultSet = preparedStatement.executeQuery();
            while (coachesFromResultSet.next()) {
                wagons.add(getWagonFromResultSet(coachesFromResultSet));
            }
        } catch (SQLException e) {
            log.error("Problems with getting all train_wagons by class {}", e.toString());
        }
        return Optional.of(wagons);
    }

    @Override
    public Optional<Wagon> getWagonById(int id) {
        Wagon wagon = null;
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_WAGON_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet coachesFromResultSet = preparedStatement.executeQuery();
            if (coachesFromResultSet.next()) {
                wagon = getWagonFromResultSet(coachesFromResultSet);
            }
        } catch (SQLException e) {
            log.error("Problems with getting wagon by id {}", e.toString());
        }
        return Optional.ofNullable(wagon);
    }

    @Override
    public boolean updateWagon(Wagon wagon) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DataBasePoolManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_WAGON);
            connection.setAutoCommit(false);
            createWagonStatement(preparedStatement, wagon);
            preparedStatement.setInt(5, wagon.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("Problems with updating train_wagons {}", e.toString());
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

    @Override
    public void deleteWagon(int id) {
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_WAGON)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Problems with deleting wagon {}", e.toString());
        }
    }

    private Wagon getWagonFromResultSet(ResultSet resultSet) throws SQLException {
        return Wagon.builder()
                .id(resultSet.getInt(1))
                .routeId(resultSet.getInt(2))
                .type(ComfortClass.valueOf(resultSet.getString(3)))
                .numOfSeats(resultSet.getInt(4))
                .price(resultSet.getInt(5))
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




