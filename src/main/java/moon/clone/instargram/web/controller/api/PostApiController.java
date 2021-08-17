package moon.clone.instargram.web.controller.api;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.config.auth.PrincipalDetails;
import moon.clone.instargram.service.LikesService;
import moon.clone.instargram.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostApiController {

    private final PostService postService;
    private final LikesService likesService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> postInfo (@PathVariable long postId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return new ResponseEntity<>(postService.getPostInfoDto(postId, principalDetails.getUser().getId()), HttpStatus.OK);
    }

    @PostMapping("/post/{postId}/likes")
    public ResponseEntity<?> likes(@PathVariable long postId , @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.likes(postId, principalDetails.getUser().getId());
        return new ResponseEntity<>("좋아요 성공", HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}/likes")
    public ResponseEntity<?> unLikes(@PathVariable long postId , @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.unLikes(postId, principalDetails.getUser().getId());
        return new ResponseEntity<>("좋아요 취소 성공", HttpStatus.OK);
    }

    @GetMapping("/post")
    public ResponseEntity<?> mainStory(@AuthenticationPrincipal PrincipalDetails principalDetails, @PageableDefault(size=3) Pageable pageable) {
        return new ResponseEntity<>(postService.getPost(principalDetails.getUser().getId(), pageable), HttpStatus.OK);
    }

    @GetMapping("/post/tag")
    public ResponseEntity<?> searchTag(@RequestParam String tag, @AuthenticationPrincipal PrincipalDetails principalDetails,
                                @PageableDefault(size=3) Pageable pageable) {
        return new ResponseEntity<>(postService.getTagPost(tag, principalDetails.getUser().getId(), pageable), HttpStatus.OK);
    }

    @GetMapping("/post/likes")
    public ResponseEntity<?> getLikesPost(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                             @PageableDefault(size=12) Pageable pageable) {
        return new ResponseEntity<>(postService.getLikesPost(principalDetails.getUser().getId(), pageable), HttpStatus.OK);
    }

    @GetMapping("/post/popular")
    public ResponseEntity<?> getPopularPost() {
        return new ResponseEntity<>(postService.getPopularPost(), HttpStatus.OK);
    }
}
