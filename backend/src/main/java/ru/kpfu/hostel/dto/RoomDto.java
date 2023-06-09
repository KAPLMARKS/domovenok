package ru.kpfu.hostel.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Set;

public class RoomDto {
    private interface Name {
        @NotNull String getName();
    }

    private interface CountPlaces {
        @NotNull int getCountPlaces();
    }

    private interface Floor {
        @NotNull int getFloor();
    }

    private interface IsMale {
        @NotNull Boolean getIsMale();
    }

    private interface Students {
        Set<StudentDto> getStudents();
    }

    private interface Hostel {
        HostelDto getHostel();
    }

    ;

    public enum Request {
        ;

        @Getter
        public static class CreateRoom implements RoomDto.Name, RoomDto.CountPlaces, RoomDto.Floor, RoomDto.IsMale {
            String name;
            int countPlaces;
            int floor;
            Boolean isMale;
        }

        @Getter
        public static class CreateRooms implements RoomDto.CountPlaces, RoomDto.Floor, RoomDto.IsMale {
            int countPlaces;
            int floor;
            Boolean isMale;
        }
    }
}
