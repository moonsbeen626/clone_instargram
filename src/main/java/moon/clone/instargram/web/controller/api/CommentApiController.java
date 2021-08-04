package moon.clone.instargram.web.controller.api;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.config.auth.PrincipalDetails;
import moon.clone.instargram.domain.comment.Comment;
import moon.clone.instargram.handler.ex.CustomValidationApiException;
import moon.clone.instargram.service.CommentService;
import moon.clone.instargram.web.dto.comment.CommentUploadDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public Comment addComment(@Valid @RequestBody CommentUploadDto commentUploadDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationApiException("유효성 검사 실패", errorMap);
        }
        return commentService.addComment(commentUploadDto.getText(), commentUploadDto.getPostId(), principalDetails.getUser().getId());
    }

    @DeleteMapping("/comment/{id}")
    public void deleteComment(@PathVariable long id) {
        commentService.deleteComment(id);
    }
}
