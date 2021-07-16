package moon.clone.instargram.domain.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moon.clone.instargram.domain.likes.Likes;
import moon.clone.instargram.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String postImgUrl;
    private String tag;
    private String text;

    @JsonIgnoreProperties({"postList"})
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @JsonIgnoreProperties({"post"})
    @OneToMany(mappedBy = "post")
    private List<Likes> likeList;

    private long likesCount;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @Builder
    public Post(String postImgUrl, String tag, String text, User user, long likesCount) {
        this.postImgUrl = postImgUrl;
        this.tag = tag;
        this.text = text;
        this.user = user;
        this.likesCount = likesCount;
    }

    public void update(String tag, String text) {
        this.tag = tag;
        this.text = text;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }
}
