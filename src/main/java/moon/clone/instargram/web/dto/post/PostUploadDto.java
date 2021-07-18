package moon.clone.instargram.web.dto.post;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Data
public class PostUploadDto {

    private String text;
    private String tag;
}
