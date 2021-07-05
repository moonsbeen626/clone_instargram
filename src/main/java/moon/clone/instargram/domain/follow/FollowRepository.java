package moon.clone.instargram.domain.follow;

import moon.clone.instargram.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findFollowByFromUserAndToUser(User fromUser, User toUser);
}
