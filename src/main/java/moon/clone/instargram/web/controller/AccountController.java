package moon.clone.instargram.web.controller;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.service.UserService;
import moon.clone.instargram.web.dto.user.UserSignupDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AccountController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public String signup(UserSignupDto userSignupDto) {
        if(userService.save(userSignupDto)) {
            return "redirect:/login";
        } else {
            return "redirect:/signup?error";
        }
    }
}
