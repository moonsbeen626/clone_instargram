package moon.clone.instargram.web.controller.api;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.config.auth.PrincipalDetails;
import moon.clone.instargram.service.FollowService;
import moon.clone.instargram.web.dto.follow.FollowDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserApiController {

    private final FollowService followService;

    @GetMapping("/user/{profileId}/follower")
    public List<FollowDto> getFollower(@PathVariable long profileId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<FollowDto> list = followService.getFollower(profileId, principalDetails.getUser().getId());
        return list;
    }

    @GetMapping("/user/{profileId}/following")
    public List<FollowDto> getFollowing(@PathVariable long profileId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return followService.getFollowing(profileId, principalDetails.getUser().getId());
    }
}
