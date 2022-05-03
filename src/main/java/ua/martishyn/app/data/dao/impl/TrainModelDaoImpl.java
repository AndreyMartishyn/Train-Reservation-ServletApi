package ua.martishyn.app.data.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.dao.interfaces.TrainAndModelDao;
import ua.martishyn.app.data.entities.Model;
import ua.martishyn.app.data.entities.Train;
import ua.martishyn.app.data.utils.DataBasePoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TrainModelDaoImpl implements TrainAndModelDao {
    private static final Logger log = LogManager.getLogger(TrainModelDaoImpl.class);
    private static final String GET_TRAIN_MODEL_BY_ID = "SELECT * FROM train_models WHERE id = ?;";
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
    public Optional<Model> getTrainModel(int id) {
        Model trainModelFromDb = null;
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

    private Model getTrainModelFromResultSet(ResultSet trainModelRs) throws SQLException {
        Model model = new Model();
        model.setId(trainModelRs.getInt(1));
        model.setName(trainModelRs.getString(2));
        return model;
    }


    private Train getTrainFromResultSet(ResultSet trainFromResultSet) throws SQLException {
        Train train = new Train();
        train.setId(trainFromResultSet.getInt(1));
        Optional<Model> model = getTrainModel(trainFromResultSet.getInt("model_id"));
        model.ifPresent(train::setModel);
        return train;
    }
}



