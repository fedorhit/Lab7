package SpringBootMVCREST.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import SpringBootMVCREST.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
