package ru.kpfu.hostel.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

public class RequisitionDto {
    private interface EmailField {
        @Email String getEmail();
    }

    private interface OrganizationName {
        String getOrganizationName();
    }

    public enum Request {
        ;

        @Getter
        public static class CreateRequisition implements EmailField, OrganizationName {
            String email;
            String organizationName;
        }
    }
}
