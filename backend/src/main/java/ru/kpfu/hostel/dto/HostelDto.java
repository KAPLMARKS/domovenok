package ru.kpfu.hostel.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class HostelDto {

    private interface Name {
        @NotNull String getName();
    }

    private interface CountFloors {
        @NotNull int getCountFloors();
    }

    private interface CountRooms {
        @NotNull int getCountRooms();
    }

    private interface CountPlaces {
        @NotNull int getCountPlaces();
    }

    public enum Request {
        ;

        @Getter
        public static class CreateHostel implements HostelDto.Name, HostelDto.CountFloors, HostelDto.CountRooms, HostelDto.CountPlaces {
            String name;
            int countFloors;
            int countRooms;
            int countPlaces;
        }
    }
}
