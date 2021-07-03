package moon.clone.instargram.web.controller;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup(User user, Model model) {
        if(userService.save(user)) {
            return "redirect:/login";
        } else {
            //model.addAttribute("msg", "이미 존재하는 이메일");
            return "redirect:/signup?error";
        }
    }
}
