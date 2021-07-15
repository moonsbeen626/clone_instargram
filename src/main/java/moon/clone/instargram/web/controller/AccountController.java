package moon.clone.instargram.web.controller;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.service.UserService;
import moon.clone.instargram.web.dto.user.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AccountController {

    @Autowired
    UserService userService;

    //회원가입
    @PostMapping("/signup")
    public String signup(UserLoginDto userLoginDto) {
        if(userService.save(userLoginDto)) {
            return "redirect:/login";
        } else {
            return "redirect:/signup?error";
        }
    }
}
