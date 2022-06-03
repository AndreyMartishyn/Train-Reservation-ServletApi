package ua.martishyn.app.data.service;

import ua.martishyn.app.data.dao.impl.TrainModelDaoImpl;
import ua.martishyn.app.data.dao.interfaces.TrainAndModelDao;
import ua.martishyn.app.data.entities.TicketFormDto;
import ua.martishyn.app.data.entities.Train;
import ua.martishyn.app.data.entities.Wagon;

import java.util.List;
import java.util.Optional;

public class TrainService {
    private final TrainAndModelDao trainAndModelDao;


    public TrainService() {
        trainAndModelDao = new TrainModelDaoImpl();
    }

    public boolean isTrainExist(int trainId) {
        final Optional<Train> trainFromDb = trainAndModelDao.getTrain(trainId);
        return trainFromDb.isPresent();
    }

    public List<Wagon> getAllWagons() {
        return trainAndModelDao.getAllWagons();
    }

    public Optional<List<Wagon>> getCoachesByClass(TicketFormDto ticketFormDto) {
        return trainAndModelDao.getWagonsByClass(ticketFormDto.getComfortClass());
    }

    public Optional<Wagon> getWagonById(int selectedWagon) {
        return trainAndModelDao.getWagonById(selectedWagon);
    }

    public Optional<Train> getTrainById(int trainId) {
        return trainAndModelDao.getTrain(trainId);
    }

    public void updateWagon(Wagon bookedWagon) {
        trainAndModelDao.updateCoach(bookedWagon);
    }
}
