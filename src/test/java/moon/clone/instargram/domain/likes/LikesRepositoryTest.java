package moon.clone.instargram.domain.likes;

import moon.clone.instargram.domain.post.Post;
import moon.clone.instargram.domain.post.PostRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LikesRepositoryTest {

    @Autowired
    LikesRepository likesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    private User user;
    private Post post;

    @BeforeEach
    public void setUp() {
        user = User.builder().email("test@test").password("asd1234!").name("test").phone("123123")
                                .profileImgUrl(null).title(null).website(null).build();
        post = Post.builder().user(user).postImgUrl("test.jpg").text("test").tag("post,test")
                                .likesCount(0).build();
        userRepository.save(user);
        postRepository.save(post);
    }

    @AfterEach
    public void tearDown() {
        likesRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    public void deleteLikesByPost_성공() {
        //given
        Likes likes = Likes.builder().user(user).post(post).build();
        likesRepository.save(likes);

        //when
        likesRepository.deleteLikesByPost(post);

        //then
        assertThat(likesRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void likes_성공() {
        //when
        likesRepository.likes(post.getId(), user.getId());

        //then
        assertThat(likesRepository.findAll().size()).isEqualTo(1);
        assertThat(likesRepository.findAll().get(0).getPost().getId()).isEqualTo(post.getId());
        assertThat(likesRepository.findAll().get(0).getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    public void unLikes_성공() {
        //given
        Likes likes = Likes.builder().post(post).user(user).build();
        likesRepository.save(likes);

        //when
        likesRepository.unLikes(post.getId(), user.getId());

        //then
        assertThat(likesRepository.findAll().size()).isEqualTo(0);
    }
}
