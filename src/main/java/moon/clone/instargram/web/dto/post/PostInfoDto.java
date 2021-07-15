package moon.clone.instargram.web.dto.post;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class PostInfoDto {

    private long id;
    private String text;
    private String tag;
    private LocalDateTime createdate;
    private boolean uploader;
    private long uploaderId;
    private String postImgUrl;
}
