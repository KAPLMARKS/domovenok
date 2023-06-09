package ru.kpfu.hostel.models.claim;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.Place;
import ru.kpfu.hostel.models.UserModel;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "claims")
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserModel declarer;
    private String message;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Enumerated(EnumType.STRING)
    private AnswerType answerType;
    private int status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id")
    private Place place;
}
