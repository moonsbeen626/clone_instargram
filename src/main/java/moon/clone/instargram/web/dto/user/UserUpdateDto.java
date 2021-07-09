package moon.clone.instargram.web.dto.user;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class UserUpdateDto {

    private long id;
    private String password;
    private String phone;
    private String name;
    private String title;
    private String website;
    private String profileImgUrl;
}
