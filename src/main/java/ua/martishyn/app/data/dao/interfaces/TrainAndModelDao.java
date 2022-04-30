package ua.martishyn.app.data.dao.interfaces;

import ua.martishyn.app.data.entities.Model;
import ua.martishyn.app.data.entities.Train;
import java.util.Optional;

public interface TrainAndModelDao {
    Optional<Train> getTrain(int id);

    Optional<Model> getTrainModel(int id);

}
