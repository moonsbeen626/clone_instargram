package moon.clone.instargram.web.dto.user;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
@AllArgsConstructor
@Setter
@Data
public class UserSignupDto {

    @Size(min=2, max=100)
    @NotBlank
    private String email;

    @NotBlank
    private String password;
    private String phone;

    @Size(min=1, max=30)
    @NotBlank
    private String name;
}
