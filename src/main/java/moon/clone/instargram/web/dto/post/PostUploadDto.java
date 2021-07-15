package moon.clone.instargram.web.dto.post;

import lombok.*;
import moon.clone.instargram.web.dto.user.UserDto;

@Builder
@AllArgsConstructor
@Getter
@Data
public class PostUploadDto {

    private String text;
    private String tag;
}
