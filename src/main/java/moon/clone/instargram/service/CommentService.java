package moon.clone.instargram.service;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.comment.Comment;
import moon.clone.instargram.domain.comment.CommentRepository;
import moon.clone.instargram.domain.post.Post;
import moon.clone.instargram.domain.post.PostRepository;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Comment addComment(String text, long postId, long sessionId) {
        Post post = postRepository.findPostById(postId);
        User user = userRepository.findUserById(sessionId);
        Comment comment = Comment.builder()
                .text(text)
                .post(post)
                .user(user)
                .build();
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }
}
