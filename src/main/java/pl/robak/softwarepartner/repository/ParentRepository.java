package pl.robak.softwarepartner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.robak.softwarepartner.model.db.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    Parent findById(long id);

}
