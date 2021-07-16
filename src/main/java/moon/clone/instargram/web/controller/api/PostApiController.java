package moon.clone.instargram.web.controller.api;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.service.LikesService;
import moon.clone.instargram.service.PostService;
import moon.clone.instargram.web.dto.post.PostInfoDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostApiController {

    private final PostService postService;
    private final LikesService likesService;

    @GetMapping("/post/{postId}")
    public PostInfoDto getPostInfo(@PathVariable long postId, Authentication authentication) {
        return postService.getPostInfoDto(postId, authentication.getName());
    }

    @PostMapping("/post/{postId}/likes")
    public void  likes(@PathVariable long postId , Authentication authentication) {
        likesService.likes(postId, authentication.getName());
    }

    @DeleteMapping("/post/{postId}/unLikes")
    public void unLikes(@PathVariable long postId , Authentication authentication) {
        likesService.unLikes(postId, authentication.getName());
    }
}
