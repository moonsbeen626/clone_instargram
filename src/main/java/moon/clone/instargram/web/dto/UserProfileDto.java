package moon.clone.instargram.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moon.clone.instargram.domain.user.User;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
    private long loginId;
    private boolean loginUser;
    private boolean follow;
    private User user;
}
