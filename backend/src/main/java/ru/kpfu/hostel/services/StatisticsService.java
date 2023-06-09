package ru.kpfu.hostel.services;

import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.Statistics;
import ru.kpfu.hostel.models.University;

import java.util.List;

public interface StatisticsService {

    List<Statistics> getUniversityStatistics(University university);

    void createHostelStatistics(University university);
}
