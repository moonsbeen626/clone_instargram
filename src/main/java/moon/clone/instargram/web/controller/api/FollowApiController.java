package moon.clone.instargram.web.controller.api;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.config.auth.PrincipalDetails;
import moon.clone.instargram.domain.follow.FollowRepository;
import moon.clone.instargram.service.FollowService;
import moon.clone.instargram.web.dto.follow.FollowDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FollowApiController {

    private final FollowService followService;

    @PostMapping("/follow/{toUserId}")
    public void followUser(@PathVariable long toUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        followService.follow(principalDetails.getUser().getId(), toUserId);
    }

    @DeleteMapping("/follow/{toUserId}")
    public void unFollowUser(@PathVariable long toUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        followService.unFollow(principalDetails.getUser().getId(), toUserId);
    }
}
