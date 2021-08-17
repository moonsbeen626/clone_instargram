package moon.clone.instargram.domain.comment;

import moon.clone.instargram.domain.post.Post;
import moon.clone.instargram.domain.post.PostRepository;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    public void deleteCommentsByPost_성공() throws Exception {
        //given
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user =  User.builder().email("asd@asd").password(encoder.encode("asd1234!")).phone("123123").name("asd").title(null).profileImgUrl(null).build();
        userRepository.save(user);

        Post post = Post.builder().postImgUrl(null).likesCount(0).tag(null).text(null).user(user).build();
        postRepository.save(post);

        commentRepository.save(Comment.builder().post(post).user(user).text("test").build());

        //when
        commentRepository.deleteCommentsByPost(post);

        //then
        assertThat(commentRepository.findAll()).isEmpty();
    }
}
