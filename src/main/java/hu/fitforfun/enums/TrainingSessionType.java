package hu.fitforfun.enums;

public enum TrainingSessionType {
    GROUP_TRAINING("Csoportos edzés"),
    PERSONAL_TRAINING("Személyi edzés");

    private String name;
    TrainingSessionType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    }
