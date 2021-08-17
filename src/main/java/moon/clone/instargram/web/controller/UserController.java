package moon.clone.instargram.web.controller;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.config.auth.PrincipalDetails;
import moon.clone.instargram.handler.ex.CustomValidationException;
import moon.clone.instargram.service.UserService;
import moon.clone.instargram.web.dto.user.UserProfileDto;
import moon.clone.instargram.web.dto.user.UserUpdateDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    //사용자 프로필 화면으로 이동
    @GetMapping("/user/profile")
    public String profile(Model model, @RequestParam long id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        UserProfileDto userProfileDto = userService.getUserProfileDto(id, principalDetails.getUser().getId());
        model.addAttribute("userProfileDto", userProfileDto);
        return "user/profile";
    }

    //사용자 정보 수정 페이지로 이동
    @GetMapping("/user/update")
    public String update() {
        return "user/update";
    }

    //사용자 정보 업데이트
    @PostMapping("/user/update")
    public String updateUser(@Valid UserUpdateDto userUpdateDto, BindingResult bindingResult, @RequestParam("profileImgUrl") MultipartFile multipartFile,
                             RedirectAttributes redirectAttributes, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        userService.update(userUpdateDto, multipartFile, principalDetails);
        redirectAttributes.addAttribute("id", principalDetails.getUser().getId());
        return "redirect:/user/profile";
    }
}
