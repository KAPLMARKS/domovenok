package ru.kpfu.hostel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.kpfu.hostel.models.Roles;

public enum EmployeeDto {
    ;

    private interface EmailInterface {
        @Size(max = 250) @Email String getEmail();
    }

    private interface FirstName {
        @Size(min = 1, max = 250) String getFirstName();
    }

    private interface MiddleName {
        @Size(max = 250) String getMiddleName();
    }

    private interface LastName {
        @Size(min = 1, max = 250) String getLastName();
    }

    private interface UniversityId {
        long getUniversityId();
    }

    private interface Dolgnost {
        @Size(min = 10, max = 250) String getDolgnost();
    }

    private interface Password {
        String getPassword();
    }

    private interface Role {
        Roles getRoles();
    }

    public enum Request {
        ;

        @Getter
        @NoArgsConstructor
        @SuperBuilder
        public static class EmployeeLoad implements EmailInterface, FirstName, MiddleName, LastName, Dolgnost, Password, Role {
            private String email;
            private String firstName;
            private String middleName;
            private String lastName;
            private String dolgnost;
            private String password;
            private Roles roles;
        }
    }

    public enum Response {
        ;

    }
}
