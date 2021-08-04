package moon.clone.instargram.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moon.clone.instargram.domain.post.Post;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phone;

    @Column(length = 30, nullable = false)
    private String name;

    private String title;
    private String website;

    private String profileImgUrl;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"})
    private List<Post> postList;

    @Builder
    public User(String email, String password, String phone, String name, String title, String website, String profileImgUrl) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.title = title;
        this.website = website;
        this.profileImgUrl = profileImgUrl;
    }

    public void update(String password, String phone, String name, String title, String website) {
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.title = title;
        this.website = website;
    }

    public void updateProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }
}
