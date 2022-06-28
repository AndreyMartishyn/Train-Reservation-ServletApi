package ua.martishyn.app.data.dao.interfaces;

import ua.martishyn.app.data.entities.Train;
import ua.martishyn.app.data.entities.TrainModel;

import java.util.List;
import java.util.Optional;

public interface TrainAndModelDao {
    Optional<Train> getTrain(int id);

    Optional<TrainModel> getTrainModel(int id);

    Optional<List<Train>> getAllTrains();

}
