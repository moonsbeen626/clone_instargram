package moon.clone.instargram.domain.follow;

import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FollowRepositoryTest {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    private User from_user;
    private User to_user;

    @BeforeEach
    public void setUp() {
        from_user = User.builder().email("from@email").name("from").password("asd").phone("asd")
                .profileImgUrl(null).website(null).title(null).build();
        to_user = User.builder().email("to@email").name("to").password("asd").phone("asd")
                .profileImgUrl(null).website(null).title(null).build();
        userRepository.save(from_user);
        userRepository.save(to_user);
    }

    @AfterEach
    public void tearDown() {
        followRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void findFollowByFromUserIdAndToUserId_성공() {
        //given
        Follow follow = new Follow(from_user, to_user);
        followRepository.save(follow);

        //when
        Follow result = followRepository.findFollowByFromUserIdAndToUserId(from_user.getId(), to_user.getId());

        //then
        assertThat(result.getFromUser().getId()).isEqualTo(from_user.getId());
        assertThat(result.getToUser().getId()).isEqualTo(to_user.getId());
    }

    @Test
    public void findFollowerCountById_성공() {
        //given
        Follow follow = new Follow(from_user, to_user);
        followRepository.save(follow);

        //when
        int from_user_follower = followRepository.findFollowerCountById(from_user.getId());
        int to_user_follower = followRepository.findFollowerCountById(to_user.getId());

        //then
        assertThat(from_user_follower).isEqualTo(0);
        assertThat(to_user_follower).isEqualTo(1);
    }

    @Test
    public void findFollowingCountById_성공() {
        //given
        Follow follow = new Follow(from_user, to_user);
        followRepository.save(follow);

        //when
        int from_user_following = followRepository.findFollowingCountById(from_user.getId());
        int to_user_following = followRepository.findFollowingCountById(to_user.getId());

        //then
        assertThat(from_user_following).isEqualTo(1);
        assertThat(to_user_following).isEqualTo(0);
    }

    @Test
    public void follow_성공() {
        //when
        followRepository.follow(from_user.getId(), to_user.getId());

        //then
        List<Follow> result_list = followRepository.findAll();
        assertThat(result_list.size()).isEqualTo(1);
        assertThat(result_list.get(0).getFromUser()).isEqualTo(from_user);
        assertThat(result_list.get(0).getToUser()).isEqualTo(to_user);
    }

    @Test
    public void unFollow_성공() {
        //given
        Follow follow = new Follow(from_user, to_user);
        followRepository.save(follow);

        //when
        followRepository.unFollow(from_user.getId(), to_user.getId());

        //then
        List<Follow> result_list = followRepository.findAll();
        assertThat(result_list.size()).isEqualTo(0);
    }
}
