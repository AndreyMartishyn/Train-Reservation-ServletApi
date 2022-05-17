package ua.martishyn.app.data.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.dao.interfaces.TrainAndModelDao;
import ua.martishyn.app.data.entities.TrainModel;
import ua.martishyn.app.data.entities.Train;
import ua.martishyn.app.data.entities.Wagon;
import ua.martishyn.app.data.entities.enums.ComfortClass;
import ua.martishyn.app.data.utils.DataBasePoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrainModelDaoImpl implements TrainAndModelDao {
    private static final Logger log = LogManager.getLogger(TrainModelDaoImpl.class);
    private static final String GET_TRAIN_MODEL_BY_ID = "SELECT * FROM train_models WHERE id = ?;";
    private static final String GET_TRAIN_BY_ID = "SELECT * FROM trains WHERE id = ?;";
    private static final String GET_WAGONS_BY_ROUTE = "SELECT * FROM train_coaches WHERE route_id = ?;";
    private static final String GET_WAGONS_BY_CLASS = "SELECT * FROM train_coaches WHERE comfort_class = ?;";
    private static final String GET_WAGON_BY_ID = "SELECT * FROM train_coaches WHERE coach_id = ?;";
    private static final String GET_ALL_WAGONS = "SELECT * FROM train_coaches;";
    private static final String UPDATE_COACH_SEATS = "UPDATE train_coaches SET seats = ? WHERE coach_id = ?";

    @Override
    public Optional<Train> getTrain(int id) {
        Train trainFromDb = null;
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TRAIN_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet stationFromResultSet = preparedStatement.executeQuery();
            while (stationFromResultSet.next()) {
                trainFromDb = getTrainFromResultSet(stationFromResultSet);
            }
        } catch (SQLException exception) {
            log.error("Problems with getting train by id {}", exception.toString());
        }
        return Optional.ofNullable(trainFromDb);
    }

    @Override
    public Optional<TrainModel> getTrainModel(int id) {
        TrainModel trainModelFromDb = null;
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TRAIN_MODEL_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet trainModelRs = preparedStatement.executeQuery();
            while (trainModelRs.next()) {
                trainModelFromDb = getTrainModelFromResultSet(trainModelRs);
            }
        } catch (SQLException exception) {
            log.error("Problems with getting train model by id {}", exception.toString());
        }
        return Optional.ofNullable(trainModelFromDb);
    }

    private TrainModel getTrainModelFromResultSet(ResultSet trainModelRs) throws SQLException {
        TrainModel model = new TrainModel();
        model.setId(trainModelRs.getInt(1));
        model.setName(trainModelRs.getString(2));
        return model;
    }


    private Train getTrainFromResultSet(ResultSet trainFromResultSet) throws SQLException {
        Train train = new Train();
        train.setId(trainFromResultSet.getInt(1));
        Optional<TrainModel> model = getTrainModel(trainFromResultSet.getInt("model_id"));
        model.ifPresent(train::setModel);
        return train;
    }

    @Override
    public Optional<List<Wagon>> getWagonsForTrain(int trainId) {
        List<Wagon> wagons = new ArrayList<>();
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_WAGONS_BY_ROUTE)) {
            preparedStatement.setInt(1, trainId);
            ResultSet coachesFromResultSet = preparedStatement.executeQuery();
            while (coachesFromResultSet.next()) {
                wagons.add(getCoachesFromResultSet(coachesFromResultSet));
            }
        } catch (SQLException e) {
            log.error("Problems with getting all train-wagons for certain train {}", e.toString());
        }
        return Optional.of(wagons);
    }

    @Override
    public List<Wagon>getAllWagons() {
        List<Wagon> wagons = new ArrayList<>();
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_WAGONS)) {
            ResultSet coachesFromResultSet = preparedStatement.executeQuery();
            while (coachesFromResultSet.next()) {
                wagons.add(getCoachesFromResultSet(coachesFromResultSet));
            }
        } catch (SQLException e) {
            log.error("Problems with getting all train-wagons {}", e.toString());
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
                wagons.add(getCoachesFromResultSet(coachesFromResultSet));
            }
        } catch (SQLException e) {
            log.error("Problems with getting all train-coaches by class {}", e.toString());
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
                wagon = getCoachesFromResultSet(coachesFromResultSet);
            }
        } catch (SQLException e) {
            log.error("Problems with getting all train-coaches {}", e.toString());
        }
        return Optional.ofNullable(wagon);
    }

    @Override
    public boolean updateCoach(Wagon wagon) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DataBasePoolManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_COACH_SEATS);
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, wagon.getNumOfSeats());
            preparedStatement.setInt(2, wagon.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("Problems with updating train-coach {}", e.toString());
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


    private Wagon getCoachesFromResultSet(ResultSet resultSet) throws SQLException {
        Wagon wagon = new Wagon();
        wagon.setId(resultSet.getInt("coach_id"));
        wagon.setRouteId(resultSet.getInt("route_id"));
        wagon.setComfortClass(ComfortClass.valueOf(resultSet.getString("comfort_class")));
        wagon.setNumOfSeats(resultSet.getInt("seats"));
        wagon.setPriceForSeat(resultSet.getInt("price"));
        return wagon;
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



