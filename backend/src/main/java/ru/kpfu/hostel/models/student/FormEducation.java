package ru.kpfu.hostel.models.student;

import java.io.Serializable;

public enum FormEducation implements Serializable {
    FULL_TIME, EXTRAMURAL, PART_TIME;

    public String getFormEducation() {
        return this.name();
    }
}
