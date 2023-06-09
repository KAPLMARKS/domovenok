package ru.kpfu.hostel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HostelManager extends Employee implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hostel_id")
    @JsonIgnore
    private Hostel hostel;
}
