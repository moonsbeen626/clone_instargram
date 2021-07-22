package moon.clone.instargram.domain.comment;
import moon.clone.instargram.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteCommentsByPost(Post post);
}
