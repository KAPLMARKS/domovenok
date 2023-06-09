package ru.kpfu.hostel.services;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.hostel.dto.RoomDto;
import ru.kpfu.hostel.exception.NotFreeSpaceForPlaceException;
import ru.kpfu.hostel.exception.NotFreeSpaceForRoomException;
import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.Room;
import ru.kpfu.hostel.repositories.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final PlaceService placeService;

    public RoomServiceImpl(RoomRepository roomRepository, PlaceService placeService) {
        this.roomRepository = roomRepository;
        this.placeService = placeService;
    }

    @Override
    public List<Room> getRoomByHostel(Hostel hostel) {
        return roomRepository.findAllByHostel(hostel);
    }

    @Override
    public List<Room> getRoomByHostelAndFloorAndMale(Hostel hostel, int floor, Boolean isMale) {
        return roomRepository.findAllByHostelAndFloorAndIsMale(hostel, floor, isMale);
    }

    @Override
    public Room createRoom(RoomDto.Request.CreateRoom roomDto, Hostel hostel) {
        if (1 + getCountRoom(hostel) > hostel.getCountRooms()) {
            throw new NotFreeSpaceForRoomException();
        }
        if (roomRepository.getCountPlace(hostel) + roomDto.getCountPlaces() > hostel.getCountPlaces()) {
            throw new NotFreeSpaceForPlaceException();
        }
        Room room = roomRepository.save(Room.builder()
                .name(roomDto.getName())
                .countPlaces(roomDto.getCountPlaces())
                .floor(roomDto.getFloor())
                .isMale(roomDto.getIsMale())
                .hostel(hostel)
                .build());
        placeService.addPlaces(room);
        return room;
    }

    @Override
    public List<Room> createRooms(RoomDto.Request.CreateRooms room, Hostel hostel, int count) {
        if (count + getCountRoom(hostel) > hostel.getCountRooms()) {
            throw new NotFreeSpaceForRoomException();
        }
        if (roomRepository.getCountPlace(hostel) + room.getCountPlaces() * count > hostel.getCountPlaces()) {
            throw new NotFreeSpaceForPlaceException();
        }
        List<Room> rooms = generateRoomsWithName(room, hostel, count);
        roomRepository.saveAll(rooms);
        placeService.addPlaces(rooms);
        return rooms;
    }


    private List<Room> generateRoomsWithName(RoomDto.Request.CreateRooms roomDto, Hostel hostel, int count) {
        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Room room = Room.builder()
                    .countPlaces(roomDto.getCountPlaces())
                    .floor(roomDto.getFloor())
                    .isMale(roomDto.getIsMale())
                    .hostel(hostel)
                    .name(roomDto.getFloor() + "-" + getNextName(i, rooms, roomDto.getFloor(), hostel))
                    .build();
            rooms.add(room);
        }
        return rooms;
    }

    private int getCountRoom(Hostel hostel) {
        return roomRepository.countByHostel(hostel);
    }

    private String getNextName(int roomIndex, List<Room> rooms, int floor, Hostel hostel) {
        Optional<Room> lastRoom = roomRepository.findFirstByHostelAndFloorOrderByName(hostel, floor);
        if (roomIndex <= 0) {
            return lastRoom.isEmpty() ? "1"  : generateNewName(lastRoom.get().getName());
        }
        return generateNewName(rooms.get(roomIndex - 1).getName());
    }

    private String generateNewName(@Length(min = 1, max = 100) String oldName) {
       String[] parts  = oldName.split("-");
       int num = Integer.parseInt(parts[1]) + 1;
       return String.valueOf(num);
    }

}
