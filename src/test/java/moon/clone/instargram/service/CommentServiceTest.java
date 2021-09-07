package moon.clone.instargram.service;

import moon.clone.instargram.domain.comment.Comment;
import moon.clone.instargram.domain.comment.CommentRepository;
import moon.clone.instargram.domain.post.Post;
import moon.clone.instargram.domain.post.PostRepository;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import moon.clone.instargram.handler.ex.CustomApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

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

    private User user;
    private Post post;

    @BeforeEach
    public void setUp() {
        user = User.builder().email("test@test").password("asd1234!").phone("123123").name("test")
                            .website(null).title(null).profileImgUrl(null).build();
        post = Post.builder().user(user).likesCount(0).tag("test,post").text("test").postImgUrl("test.jpg").build();
    }

    @Test
    public void addComment_성공() {
        //given
        Comment comment = Comment.builder().user(user).post(post).text("test").build();
        given(postRepository.findById(anyLong())).willReturn(java.util.Optional.ofNullable(post));
        given(userRepository.findById(anyLong())).willReturn(java.util.Optional.ofNullable(user));
        given(commentRepository.save(any())).willReturn(comment);

        //when
        Comment result = commentService.addComment("test", post.getId(), user.getId());

        //then
        assertThat(result.getPost().getId()).isEqualTo(post.getId());
        assertThat(result.getUser().getId()).isEqualTo(user.getId());
        assertThat(result.getText()).isEqualTo("test");
    }

    @Test
    public void addComment_실패() throws Exception {
        //given
        given(postRepository.findById(anyLong())).willReturn(java.util.Optional.ofNullable(post));
        given(userRepository.findById(anyLong())).willReturn(java.util.Optional.ofNullable(null));

        //when
        assertThrows(CustomApiException.class, () -> {
            Comment result = commentService.addComment("test", post.getId(), user.getId());
        });
    }
}
