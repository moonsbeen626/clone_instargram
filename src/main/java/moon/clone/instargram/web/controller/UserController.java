package moon.clone.instargram.web.controller;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import moon.clone.instargram.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Member;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    //사용자 프로필 화면으로 이동
    @GetMapping("/user/profile")
    public String profile(Model model, @RequestParam Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user/profile";
    }

    //사용자 정보 수정 페이지로 이동
    @GetMapping("/user/update")
    public String update(Authentication authentication, Model model) {
        User user = userService.getUserByEmail(authentication.getName());
        model.addAttribute("user", user);
        return "user/update";
    }

    //사용자 정보 업데이트
    @PostMapping("/user/update")
    public String updateUser(User user, RedirectAttributes redirectAttributes) {
        userService.update(user);
        redirectAttributes.addAttribute("id", user.getId());
        return "redirect:/user/profile";
    }
}
