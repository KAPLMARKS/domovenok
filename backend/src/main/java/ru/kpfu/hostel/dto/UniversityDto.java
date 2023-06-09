package ru.kpfu.hostel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public enum UniversityDto {
    ;

    private interface UniversityKey {
        String getKey();
    }

    public enum Response {
        ;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SecretKey implements UniversityKey {
            private String key;
        }
    }

    public enum Request {
        ;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SecretKey implements UniversityKey {
            private String key;
        }
    }
}
