package ru.kpfu.hostel.models.student;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.kpfu.hostel.models.Place;
import ru.kpfu.hostel.models.Room;
import ru.kpfu.hostel.models.UserModel;

import java.io.Serializable;

@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student extends UserModel implements Serializable {
    @Column(nullable = false)
    private boolean isMale;
    //галочка человек нуждается в общежитии или нет
    @Column(nullable = false)
    private boolean isNowStatement;
    //гражданство
    @Column(nullable = false)
    private String citizenship;
    //контракт, бюджет, платник
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudentCategory studentCategory;
    //очная, заочная, очно-заочная
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormEducation formEducation;
    //бакалавр, магистр
    @Enumerated(EnumType.STRING)
    private QualificationType qualificationType;
    @Column(nullable = true)
    private int course;
    @Column(nullable = false)
    private String faculty;
    @Column(nullable = false)
    private int age;
    //льготы
    @Column(nullable = false)
    private boolean hasPreferential;
    private int rating;
    private int activityBalls;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @OneToOne
    @JoinColumn(name = "place_id")
    private Place place;
    private String spravka;
    private String privivki;
    private String medPolis;
}

