package moon.clone.instargram.service;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.config.auth.UserLogin;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

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
        user.setPassword("{bcrypt}" +encoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
}
