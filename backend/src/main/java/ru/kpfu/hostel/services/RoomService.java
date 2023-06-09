package ru.kpfu.hostel.services;

import ru.kpfu.hostel.dto.RoomDto;
import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.Room;

import java.util.List;

public interface RoomService {
    Room createRoom(RoomDto.Request.CreateRoom room, Hostel hostel);

    List<Room> createRooms(RoomDto.Request.CreateRooms room, Hostel hostel, int count);

    List<Room> getRoomByHostel(Hostel hostel);

    List<Room> getRoomByHostelAndFloorAndMale(Hostel hostel, int floor, Boolean isMale);
}
