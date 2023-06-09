package ru.kpfu.hostel.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import ru.kpfu.hostel.models.Place;
import ru.kpfu.hostel.models.claim.Type;

public enum ClaimDto {
    ;

    public enum Response {
        ;

        private interface Message {
           String getMessage();
        }

        private interface TypeInterface {
            @NotNull Type getType();
        }

        private interface AnswerType {
            @NotNull AnswerType getAnswerType();
        }

        private interface ClaimStatus {
            int getStatus();
        }

        @Getter
        public static class CreateClaim implements  Message, TypeInterface{
            private String message;
            private Type type;
        }

        @Getter
        public static class EditClaim implements AnswerType, ClaimStatus {
            private AnswerType answerType;
            private int status;
        }
    }
}
