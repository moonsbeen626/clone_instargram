package moon.clone.instargram.service;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.follow.Follow;
import moon.clone.instargram.domain.follow.FollowRepository;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    /**
     * from_id, to_id에 해당하는 follow 객체 반환
     * @param fromId 팔로우 요청 id
     * @param toId 팔로우 당한 id
     * @return 해당 정보 가지고 있는 follow객체의 id값
     */
    @Transactional
    public long getFollowId(Long fromId, Long toId) {
        User fromUser = userRepository.findUserById(fromId);
        User toUser = userRepository.findUserById(toId);

        Follow follow = followRepository.findFollowByFromUserAndToUser(fromUser, toUser);

        if(follow != null) return follow.getId();
        else return -1;
    }

}
