package moon.clone.instargram.web.controller;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.service.PostService;
import moon.clone.instargram.service.UserService;
import moon.clone.instargram.web.dto.post.PostDto;
import moon.clone.instargram.web.dto.post.PostUpdateDto;
import moon.clone.instargram.web.dto.post.PostUploadDto;
import moon.clone.instargram.web.dto.user.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final UserService userService;
    private final PostService postService;

    //포스트 업로드 화면으로 이동
    @GetMapping("/post/upload")
    public String upload(Authentication authentication, Model model) {
        UserDto userDto = userService.getUserDtoByEmail(authentication.getName());
        model.addAttribute("userDto", userDto);
        return "post/upload";
    }

    //포스트 업로드 후 프로필 화면으로 이동
    @PostMapping("post")
    public String uploadPost(PostUploadDto postUploadDto, @RequestParam("uploadImgUrl") MultipartFile multipartFile, RedirectAttributes redirectAttributes, Authentication authentication) {
        long id = userService.getUserIdByEmail(authentication.getName());
        postService.save(postUploadDto, id, multipartFile);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/user/profile";
    }

    //포스트 수정 화면으로 이동
    @GetMapping("/post/update/{postId}")
    public String update(@PathVariable long postId, Model model, Authentication authentication) {
        PostDto postDto = postService.getPostDto(postId);
        UserDto userDto = userService.getUserDtoByEmail(authentication.getName());
        model.addAttribute("postDto", postDto);
        model.addAttribute("userDto", userDto);
        return "post/update";
    }

    @PostMapping("/post/update")
    public String postUpdate(PostUpdateDto postUpdateDto, Authentication authentication, RedirectAttributes redirectAttributes) {
        long id = userService.getUserIdByEmail(authentication.getName());
        postService.update(postUpdateDto);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/user/profile";
    }

    @PostMapping("/post/delete")
    public String delete(@RequestParam("postId") long postId, Authentication authentication, RedirectAttributes redirectAttributes) {
        long id = userService.getUserIdByEmail(authentication.getName());
        postService.delete(postId);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/user/profile";
    }
}
