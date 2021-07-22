package moon.clone.instargram.web.controller.api;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.config.auth.PrincipalDetails;
import moon.clone.instargram.domain.comment.Comment;
import moon.clone.instargram.service.CommentService;
import moon.clone.instargram.web.dto.comment.CommentUploadDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public Comment addComment(@RequestBody CommentUploadDto commentUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return commentService.addComment(commentUploadDto.getText(), commentUploadDto.getPostId(), principalDetails.getUser().getId());
    }

    @DeleteMapping("/comment/{id}")
    public void deleteComment(@PathVariable long id) {
        commentService.deleteComment(id);
    }
}
