package pl.robak.softwarepartner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.robak.softwarepartner.model.db.Attendance;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Attendance findById(long id);

    @Query("SELECT a FROM Attendance a LEFT JOIN FETCH a.child c LEFT JOIN FETCH c.school s WHERE a.entry_date >= :startDate AND a.exit_date <= :endDate and s.id = :schoolId")
    List<Attendance> findAllBetween(@Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate, @Param("schoolId") long schoolId);
}
