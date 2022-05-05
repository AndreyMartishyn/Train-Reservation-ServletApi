package ua.martishyn.app.data.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComplexRoute implements Serializable {
    private int id;
    private Train train;
    private List<IntermediateStation> intermediateStations = new ArrayList<>();

    public static class IntermediateStation implements Serializable {
        Station station;
        Date arrivalDate;
        Date departureDate;

        public IntermediateStation(Station station, Date arrivalDate, Date departureDate) {
            this.station = station;
            this.arrivalDate = arrivalDate;
            this.departureDate = departureDate;
        }

        public Station getStation() {
            return station;
        }

        public Date getArrivalDate() {
            return arrivalDate;
        }

        public Date getDepartureDate() {
            return departureDate;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public void addIntermediateStation(Station station, Date arrival, Date departure) {
        intermediateStations.add(new IntermediateStation(station, arrival, departure));
    }

    public List<IntermediateStation> getIntermediateStations() {
        return intermediateStations;
    }

    public void setIntermediateStations(List<IntermediateStation> intermediateStations) {
        this.intermediateStations = intermediateStations;
    }
}
