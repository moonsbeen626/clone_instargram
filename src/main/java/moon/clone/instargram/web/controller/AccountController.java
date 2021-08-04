package moon.clone.instargram.web.controller;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.handler.ex.CustomValidationException;
import moon.clone.instargram.service.UserService;
import moon.clone.instargram.web.dto.user.UserSignupDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.naming.Binding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class AccountController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public String signup(@Valid UserSignupDto userSignupDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            Map<String,String> errorMap = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성 검사 실패", errorMap);
        }
        else {
            if (userService.save(userSignupDto)) {
                return "redirect:/login";
            } else {
                return "redirect:/signup?error";
            }
        }
    }

    //사용자 로그 아웃
    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}
