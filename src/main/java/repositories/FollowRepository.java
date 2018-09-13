
package repositories;

import domain.Follow;
import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    @Query("select f from Follow f where f.follower.id=?1 and f.followed=?2")
    Follow find(int follower, int followed);

    @Query("select f from Follow f where f.followed=?1")
    Collection<Follow> findByFollowed(int id);
    @Query("select f from Follow f where f.follower=?1")
    Collection<Follow> findByFollower(int id);
}
