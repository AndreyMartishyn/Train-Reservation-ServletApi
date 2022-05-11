package ua.martishyn.app.data.entities;

import ua.martishyn.app.data.entities.enums.ComfortClass;

import java.io.Serializable;

public class Wagon implements Serializable {
    private int id;
    private ComfortClass comfortClass;
    private int numOfSeats;
    private int priceForSeat;

    public Wagon() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ComfortClass getComfortClass() {
        return comfortClass;
    }

    public void setComfortClass(ComfortClass comfortClass) {
        this.comfortClass = comfortClass;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    public int getPriceForSeat() {
        return priceForSeat;
    }

    public void setPriceForSeat(int priceForSeat) {
        this.priceForSeat = priceForSeat;
    }
}
