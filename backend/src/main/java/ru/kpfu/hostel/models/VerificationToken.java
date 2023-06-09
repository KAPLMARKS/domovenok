package ru.kpfu.hostel.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    @OneToOne(targetEntity = Requisition.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "requisition_id")
    private Requisition requisition;

    private Date expiryData;
}
