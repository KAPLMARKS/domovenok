package ru.kpfu.hostel.services;

import ru.kpfu.hostel.models.student.Student;

public interface DocumentService {

    Student addSpravka(Student student,String docUrl);
    Student addPrivivki(Student student, String docUrl);
    Student addMedPolis(Student student, String docUrl);
}
