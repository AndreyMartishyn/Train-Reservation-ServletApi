package ua.martishyn.app.data.service;

import ua.martishyn.app.data.dao.impl.TrainModelDaoImpl;
import ua.martishyn.app.data.dao.interfaces.TrainAndModelDao;
import ua.martishyn.app.data.entities.TicketFormDto;
import ua.martishyn.app.data.entities.Train;
import ua.martishyn.app.data.entities.Wagon;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TrainService {
    private final TrainAndModelDao trainAndModelDao;


    public TrainService() {
        trainAndModelDao = new TrainModelDaoImpl();
    }

    public List<Train> getAllTrains(){
        return trainAndModelDao.getAllTrains().orElse(Collections.emptyList());
    }

    public boolean isTrainExist(int trainId) {
        final Optional<Train> trainFromDb = trainAndModelDao.getTrain(trainId);
        return trainFromDb.isPresent();
    }

    public Optional<Train> getTrainById(int trainId) {
        return trainAndModelDao.getTrain(trainId);
    }


}