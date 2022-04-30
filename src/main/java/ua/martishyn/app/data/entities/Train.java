package ua.martishyn.app.data.entities;

import java.io.Serializable;
import java.util.Objects;

public class Train implements Serializable {
    private int id;
    private Model model;

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

        public Builder model(Model model) {
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

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
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
