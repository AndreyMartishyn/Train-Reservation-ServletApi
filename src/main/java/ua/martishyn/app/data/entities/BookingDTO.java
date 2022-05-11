package ua.martishyn.app.data.entities;

import java.io.Serializable;
import java.util.List;

public class BookingDTO implements Serializable {
    private int trainId;
    private String departureStation;
    private String arrivalStation;
    private String departureTime;
    private String arrivalTime;
    private String comfortClass;
    private List<Integer> coachesNumbers;

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getComfortClass() {
        return comfortClass;
    }

    public void setComfortClass(String comfortClass) {
        this.comfortClass = comfortClass;
    }


    public List<Integer> getCoachesNumbers() {
        return coachesNumbers;
    }

    public void setCoachesNumbers(List<Integer> coaches) {
        this.coachesNumbers = coaches;
    }
}
