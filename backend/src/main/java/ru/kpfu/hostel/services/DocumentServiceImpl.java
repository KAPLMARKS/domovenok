package ru.kpfu.hostel.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.hostel.models.student.Student;
import ru.kpfu.hostel.repositories.StudentRepository;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final StudentRepository studentRepository;

    @Override
    public Student addSpravka(Student student, String docUrl) {
        student.setSpravka(docUrl);
        return studentRepository.save(student);
    }

    @Override
    public Student addPrivivki(Student student, String docUrl) {
        student.setPrivivki(docUrl);
        return studentRepository.save(student);
    }

    @Override
    public Student addMedPolis(Student student, String docUrl) {
        student.setMedPolis(docUrl);
        return studentRepository.save(student);
    }
}
