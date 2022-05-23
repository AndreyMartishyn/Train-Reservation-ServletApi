package ua.martishyn.app.data.entities;

import java.util.Objects;

public class Train implements Entity {
    private int id;
    private TrainModel model;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Train newTrain;

        public Builder() {
            newTrain = new Train();
        }

        public Builder id(int id) {
            newTrain.id = id;
            return this;
        }

        public Builder model(TrainModel model) {
            newTrain.model = model;
            return this;
        }

        public Train build() {
            return newTrain;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setModel(TrainModel model) {
        this.model = model;
    }

    public TrainModel getModel() {
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train train = (Train) o;
        return id == train.id && Objects.equals(model, train.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model);
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", model=" + model +
                '}';
    }
}
