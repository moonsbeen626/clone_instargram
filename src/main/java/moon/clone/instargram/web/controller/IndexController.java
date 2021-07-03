package moon.clone.instargram.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/") // session 여부에 따라 로그인 페이지로 이동할지, 메인 페이지로 이동할지 결정하도록 변경해야함.
    public String index() {
        return "login";
    }

    @GetMapping("/signup") //회원 가입 폼으로 이동
    public String signup() {
        return "signup";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/story")
    public String story() {
        return "story";
    }
}
