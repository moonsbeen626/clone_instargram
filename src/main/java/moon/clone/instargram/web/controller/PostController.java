package moon.clone.instargram.web.controller;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.config.auth.PrincipalDetails;
import moon.clone.instargram.service.PostService;
import moon.clone.instargram.service.UserService;
import moon.clone.instargram.web.dto.post.PostDto;
import moon.clone.instargram.web.dto.post.PostUpdateDto;
import moon.clone.instargram.web.dto.post.PostUploadDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    //포스트 업로드 화면으로 이동
    @GetMapping("/post/upload")
    public String upload() {
        return "post/upload";
    }

    //포스트 업로드 후 프로필 화면으로 이동
    @PostMapping("post")
    public String uploadPost(PostUploadDto postUploadDto, @RequestParam("uploadImgUrl") MultipartFile multipartFile, RedirectAttributes redirectAttributes, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        postService.save(postUploadDto, multipartFile, principalDetails);
        redirectAttributes.addAttribute("id", principalDetails.getUser().getId());
        return "redirect:/user/profile";
    }

    //포스트 수정 화면으로 이동
    @GetMapping("/post/update/{postId}")
    public String update(@PathVariable long postId, Model model) {
        PostDto postDto = postService.getPostDto(postId);
        model.addAttribute("postDto", postDto);
        return "post/update";
    }

    //포스트 수정 폼
    @PostMapping("/post/update")
    public String postUpdate(PostUpdateDto postUpdateDto, @AuthenticationPrincipal PrincipalDetails principalDetails, RedirectAttributes redirectAttributes) {
        postService.update(postUpdateDto);
        redirectAttributes.addAttribute("id", principalDetails.getUser().getId());
        return "redirect:/user/profile";
    }

    //포스트 삭제 폼
    @PostMapping("/post/delete")
    public String delete(@RequestParam("postId") long postId, @AuthenticationPrincipal PrincipalDetails principalDetails, RedirectAttributes redirectAttributes) {
        postService.delete(postId);
        redirectAttributes.addAttribute("id", principalDetails.getUser().getId());
        return "redirect:/user/profile";
    }
}
