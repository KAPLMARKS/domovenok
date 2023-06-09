package ru.kpfu.hostel.services;

import ru.kpfu.hostel.models.University;

import java.io.InputStream;
import java.util.List;

public interface ExcelParsingService<T> {
    List<T> parseUsers(InputStream inputStream, University university);
}
