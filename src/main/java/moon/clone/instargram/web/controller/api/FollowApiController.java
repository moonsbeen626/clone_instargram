package moon.clone.instargram.web.controller.api;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.follow.Follow;
import moon.clone.instargram.domain.follow.FollowRepository;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import moon.clone.instargram.service.FollowService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FollowApiController {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final FollowService followService;

    /**
     * follow fromUserId를 가진 user가 toUserId를 가진 user를 팔로우 하는 정보를 추가한다.
     * @param fromUserId 팔로우 하는 유저의 id
     * @param toUserId 팔로우 당하는 유저의 id
     * @return 새로 생성된 follow 객체
     */
    @PostMapping("/follow/{fromUserId}/{toUserId}")
    public Follow followUser(@PathVariable long fromUserId, @PathVariable Long toUserId) {
        User fromUser = userRepository.findUserById(fromUserId);
        User toUser = userRepository.findUserById(toUserId);

        Follow follow = Follow.builder()
                .fromUser(fromUser)
                .toUser(toUser).build();
        followRepository.save(follow);

        return follow;
    }

    @DeleteMapping("follow/{fromUserId}/{toUserId}")
    void unFollowUser(@PathVariable Long fromUserId, @PathVariable long toUserId) {
        Long id = followService.getFollowId(fromUserId, toUserId);
        followRepository.deleteById(id);
    }
}
