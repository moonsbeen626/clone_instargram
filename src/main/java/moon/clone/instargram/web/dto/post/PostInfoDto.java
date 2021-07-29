package moon.clone.instargram.web.dto.post;

import lombok.*;
import moon.clone.instargram.domain.comment.Comment;
import moon.clone.instargram.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

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
    private User postUploader;
    private long likesCount;
    private boolean likeState;
    private boolean uploader;
    private String postImgUrl;
    private List<Comment> commentList;
}
