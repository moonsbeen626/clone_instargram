package moon.clone.instargram.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moon.clone.instargram.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @JoinColumn(name ="user_id")
    @ManyToOne
    private User user;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @Builder
    public Post(String postImgUrl, String tag, String text, User user) {
        this.postImgUrl = postImgUrl;
        this.tag = tag;
        this.text = text;
        this.user = user;
    }

    public void update(String tag, String text) {
        this.tag = tag;
        this.text = text;
    }
}
