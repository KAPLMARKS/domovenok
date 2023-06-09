package ru.kpfu.hostel.models.student;

import java.io.Serializable;

public enum QualificationType implements Serializable {
    BAKALAVR, MAGISTR;

    public String getQualificationType() {
        return this.name();
    }
}
