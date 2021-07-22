package moon.clone.instargram.web.dto.comment;

import lombok.Data;

@Data
public class CommentUploadDto {
    private String text;
    private long postId;
}
