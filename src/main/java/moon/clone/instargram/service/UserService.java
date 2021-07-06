package moon.clone.instargram.service;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.config.auth.UserLogin;
import moon.clone.instargram.domain.follow.Follow;
import moon.clone.instargram.domain.follow.FollowRepository;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import moon.clone.instargram.web.dto.UserProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    /**
     * Spring Security 필수 메소드
     * @param username 입력받은 사용자 이메일 정보
     * @return UserLogin 시큐리티 메타 정보를 포함한 user정보
     * @throws UsernameNotFoundException
     */
    @Override
    public UserLogin loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);

        if(user != null) {
            return new UserLogin(user);
        } else throw new UsernameNotFoundException(username);
    }

    /**
     * 회원정보 저장
     * @param user 회원정보
     * @return 저장되는 회원의 User 엔티티
     */
    @Transactional
    public boolean save(User user) {
        if(userRepository.findUserByEmail(user.getEmail()) != null) return false;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();//패스워드 암호화
        user.setPassword("{bcrypt}" + encoder.encode(user.getPassword()));
        userRepository.save(User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .phone(user.getPhone())
                .name(user.getName())
                .title(null)
                .website(null)
                .profileImgUrl("/img/default_profile.jpg")
                .build());
        return true;
    }

    /**
     * 사용자 정보 업데이트
     * @param user 업데이트 할 사용자 정보
     */
    @Transactional
    public void update(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();//패스워드 암호화
        user.setPassword("{bcrypt}" +encoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    @Transactional
    public UserProfileDto getProfile(long currentId, String loginEmail) {
        UserProfileDto userProfileDto = new UserProfileDto();

        // 현재 id에 해당하는 user정보
        User user = userRepository.getById(currentId);
        userProfileDto.setUser(user);

        // loginEmail 활용하여 currentId가 로그인된 사용자 인지 확인
        User loginUser = userRepository.findUserByEmail(loginEmail);
        userProfileDto.setLoginUser(loginUser.getId() == user.getId());
        userProfileDto.setLoginId(loginUser.getId());

        // currentId를 가진 user가 loginEmail을 가진 user를 구독 했는지 확인
        userProfileDto.setFollow(followRepository.findFollowByFromUserAndToUser(loginUser, user) != null);

        return userProfileDto;
    }
}
