package moon.clone.instargram.web.controller;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import moon.clone.instargram.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Member;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    //회원가입
    @PostMapping("/signup")
    public String signup(User user, Model model) {
        if(userService.save(user)) {
            return "redirect:/login";
        } else {
            return "redirect:/signup?error";
        }
    }

    //사용자 정보 수정 페이지로 이동
    @GetMapping("/user/update")
    public String update(Principal principal, Model model) {
        User user = userService.getCurrentUserInfo(principal.getName());
        model.addAttribute("user", user);
        return "user/update";
    }

    //사용자 정보 업데이트
    @PostMapping("/user/update")
    public String updateUser(User user) {
        userService.update(user);
        return "redirect:/user/profile";
    }

    //사용자 프로필 화면으로 이동
    @GetMapping("/user/profile")
    public String update() {
        return "user/profile";
    }
}
