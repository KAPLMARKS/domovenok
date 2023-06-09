package ru.kpfu.hostel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.kpfu.hostel.models.claim.Claim;
import ru.kpfu.hostel.models.student.Student;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Place implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @JsonIgnore
    @OneToMany(mappedBy = "place")
    private List<Claim> claims;
}
