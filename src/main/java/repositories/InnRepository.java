package repositories;

import domain.Inn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InnRepository extends JpaRepository<Inn, Integer> {

}
