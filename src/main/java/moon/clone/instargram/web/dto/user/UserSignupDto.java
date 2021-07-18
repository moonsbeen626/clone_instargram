package moon.clone.instargram.web.dto.user;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@Setter
@Data
public class UserSignupDto {
    private String email;
    private String password;
    private String phone;
    private String name;
}
