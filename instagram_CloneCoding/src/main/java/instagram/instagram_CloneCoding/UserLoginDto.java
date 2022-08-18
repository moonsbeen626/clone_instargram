package instagram.instagram_CloneCoding;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UserLoginDto {
    private String email;
    private String password;
    private String phone;
    private String name;
}
