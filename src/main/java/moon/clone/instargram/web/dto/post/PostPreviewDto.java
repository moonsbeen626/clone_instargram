package moon.clone.instargram.web.dto.post;

import lombok.*;

import java.math.BigInteger;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class PostPreviewDto {
    private long id;
    private String postImgUrl;
    private long likesCount;

    public PostPreviewDto(BigInteger id, String postImgUrl, BigInteger likesCount) {
        this.id = id.longValue();
        this.postImgUrl = postImgUrl;
        this.likesCount = likesCount.longValue();
    }
}
