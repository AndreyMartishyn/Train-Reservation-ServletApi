package ua.martishyn.app.data.dao.interfaces;

import ua.martishyn.app.data.entities.TrainModel;
import ua.martishyn.app.data.entities.Train;
import ua.martishyn.app.data.entities.Wagon;

import java.util.List;
import java.util.Optional;

public interface TrainAndModelDao {
    Optional<Train> getTrain(int id);

    Optional<TrainModel> getTrainModel(int id);

    Optional<List<Wagon>> getWagonsForTrain(int id);

    Optional<List<Wagon>> getWagonsByClass(String comfortClass);

    Optional<Wagon> getWagonById(int id);

    boolean updateCoach(Wagon wagon);
}
