package moon.clone.instargram.service;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.follow.Follow;
import moon.clone.instargram.domain.follow.FollowRepository;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import moon.clone.instargram.web.dto.follow.FollowDto;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final EntityManager em;

    /**
     * from_id, to_id에 해당하는 follow 객체 반환
     * @param email 팔로우 요청 email
     * @param toId 팔로우 당한 id
     * @return 해당 정보 가지고 있는 follow객체의 id값
     */
    @Transactional
    public long getFollowIdByFromEmailToId(String email, Long toId) {
        User fromUser = userRepository.findUserByEmail(email);
        User toUser = userRepository.findUserById(toId);

        Follow follow = followRepository.findFollowByFromUserAndToUser(fromUser, toUser);

        if(follow != null) return follow.getId();
        else return -1;
    }

    /**
     * profileId 사용자의 팔로워에 대한 정보를 List로 반환.
     * @param profileId 현재 프로필 페이지의 id
     * @return 팔로워 정보 담은 FollowDto 리스트
     */
    public List<FollowDto> getFollowDtoListByProfileIdAboutFollower(long profileId, String loginEmail) {
        long loginId = userRepository.findUserByEmail(loginEmail).getId();

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.name, u.profile_img_url, ");
        sb.append("if ((SELECT 1 FROM follow WHERE to_user_id = ? AND from_user_id = u.id), TRUE, FALSE) AS followState, ");
        sb.append("if ((?=u.id), TRUE, FALSE) AS loginUser ");
        sb.append("FROM user u, follow f ");
        sb.append("WHERE u.id = f.from_user_id AND f.to_user_id = ?");

        // 쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, loginId)
                .setParameter(2, loginId)
                .setParameter(3, profileId);

        //JPA 쿼리 매핑 - DTO에 매핑
        JpaResultMapper result = new JpaResultMapper();
        List<FollowDto> followDtoList = result.list(query, FollowDto.class);
        return followDtoList;
    }

    /**
     * profileId 사용자의 팔로잉에 대한 정보를 List로 반환.
     * @param profileId 현재 프로필 페이지의 id
     * @return 팔로잉 정보 담은 FollowDto 리스트
     */
    public List<FollowDto> getFollowDtoListByProfileIdAboutFollowing(long profileId, String loginEmail) {
        long loginId = userRepository.findUserByEmail(loginEmail).getId();

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.name, u.profile_img_url, ");
        sb.append("if ((SELECT 1 FROM follow WHERE to_user_id = ? AND to_user_id = u.id), TRUE, FALSE) AS followState, ");
        sb.append("if ((?=u.id), TRUE, FALSE) AS loginUser ");
        sb.append("FROM user u, follow f ");
        sb.append("WHERE u.id = f.to_user_id AND f.from_user_id = ?");

        // 쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, loginId)
                .setParameter(2, loginId)
                .setParameter(3, profileId);

        //JPA 쿼리 매핑 - DTO에 매핑
        JpaResultMapper result = new JpaResultMapper();
        List<FollowDto> followDtoList = result.list(query, FollowDto.class);
        return followDtoList;
    }
}
