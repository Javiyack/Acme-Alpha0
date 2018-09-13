package repositories;

import domain.Folder;
import domain.Hike;
import domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface HikeRepository extends JpaRepository<Hike, Integer> {

	@Query("select h from Hike h where h.route.id=?1")
	Collection<Hike> findByRouteId(Integer routeId);

	@Query("select h from Hike h where h.name like %?1% " +
			"or h.description like %?1% or h.origin like %?1% or h.destination like %?1% " +
			"or h.difficulty like %?1%")
	Collection<Hike> findByKeyWord(String keyWord);

	@Query("select h from Hike h where h.difficulty like %?1%")
	Collection<Hike> findByDifficultyKeyWord(String keyWord);

	@Query("select h from Hike h where h.name like %?1% " +
			"or h.description like %?1% or h.origin like %?1% or h.destination like %?1% ")
	Collection<Hike> findByIndexedKeyWord(String keyWord);

	@Query("select h from Hike h" +
			" where h.route.user.id=?1")
	Collection<Hike> findByUserId(int id);
}
