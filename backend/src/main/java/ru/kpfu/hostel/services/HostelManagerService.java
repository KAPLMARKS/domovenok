package ru.kpfu.hostel.services;

import ru.kpfu.hostel.dto.EmployeeDto;
import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.HostelManager;
import ru.kpfu.hostel.models.University;

import java.io.InputStream;
import java.util.List;

public interface HostelManagerService {

    HostelManager loadHostelManager(EmployeeDto.Request.EmployeeLoad hostelManager, University university, Hostel hostel);

    List<HostelManager> loadHostelManagerExcel(InputStream inputStream, University university, Hostel hostel);
}
