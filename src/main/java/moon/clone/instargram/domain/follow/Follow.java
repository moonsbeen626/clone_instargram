package moon.clone.instargram.domain.follow;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moon.clone.instargram.domain.user.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="subscribe_uk",
                        columnNames = {"from_user_id", "to_user_id"}
                )
        }
)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "from_user_id")
    @ManyToOne
    private User fromUser;

    @JoinColumn(name = "to_user_id")
    @ManyToOne
    private User toUser;

    @Builder
    public Follow(User fromUser, User toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
