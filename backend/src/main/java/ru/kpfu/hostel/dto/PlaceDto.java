package ru.kpfu.hostel.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class PlaceDto {
    private interface Number {
        @NotNull String getNumber();
    }

    private interface Room {
        @NotNull RoomDto getRoom();
    }

    private interface Student {
        @NotNull StudentDto getStudent();
    }

    public enum Request {
        ;

        @Getter
        public static class CreatePlace implements PlaceDto.Number {
            String number;
        }
    }
}
