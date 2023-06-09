package ru.kpfu.hostel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.kpfu.hostel.models.claim.Type;

public enum BallsDto {
    ;

    private interface Budget {
        int getBudget();
    }

    private interface Lgots {
        int getLgots();
    }


    public enum Request {
        ;

        @Getter
        @NoArgsConstructor
        @SuperBuilder
        public static class BallsData implements BallsDto.Budget, BallsDto.Lgots {
            private int budget;
            private int lgots;
        }
    }

    public enum Response {
        ;

    }
}
