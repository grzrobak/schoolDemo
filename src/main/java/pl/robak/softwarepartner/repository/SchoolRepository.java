package pl.robak.softwarepartner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.robak.softwarepartner.model.db.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    School findById(long id);
}
