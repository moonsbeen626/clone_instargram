package moon.clone.instargram.domain.likes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moon.clone.instargram.domain.post.Post;
import moon.clone.instargram.domain.user.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="likes_uk",
                        columnNames = {"post_id", "user_id"}
                )
        }
)
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "post_id")
    @ManyToOne
    private Post post;

    @JsonIgnoreProperties({"postList"}) //post -> user -> likesList -> user -> postList 무한 참조 막기 위함
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Builder
    public Likes(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}
