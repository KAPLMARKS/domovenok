package ru.kpfu.hostel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.hostel.dto.HostelDto;
import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.UserModel;
import ru.kpfu.hostel.repositories.HostelRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class HostelServiceImpl implements HostelService {

    private final HostelRepository hostelRepository;
    private final UniversityService universityService;

    @Autowired
    public HostelServiceImpl(HostelRepository hostelRepository, UniversityService universityService) {
        this.hostelRepository = hostelRepository;
        this.universityService = universityService;
    }

    @Override
    public Hostel createHostel(HostelDto.Request.CreateHostel hostel, UserModel user) {
        return hostelRepository.save(Hostel.builder()
                .name(hostel.getName())
                .countFloors(hostel.getCountFloors())
                .countRooms(hostel.getCountRooms())
                .countPlaces(hostel.getCountPlaces())
                .university(user.getUniversity())
                .build());
    }

    @Override
    public List<Hostel> getHostels() {
        return hostelRepository.findAll();
    }

    @Override
    public List<Integer> getHostelFloors(Hostel hostel) {
        List<Integer> floors = new ArrayList<>();
        int countFloors = hostel.getCountFloors();
        for (int i = 1; i <= countFloors; i++) {
            floors.add(i);
        }
        return floors;
    }


}
