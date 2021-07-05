package moon.clone.instargram.web.controller;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AccountController {

    @Autowired
    UserService userService;

    //회원가입
    @PostMapping("/signup")
    public String signup(User user, Model model) {
        if(userService.save(user)) {
            return "redirect:/login";
        } else {
            return "redirect:/signup?error";
        }
    }
}
