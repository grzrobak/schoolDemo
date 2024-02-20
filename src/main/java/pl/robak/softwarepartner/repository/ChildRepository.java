package pl.robak.softwarepartner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.robak.softwarepartner.model.db.Child;
import pl.robak.softwarepartner.model.db.School;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    Child findById(long id);

}
