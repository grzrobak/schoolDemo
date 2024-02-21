package pl.robak.softwarepartner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.robak.softwarepartner.model.db.Child;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    Child findById(long id);

}
