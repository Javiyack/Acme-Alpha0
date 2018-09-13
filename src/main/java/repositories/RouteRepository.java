package repositories;

import domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {


	@Query("select r from Route r where r.user.id=?1")
	Collection<Route> findByOwner(Integer ownerId);


	@Query("select r from Route r where r.description like %?1%")
	Collection<Route> findByKeyWord(String keyWord);

	@Query("select r from Route r where r.user.id=?1")
	Collection<Route> findByUserId(int id);
}
