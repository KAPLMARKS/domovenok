package ru.kpfu.hostel.services;

import ru.kpfu.hostel.dto.PlaceDto;
import ru.kpfu.hostel.models.Place;
import ru.kpfu.hostel.models.Room;
import ru.kpfu.hostel.models.student.Student;

import java.util.List;

public interface PlaceService {
    Place addPlace(PlaceDto.Request.CreatePlace place, Room room);

    List<Place> addPlaces(List<PlaceDto.Request.CreatePlace> places, Room room);

    List<Place> addPlaces(Room room);

    List<Place> addPlaces(List<Room> rooms);

    List<Place> checkEmptyPlaces();

    List<Place> checkEmptyPlacesAndMale(Boolean isMale);

    List<Place> getPlacesByRoom(Room room);

    Place getPlaceByStudent(Student student);
}
