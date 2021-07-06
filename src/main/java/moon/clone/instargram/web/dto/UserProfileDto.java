package moon.clone.instargram.web.dto;

import lombok.*;
import moon.clone.instargram.domain.user.User;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class UserProfileDto {
    private long loginId;
    private boolean loginUser;
    private boolean follow;
    private User user;
}
