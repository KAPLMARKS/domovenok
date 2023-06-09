package ru.kpfu.hostel.services;

import org.springframework.stereotype.Service;
import ru.kpfu.hostel.dto.PlaceDto;
import ru.kpfu.hostel.exception.NotSupportSecondAddingPlaceException;
import ru.kpfu.hostel.models.Place;
import ru.kpfu.hostel.models.Room;
import ru.kpfu.hostel.models.student.Student;
import ru.kpfu.hostel.repositories.PlaceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public Place addPlace(PlaceDto.Request.CreatePlace place, Room room) {
        if (placeRepository.countByRoom(room) + 1 > room.getCountPlaces()) {
            throw new NotSupportSecondAddingPlaceException();
        }
        return placeRepository.save(Place.builder()
                .number(place.getNumber())
                .room(room)
                .build());
    }

    @Override
    public List<Place> addPlaces(List<PlaceDto.Request.CreatePlace> places, Room room) {
        if (placeRepository.countByRoom(room) + places.size() > room.getCountPlaces()) {
            throw new NotSupportSecondAddingPlaceException();
        }
        return placeRepository.saveAll(
                places.stream().map(p -> (Place.builder()
                        .number(p.getNumber())
                        .room(room)
                        .build())).collect(Collectors.toList()));
    }

    @Override
    public List<Place> addPlaces(Room room) {
        if (placeRepository.countByRoom(room) != 0) {
            throw new NotSupportSecondAddingPlaceException();
        }
        List<Place> places = getPlaceWithName(room);
        return placeRepository.saveAll(places);
    }

    @Override
    public List<Place> addPlaces(List<Room> rooms) {
        if (placeRepository.countByRoomIn(rooms) != 0) {
            throw new NotSupportSecondAddingPlaceException();
        }
        List<Place> places = new ArrayList<>();
        for (Room room : rooms) {
            places.addAll(getPlaceWithName(room));
        }
        return placeRepository.saveAll(places);
    }

    @Override
    public List<Place> checkEmptyPlaces() {
        return placeRepository.findAllByStudentIsNull();
    }

    private List<Place> getPlaceWithName(Room room) {
        List<Place> places = new ArrayList<>();
        for (int i = 0; i < room.getCountPlaces(); i++) {
            places.add(Place.builder()
                    .number(String.valueOf(i + 1))
                    .room(room)
                    .build());
        }
        return places;
    }

    @Override
    public List<Place> checkEmptyPlacesAndMale(Boolean isMale) {
        return placeRepository.findAllByStudentIsNullAndRoomIsMale(isMale);
    }

    @Override
    public List<Place> getPlacesByRoom(Room room) {
        return placeRepository.findAllByRoomAndStudentIsNull(room);
    }

    @Override
    public Place getPlaceByStudent(Student student) {
        return placeRepository.findOneByStudent(student);
    }
}
