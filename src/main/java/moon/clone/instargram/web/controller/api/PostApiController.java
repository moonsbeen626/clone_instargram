package moon.clone.instargram.web.controller.api;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.config.auth.PrincipalDetails;
import moon.clone.instargram.service.LikesService;
import moon.clone.instargram.service.PostService;
import moon.clone.instargram.web.dto.post.PostInfoDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostApiController {

    private final PostService postService;
    private final LikesService likesService;

    @GetMapping("/post/{postId}")
    public PostInfoDto getPostInfo(@PathVariable long postId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return postService.getPostInfoDto(postId, principalDetails.getUser().getId());
    }

    @PostMapping("/post/{postId}/likes")
    public void  likes(@PathVariable long postId , @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.likes(postId, principalDetails.getUser().getId());
    }

    @DeleteMapping("/post/{postId}/unLikes")
    public void unLikes(@PathVariable long postId , @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.unLikes(postId, principalDetails.getUser().getId());
    }
}
