package ua.martishyn.app.data.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Train implements Serializable {
    private int id;
    private TrainModel model;
    private List<Wagon> trainCarriages;

    public static Builder builder() {
        return new Builder();
    }

    private static class Builder {
        private Train newTrain;

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

    public TrainModel getModel() {
        return model;
    }

    public void setModel(TrainModel model) {
        this.model = model;
    }

    public List<Wagon> getTrainCarriages() {
        return trainCarriages;
    }

    public void setTrainCarriages(List<Wagon> trainCarriages) {
        this.trainCarriages = trainCarriages;
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
