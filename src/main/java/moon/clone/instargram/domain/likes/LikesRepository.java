package moon.clone.instargram.domain.likes;

import moon.clone.instargram.domain.post.Post;
import moon.clone.instargram.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    void deleteLikesByPost(Post post);

    @Modifying
    @Query(value = "INSERT INTO likes(post_id, user_id) VALUES(:postId, :userId)", nativeQuery = true)
    void likes(long postId, long userId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE post_id = :postId AND user_id = :userId", nativeQuery = true)
    void unLikes(long postId, long userId);
}
