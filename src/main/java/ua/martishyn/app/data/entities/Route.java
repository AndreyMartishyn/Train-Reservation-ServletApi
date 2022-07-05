package ua.martishyn.app.data.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Route implements Serializable {
    private static final long serialVersionUID = -1719747856423655953L;

    private int id;
    private Train train;
    private List<IntermediateStation> intermediateStations = new ArrayList<>();

    public static class IntermediateStation implements Serializable {
        Station station;
        LocalDateTime arrivalDate;
        LocalDateTime departureDate;

        public IntermediateStation(Station station, LocalDateTime arrivalDate, LocalDateTime departureDate) {
            this.station = station;
            this.arrivalDate = arrivalDate;
            this.departureDate = departureDate;
        }

        public Station getStation() {
            return station;
        }

        public LocalDateTime getArrivalDate() {
            return arrivalDate;
        }

        public LocalDateTime getDepartureDate() {
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

    public void addIntermediateStation(Station station, LocalDateTime arrival, LocalDateTime departure) {
        intermediateStations.add(new IntermediateStation(station, arrival, departure));
    }

    public List<IntermediateStation> getIntermediateStations() {
        return intermediateStations;
    }

    public void setIntermediateStations(List<IntermediateStation> intermediateStations) {
        this.intermediateStations = intermediateStations;
    }
}
