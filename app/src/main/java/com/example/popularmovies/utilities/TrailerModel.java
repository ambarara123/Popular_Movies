package com.example.popularmovies.utilities;

public class TrailerModel {
    private String value;
    private String trailerName;

    public TrailerModel() {
        this.value = "";
        this.trailerName = "";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }
}
