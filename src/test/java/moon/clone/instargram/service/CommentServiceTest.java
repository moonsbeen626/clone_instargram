package moon.clone.instargram.service;

import moon.clone.instargram.domain.comment.CommentRepository;
import moon.clone.instargram.domain.post.PostRepository;
import moon.clone.instargram.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Test
    public void addComment_성공() {
        //given
        //when
        //then
    }

    @Test
    public void addComment_실패() {
        //given
        //when
        //then
    }

    @Test
    public void deleteComment_성공() {
        //given
        //when
        //then
    }
}
