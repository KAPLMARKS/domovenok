package ru.kpfu.hostel.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.hostel.dto.HostelDto;
import ru.kpfu.hostel.dto.PlaceDto;
import ru.kpfu.hostel.dto.RoomDto;
import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.Place;
import ru.kpfu.hostel.models.Room;
import ru.kpfu.hostel.models.UserModel;
import ru.kpfu.hostel.models.student.Student;
import ru.kpfu.hostel.repositories.HostelRepository;
import ru.kpfu.hostel.repositories.RoomRepository;
import ru.kpfu.hostel.repositories.UserModelRepository;
import ru.kpfu.hostel.services.HostelService;
import ru.kpfu.hostel.services.PlaceService;
import ru.kpfu.hostel.services.RoomService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hostel")
@SecurityRequirement(name = "bearerAuth")
public class HostelController {

    private final HostelService hostelService;
    private final RoomService roomService;

    private final PlaceService placeService;

    private final HostelRepository hostelRepository;
    private final UserModelRepository userModelRepository;

    private final RoomRepository roomRepository;

    @Autowired
    public HostelController(HostelService hostelService, RoomService roomService, PlaceService placeService, HostelRepository hostelRepository, UserModelRepository userModelRepository, RoomRepository roomRepository) {
        this.hostelService = hostelService;
        this.roomService = roomService;
        this.placeService = placeService;
        this.hostelRepository = hostelRepository;
        this.userModelRepository = userModelRepository;
        this.roomRepository = roomRepository;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('STUD_GORODOK')")
    public ResponseEntity<Hostel> createHostel(@AuthenticationPrincipal UserModel user, @RequestBody HostelDto.Request.CreateHostel hostel) {
        return ResponseEntity.ok(hostelService.createHostel(hostel, user));
    }

    @PostMapping("/{hostelId}/create-room")
    @PreAuthorize("hasAuthority('STUD_GORODOK')")
    public ResponseEntity<Room> createRoom(@PathVariable Long hostelId, @RequestBody RoomDto.Request.CreateRoom room) {
        Hostel hostel = hostelRepository.findById(hostelId).orElseThrow();
        return ResponseEntity.ok(roomService.createRoom(room, hostel));
    }

    @PostMapping("/{hostelId}/create-rooms")
    @PreAuthorize("hasAuthority('STUD_GORODOK')")
    public ResponseEntity<List<Room>> createRooms(@PathVariable Long hostelId,
                                                  @RequestBody RoomDto.Request.CreateRooms room,
                                                  @RequestParam(name = "count") int count) {
        Hostel hostel = hostelRepository.findById(hostelId).orElseThrow();
        return ResponseEntity.ok(roomService.createRooms(room, hostel, count));
    }

    @PostMapping("/{hostelId}/room/{roomId}/add-place")
    @PreAuthorize("hasAuthority('STUD_GORODOK')")
    public ResponseEntity<Place> createPlace(@PathVariable Long hostelId, @PathVariable Long roomId, @RequestBody PlaceDto.Request.CreatePlace place) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        return ResponseEntity.ok(placeService.addPlace(place, room));
    }

    @PostMapping("/{hostelId}/room/{roomId}/add-places")
    @PreAuthorize("hasAuthority('STUD_GORODOK')")
    public ResponseEntity<List<Place>> createPlaces(@PathVariable Long hostelId, @PathVariable Long roomId, @RequestBody List<PlaceDto.Request.CreatePlace> places) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        return ResponseEntity.ok(placeService.addPlaces(places, room));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('STUD_GORODOK')")
    public ResponseEntity<List<Hostel>> getHostels() {
        return ResponseEntity.ok(hostelService.getHostels());
    }

    @GetMapping("/{hostelId}/all")
    @PreAuthorize("hasAuthority('STUD_GORODOK')")
    public ResponseEntity<List<Room>> getRoomsByHostel(@PathVariable Long hostelId) {
        Hostel hostel = hostelRepository.findById(hostelId).orElseThrow();
        return ResponseEntity.ok(roomService.getRoomByHostel(hostel));
    }

    @GetMapping("/checkEmptyPlaces")
    @PreAuthorize("hasAuthority('STUD_GORODOK')")
    public ResponseEntity<List<Place>> getEmptyPlaces() {
        return ResponseEntity.ok(placeService.checkEmptyPlaces());
    }

    @GetMapping("/checkEmptyPlacesByMale")
    @PreAuthorize("hasAuthority('STUD_GORODOK')")
    public ResponseEntity<List<Place>> getEmptyPlacesByMale(Student student) {
        return ResponseEntity.ok(placeService.checkEmptyPlacesAndMale(student.isMale()));
    }

    @GetMapping("/{hostelId}/countFloor")
    @PreAuthorize(("hasAuthority('STUDENT') || hasAuthority('STUD_GORODOK')"))
    public ResponseEntity<List<Integer>> getArrayFloor(@PathVariable Long hostelId) {
        Hostel hostel = hostelRepository.findById(hostelId).orElseThrow();
        return ResponseEntity.ok(hostelService.getHostelFloors(hostel));
    }
    @GetMapping("/{hostelId}/floor")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<List<Room>> getRoomsByHostelAndFloorAndMale(@PathVariable Long hostelId, @RequestParam int floor, @AuthenticationPrincipal Student student) {
        Hostel hostel = hostelRepository.findById(hostelId).orElseThrow();
        return ResponseEntity.ok(roomService.getRoomByHostelAndFloorAndMale(hostel, floor, student.isMale()));
    }

    @GetMapping("/{roomId}/place")
    @PreAuthorize("hasAuthority('STUDENT') || hasAuthority('STUD_GORODOK')")
    public ResponseEntity<List<Place>> getPlaces(@PathVariable Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        return ResponseEntity.ok(placeService.getPlacesByRoom(room));
    }
}

