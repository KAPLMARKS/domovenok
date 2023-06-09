package ru.kpfu.hostel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.hostel.models.Hostel;
import ru.kpfu.hostel.models.Room;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findFirstByHostelAndFloorOrderByName(Hostel hostel, int floor);

    int countByHostel(Hostel hostel);

    @Query("""
            SELECT COALESCE(SUM(r.countPlaces), 0) FROM Room r WHERE r.hostel = :hostel
            """)
    int getCountPlace(@Param("hostel") Hostel hostel);

    @Query("""
        SELECT r FROM Room r where r.hostel = :hostel
    """)
    List<Room> findAllByHostel(@Param("hostel") Hostel hostel);


    List<Room> findAllByHostelAndFloorAndIsMale( Hostel hostel, int floor, Boolean isMale);
}
