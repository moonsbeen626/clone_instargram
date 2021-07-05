package moon.clone.instargram.web.controller.api;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserApiController {

    @Autowired
    private final UserRepository userRepository;

    @GetMapping("/users") //모든 user 조회
    List<User> all() {
        return userRepository.findAll();
    }

    @PostMapping("/users") //등록
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @GetMapping("/users/{id}") //id에 해당되는 user 조회
    User one(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PutMapping("/users/{id}") //수정
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();//패스워드 암호화

        return userRepository.findById(id)//id로 user를 조회함
                .map(user -> {
                    user.setPassword("{bcrypt}" + encoder.encode(newUser.getPassword()));
                    user.setPhone(newUser.getPhone());
                    user.setName(newUser.getName());
                    user.setTitle(newUser.getTitle());
                    user.setWebsite(newUser.getWebsite());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}") //삭제
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
