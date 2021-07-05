package moon.clone.instargram.web.controller;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import moon.clone.instargram.service.FollowService;
import moon.clone.instargram.service.UserService;
import moon.clone.instargram.web.dto.UserProfileDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    //사용자 프로필 화면으로 이동
    @GetMapping("/user/profile")
    public String profile(Model model, @RequestParam Long id, Authentication authentication) {
        UserProfileDto userProfileDto = userService.getProfile(id, authentication.getName());
        model.addAttribute("userDto", userProfileDto);
        return "user/profile";
    }

    //사용자 정보 수정 페이지로 이동
    @GetMapping("/user/update")
    public String update(Authentication authentication, Model model) {
        User user = userRepository.findUserByEmail(authentication.getName());
        model.addAttribute("user", user);
        return "user/update";
    }

    //사용자 정보 업데이트
    @PostMapping("/user/update")
    public String updateUser(User user, RedirectAttributes redirectAttributes, Model model, Authentication authentication) {
        userService.update(user);
        redirectAttributes.addAttribute("id", user.getId());
        return "redirect:/user/profile";
    }
}
