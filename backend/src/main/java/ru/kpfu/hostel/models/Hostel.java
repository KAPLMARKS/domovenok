package ru.kpfu.hostel.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.kpfu.hostel.models.claim.Claim;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
// общежитие
public class Hostel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int countFloors;
    @Column(nullable = false)
    private int countRooms;
    @Column(nullable = false)
    private int countPlaces;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "university_id")
    private University university;

    @OneToMany(mappedBy = "hostel")
    private List<HostelManager> manager;

}
