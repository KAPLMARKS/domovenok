package ru.kpfu.hostel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.Place;
import ru.kpfu.hostel.models.Room;
import ru.kpfu.hostel.models.student.Student;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    int countByRoom(Room room);

    int countByRoomIn(Iterable<Room> rooms);

    List<Place> findAllByStudentIsNull();


    List<Place> findAllByStudentIsNullAndRoomIsMale(Boolean isMale);

    List<Place> findAllByRoomAndStudentIsNull(Room room);

    Place findOneByStudent(Student student);

    Integer countAllByRoom_HostelAndStudentIsNull(Hostel hostel);

    Integer countAllByRoom_Hostel(Hostel hostel);
}
