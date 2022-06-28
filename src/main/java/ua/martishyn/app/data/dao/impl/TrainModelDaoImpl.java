package ua.martishyn.app.data.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.dao.interfaces.TrainAndModelDao;
import ua.martishyn.app.data.entities.Train;
import ua.martishyn.app.data.entities.TrainModel;
import ua.martishyn.app.data.utils.db_pool.DataBasePoolManager;

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
    private static final String GET_ALL_TRAINS = "SELECT * FROM trains;";
    private static final String GET_TRAIN_BY_ID = "SELECT * FROM trains WHERE id = ?;";

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

    @Override
    public Optional<List<Train>> getAllTrains() {
        List<Train> trains = new ArrayList<>();
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_TRAINS)) {
            ResultSet trainsFromRS = preparedStatement.executeQuery();
            while (trainsFromRS.next()) {
                trains.add(getTrainFromResultSet(trainsFromRS));
            }
        } catch (SQLException e) {
            log.error("Problems with getting all trains {}", e.toString());
        }
        return Optional.of(trains);
    }

    private TrainModel getTrainModelFromResultSet(ResultSet trainModelRs) throws SQLException {
        return TrainModel.builder()
                .id(trainModelRs.getInt(1))
                .name(trainModelRs.getString(2))
                .build();
    }

    private Train getTrainFromResultSet(ResultSet trainFromResultSet) throws SQLException {
        Train train = new Train();
        train.setId(trainFromResultSet.getInt(1));
        Optional<TrainModel> model = getTrainModel(trainFromResultSet.getInt("model_id"));
        model.ifPresent(train::setModel);
        return train;
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



