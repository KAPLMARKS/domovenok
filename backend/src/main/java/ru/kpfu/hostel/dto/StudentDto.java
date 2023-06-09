package ru.kpfu.hostel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

public class StudentDto {
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

    private interface Password {
        String getPassword();
    }

    private interface IsMale {
        @NotNull Boolean getIsMale();
    }

    private interface IsNowStatement {
        @NotNull Boolean getIsNowStatement();
    }

    private interface Citizenship {
        @NotNull String getCitizenship();
    }

    private interface StudentCategory {
        @NotNull String getStudentCategory();
    }

    private interface FormEducation {
        @NotNull String getFormEducation();
    }

    private interface QualificationType {
        @NotNull String getQualificationType();
    }

    private interface Course {
        @NotNull int getCourse();
    }

    private interface Faculty {
        @NotNull String getFaculty();
    }

    private interface HasPrefenrential {
        @NotNull Boolean getHasPreferential();
    }

    private interface Age {
        @NotNull int getAge();
    }


    public enum Request {
        ;

        @Getter
        @NoArgsConstructor
        @SuperBuilder
        public static class StudentLoad implements EmailInterface, FirstName, MiddleName, LastName, Password, IsMale, IsNowStatement, Citizenship, StudentCategory, FormEducation, QualificationType, Course, Faculty, Age, HasPrefenrential {
            private String email;
            private String firstName;
            private String middleName;
            private String lastName;
            private String password;
            private Boolean isMale;
            private Boolean isNowStatement;
            private String citizenship;
            private String studentCategory;
            private String formEducation;
            private String qualificationType;
            private int course;
            private String faculty;
            private Boolean hasPreferential;
            private int age;
        }
    }

    public enum Response {
        ;

    }
}
