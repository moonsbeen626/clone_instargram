package moon.clone.instargram.web.dto.user;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@Getter
@Data
public class UserUpdateDto {

    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 이상의 비밀번호여야 합니다.")
    private String password;

    @NotBlank(message = "전화번호를 입력해 주세요.")
    @Pattern(regexp = "^[0-9]+$", message = "전화 번호는 숫자로만 입력해 주세요.")
    private String phone;

    @Size(min=1, max=30, message = "이름은 1글자 이상, 30글자 이내로 작성해 주세요.")
    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;

    private String title;
    private String website;
}
