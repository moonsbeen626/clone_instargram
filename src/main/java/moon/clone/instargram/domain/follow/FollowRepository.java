package moon.clone.instargram.domain.follow;

import moon.clone.instargram.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    //Follow findFollowByFromUserAndToUser(User fromUser, User toUser);
    Follow findFollowByFromUserIdAndToUserId(long from_user_id, long to_user_id);

    @Query(value = "SELECT COUNT(*) FROM follow WHERE to_user_id = :profileId", nativeQuery = true)
    int findFollowerCountById(long profileId);

    @Query(value = "SELECT COUNT(*) FROM follow WHERE from_user_id = :profileId", nativeQuery = true)
    int findFollowingCountById(long profileId);

    @Modifying
    @Query(value = "INSERT INTO follow(from_user_id, to_user_id) VALUES(:fromId, :toId)", nativeQuery = true)
    void follow(long fromId, long toId);

    @Modifying
    @Query(value = "DELETE FROM follow WHERE from_user_id = :fromId AND to_user_id = :toId", nativeQuery = true)
    void unFollow(long fromId, long toId);
}
