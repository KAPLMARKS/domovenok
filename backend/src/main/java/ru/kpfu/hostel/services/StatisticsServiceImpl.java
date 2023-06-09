package ru.kpfu.hostel.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.Statistics;
import ru.kpfu.hostel.models.University;
import ru.kpfu.hostel.repositories.HostelRepository;
import ru.kpfu.hostel.repositories.PlaceRepository;
import ru.kpfu.hostel.repositories.StatisticsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService{

    private final StatisticsRepository statisticsRepository;
    private final HostelRepository hostelRepository;
    private final PlaceRepository placeRepository;

    @Override
    public List<Statistics> getUniversityStatistics(University university) {
        createHostelStatistics(university);
        return statisticsRepository.findAllByHostel_University(university);
    }

    @Override
    public void createHostelStatistics(University university) {
        List<Hostel> hostels = hostelRepository.getAllByUniversity(university);
        for (Hostel hostel: hostels) {
            Statistics existingStats = statisticsRepository.findAllByHostel(hostel);
            if(existingStats != null) {
                existingStats.setFreePlaces(placeRepository.countAllByRoom_HostelAndStudentIsNull(hostel));
                statisticsRepository.save(existingStats);
            } else {
                Statistics statistics = new Statistics();
                statistics.setHostel(hostel);
                statistics.setFreePlaces(placeRepository.countAllByRoom_HostelAndStudentIsNull(hostel));
                statistics.setAllPlaces(placeRepository.countAllByRoom_Hostel(hostel));
                statisticsRepository.save(statistics);
            }
        }
    }
}
