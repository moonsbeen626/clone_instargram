package moon.clone.instargram.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSaveDto {
    private String email;
    private String password;
    private String phone;
    private String name;
}
