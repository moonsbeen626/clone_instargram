package moon.clone.instargram.web.controller;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.service.UserService;
import moon.clone.instargram.web.dto.user.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final UserService userService;

    @GetMapping("/") // session 여부에 따라 로그인 페이지로 이동할지, 메인 페이지로 이동할지 결정하도록 변경해야함.
    public String index() {
        return "login";
    }

    @GetMapping("/signup") //회원 가입 폼으로 이동
    public String signup() {
        return "signup";
    }

    @GetMapping("/login") //로그인 화면으로 이동
    public String login() {
        return "login";
    }

    //메인 sroty화면으로 이동
    @GetMapping("/story")
    public String story(Authentication authentication, Model model) {
        UserDto userDto = userService.getUserDtoByEmail(authentication.getName());
        model.addAttribute("userDto", userDto);
        return "story";
    }
}
