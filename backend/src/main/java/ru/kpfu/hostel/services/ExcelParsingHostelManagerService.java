package ru.kpfu.hostel.services;

import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.HostelManager;
import ru.kpfu.hostel.models.University;

import java.io.InputStream;
import java.util.List;

public interface ExcelParsingHostelManagerService {
    List<HostelManager> parseHostelManager(InputStream inputStream, University university, Hostel hostel);
}
