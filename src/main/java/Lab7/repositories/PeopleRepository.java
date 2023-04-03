package Lab7.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Lab7.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
