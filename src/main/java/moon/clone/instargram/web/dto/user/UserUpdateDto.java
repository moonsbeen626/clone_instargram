package moon.clone.instargram.web.dto.user;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@Getter
@Data
public class UserUpdateDto {

    @NotBlank
    private String password;

    private String phone;

    @NotBlank
    @Size(min=2, max=30)
    private String name;

    private String title;
    private String website;
}
