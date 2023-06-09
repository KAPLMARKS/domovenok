package ru.kpfu.hostel.services;

import ru.kpfu.hostel.dto.HostelDto;
import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public interface HostelService {
    Hostel createHostel(HostelDto.Request.CreateHostel hostel, UserModel userModel);

    List<Hostel> getHostels();

    List<Integer> getHostelFloors(Hostel hostel);
}
