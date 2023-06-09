package ru.kpfu.hostel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    private int countPlaces;
    private int floor;
    private boolean isMale;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hostel_id")
    private Hostel hostel;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Set<Place> places;

}
