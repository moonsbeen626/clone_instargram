package moon.clone.instargram.domain.follow;

import moon.clone.instargram.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findFollowByFromUserAndToUser(User fromUser, User toUser);

    @Query(value = "SELECT COUNT(*) FROM follow WHERE to_user_id = :profileId", nativeQuery = true)
    int findFollowerCountById(long profileId);

    @Query(value = "SELECT COUNT(*) FROM follow WHERE from_user_id = :profileId", nativeQuery = true)
    int findFollowingCountById(long profileId);
}
