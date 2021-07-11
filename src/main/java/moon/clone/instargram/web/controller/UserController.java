package moon.clone.instargram.web.controller;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import moon.clone.instargram.service.UserService;
import moon.clone.instargram.web.dto.user.UserDto;
import moon.clone.instargram.web.dto.user.UserLoginDto;
import moon.clone.instargram.web.dto.user.UserProfileDto;
import moon.clone.instargram.web.dto.user.UserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    //메인 sroty화면으로 이동
    @GetMapping("user/story")
    public String story(Authentication authentication, Model model) {
        UserDto userDto = userService.getUserDtoByEmail(authentication.getName());
        model.addAttribute("userDto", userDto);
        return "user/story";
    }

    //사용자 프로필 화면으로 이동
    @GetMapping("/user/profile")
    public String profile(Model model, @RequestParam long id, Authentication authentication) {
        UserProfileDto userProfileDto = userService.getProfile(id, authentication.getName());
        model.addAttribute("userProfileDto", userProfileDto);
        return "user/profile";
    }

    //사용자 정보 수정 페이지로 이동
    @GetMapping("/user/update")
    public String update(Authentication authentication, Model model) {
        UserDto userDto = userService.getUserDtoByEmail(authentication.getName());
        model.addAttribute("userDto", userDto);
        return "user/update";
    }

    //사용자 정보 업데이트
    @PostMapping("/user/update")
    public String updateUser(UserUpdateDto userUpdateDto, @RequestParam("profileImgUrl") MultipartFile multipartFile, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        userService.update(userUpdateDto, multipartFile);
        redirectAttributes.addAttribute("id", userUpdateDto.getId());
        return "redirect:/user/profile";
    }
}
