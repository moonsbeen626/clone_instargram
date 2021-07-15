package moon.clone.instargram.domain.post;

import moon.clone.instargram.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostsByUser(User user);
    Post findPostById(long id);
    void deletePostById(long id);
}
