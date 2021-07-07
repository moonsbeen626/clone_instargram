package moon.clone.instargram.web.controller.api;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.follow.Follow;
import moon.clone.instargram.domain.follow.FollowRepository;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import moon.clone.instargram.service.FollowService;
import moon.clone.instargram.service.UserService;
import moon.clone.instargram.web.dto.follow.FollowDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FollowApiController {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final FollowService followService;

    /**
     * follow fromUserId를 가진 user가 toUserId를 가진 user를 팔로우 하는 정보를 추가한다.
     * @param toUserId 팔로우 당하는 유저의 id
     * @return 새로 생성된 follow 객체
     */
    @PostMapping("/follow/{toUserId}") // 수정필요 -> dto로
    public Follow followUser(@PathVariable long toUserId, Authentication authentication) {
        User fromUser = userRepository.findUserByEmail(authentication.getName());
        User toUser = userRepository.findUserById(toUserId);

        Follow follow = Follow.builder()
                .fromUser(fromUser)
                .toUser(toUser).build();
        followRepository.save(follow);

        return follow;
    }

    /**
     * follow fromUserId를 가진 user가 toUserId를 가진 user를 팔로우 하는 정보를 삭제한다.
     * @param toUserId 언팔로우 당하는 유저의 id
     */
    @DeleteMapping("/follow/{toUserId}")
    public void unFollowUser(@PathVariable long toUserId, Authentication authentication) {
        Long id = followService.getFollowIdByFromEmailToId(authentication.getName(), toUserId);
        followRepository.deleteById(id);
    }

    /**
     * 팔로워 정보를 리스트로 반환.
     * @param profileId 현재 프로필페이지의 주인 id
     * @return profileId를 toUser로 가지는 팔로워의 정보
     */
    @GetMapping("/follow/{profileId}/follower")
    public List<FollowDto> getFollower(@PathVariable long profileId, Authentication authentication) {
        return followService.getFollowDtoListByProfileIdAboutFollower(profileId, authentication.getName());
    }

    /**
     * 팔로잉 정보를 리스트로 반환.
     * @param profileId 현재 프로필페이지의 주인 id
     * @return profileId를 toUser로 가지는 팔로잉 정보
     */
    @GetMapping("/follow/{profileId}/following")
    public List<FollowDto> getFollowing(@PathVariable long profileId, Authentication authentication) {
        return followService.getFollowDtoListByProfileIdAboutFollowing(profileId, authentication.getName());
    }
}
