package moon.clone.instargram.web.dto.user;

import lombok.*;

@Builder
@Getter
@Setter
@Data
public class UserLoginDto {
    private String email;
    private String password;
    private String phone;
    private String name;
}
