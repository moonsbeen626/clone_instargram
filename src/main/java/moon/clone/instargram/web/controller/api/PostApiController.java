package moon.clone.instargram.web.controller.api;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.follow.Follow;
import moon.clone.instargram.service.PostService;
import moon.clone.instargram.web.dto.post.PostInfoDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostApiController {

    private final PostService postService;

    @GetMapping("/post/{postId}")
    public PostInfoDto getPostInfo(@PathVariable long postId, Authentication authentication) {
        return postService.getPostInfoDto(postId, authentication.getName());
    }

}
