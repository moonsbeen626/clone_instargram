package moon.clone.instargram.domain.post;

import moon.clone.instargram.domain.follow.Follow;
import moon.clone.instargram.domain.follow.FollowRepository;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    private User from_user;
    private User to_user;
    private Post post1;
    private Post post2;
    private Post post3;
    private Post post4;

    @BeforeEach
    public void setUp() {
        from_user = User.builder().name("from").password("asd1234!").email("test1@test").phone("1234")
                .website(null).title(null).profileImgUrl(null).build();
        to_user = User.builder().name("to").password("asd1234!").email("test2@test").phone("1234")
                .website(null).title(null).profileImgUrl(null).build();
        post1 = Post.builder().text("post1").tag("post,1,post1").postImgUrl("post1.jpg").user(to_user)
                .likesCount(0).build();
        post2 = Post.builder().text("post2").tag("post,2,post2").postImgUrl("post2.jpg").user(to_user)
                .likesCount(0).build();
        post3 = Post.builder().text("post3").tag("post,3,post3").postImgUrl("post3.jpg").user(to_user)
                .likesCount(0).build();
        post4 = Post.builder().text("post4").tag("post,4,post4").postImgUrl("post4.jpg").user(to_user)
                .likesCount(0).build();
    }

    @AfterEach
    public void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void mainStory_성공() {
        //given
        Pageable pageable = Pageable.ofSize(3);
        userRepository.save(from_user);
        userRepository.save(to_user);
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);

        Follow follow = new Follow(from_user, to_user);
        followRepository.save(follow);

        //when
        Page<Post> result_page = postRepository.mainStory(from_user.getId(), pageable);

        //then
        assertThat(result_page.getTotalElements()).isEqualTo(4);
        assertThat(result_page.getTotalPages()).isEqualTo(2);
        assertThat(result_page.toList().get(0).getId()).isEqualTo(4); //id 기준 desc
        assertThat(result_page.toList().get(1).getId()).isEqualTo(3);
    }

    @Test
    public void searchResult_성공() {
        //given
        Pageable pageable = Pageable.ofSize(12);
        userRepository.save(from_user);
        userRepository.save(to_user);
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);

        //when
        Page<Post> result_by_post = postRepository.searchResult("post", pageable);
        Page<Post> result_by_1 = postRepository.searchResult("1", pageable);
        Page<Post> result_by_x = postRepository.searchResult("x", pageable);

        //then
        assertThat(result_by_post.getTotalElements()).isEqualTo(4);
        assertThat(result_by_post.getTotalPages()).isEqualTo(1);
        assertThat(result_by_post.toList().get(0).getId()).isEqualTo(4);

        assertThat(result_by_1.getTotalElements()).isEqualTo(1);
        assertThat(result_by_1.getTotalPages()).isEqualTo(1);
        assertThat(result_by_1.toList().get(0).getId()).isEqualTo(1);

        assertThat(result_by_x.getTotalElements()).isEqualTo(0);
        assertThat(result_by_x.getTotalPages()).isEqualTo(0);
    }
}
