package ru.kpfu.hostel.models.student;

import java.io.Serializable;

public enum StudentCategory implements Serializable {
    BUDGET, PAID, CONTRACT;

    public String getStudentCategory() {
        return this.name();
    }
}
