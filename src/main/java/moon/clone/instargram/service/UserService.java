package moon.clone.instargram.service;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import moon.clone.instargram.web.dto.UserSaveDto;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원정보 저장
     * @param user 회원정보
     * @return 저장되는 회원의 User엔티티
     */
    @Transactional
    public User save(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();//패스워크 암호화
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user);
//        return userRepository.save(User.builder()
//                .email(user.getEmail())
//                .password(user.getPassword())
//                .phone(user.getPhone())
//                .name(user.getName()).build());
    }
}
