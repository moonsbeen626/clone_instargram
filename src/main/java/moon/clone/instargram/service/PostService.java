package moon.clone.instargram.service;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.config.auth.PrincipalDetails;
import moon.clone.instargram.domain.comment.CommentRepository;
import moon.clone.instargram.domain.likes.LikesRepository;
import moon.clone.instargram.domain.post.Post;
import moon.clone.instargram.domain.post.PostRepository;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import moon.clone.instargram.web.dto.post.PostDto;
import moon.clone.instargram.web.dto.post.PostUpdateDto;
import moon.clone.instargram.web.dto.post.PostInfoDto;
import moon.clone.instargram.web.dto.post.PostUploadDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;

    @Value("${post.path}")
    private String uploadUrl;
    @Transactional
    public void save(PostUploadDto postUploadDto, MultipartFile multipartFile, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID();
        String imgFileName = uuid + "_" + multipartFile.getOriginalFilename();

        Path imageFilePath = Paths.get(uploadUrl + imgFileName);
        try {
            Files.write(imageFilePath, multipartFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        postRepository.save(Post.builder()
            .postImgUrl(imgFileName)
            .tag(postUploadDto.getTag())
            .text(postUploadDto.getText())
            .user(principalDetails.getUser())
            .likesCount(0)
            .build());
    }

    @Transactional
    public PostInfoDto getPostInfoDto(long postId, long sessionId) {
        PostInfoDto postInfoDto = new PostInfoDto();
        postInfoDto.setId(postId);

        Post post = postRepository.findPostById(postId);
        postInfoDto.setTag(post.getTag());
        postInfoDto.setText(post.getText());
        postInfoDto.setPostImgUrl(post.getPostImgUrl());
        postInfoDto.setCreatedate(post.getCreateDate());

        //포스트 정보 요청시 포스트 엔티티의 likesCount, likesState, CommentList를 설정해준다.
        postInfoDto.setLikesCount(post.getLikesList().size());
        post.getLikesList().forEach(likes -> {
            if(likes.getUser().getId() == sessionId) postInfoDto.setLikeState(true);
        });
        postInfoDto.setCommentList(post.getCommentList());

        User user = userRepository.findUserById(sessionId);
        if(user.getId() == post.getUser().getId()) postInfoDto.setUploader(true);
        else postInfoDto.setUploader(false);

        return postInfoDto;
    }

    @Transactional
    public PostDto getPostDto(long postId) {
        //예외 처리 필요 -> post의 작성자가 아닌 사람이 해당 페이지에 접근하여 수정하려고 한다면??
        Post post = postRepository.findPostById(postId);

        PostDto postDto = PostDto.builder()
                .id(postId)
                .tag(post.getTag())
                .text(post.getText())
                .postImgUrl(post.getPostImgUrl())
                .build();

        return postDto;
    }

    @Transactional
    public void update(PostUpdateDto postUpdateDto) {
        Post post = postRepository.findPostById(postUpdateDto.getId());
        post.update(postUpdateDto.getTag(), postUpdateDto.getText());
    }

    @Transactional
    public void delete(long postId) {
        Post post = postRepository.findPostById(postId);

        //관련된 likes의 정보 먼저 삭제해 준다.
        likesRepository.deleteLikesByPost(post);

        //관련된 Comment의 정보 먼저 삭제해 준다.
        commentRepository.deleteCommentsByPost(post);

        //관련 파일 저장 위치에서 삭제해 준다.
        File file = new File(uploadUrl + post.getPostImgUrl());
        file.delete();

        postRepository.deletePostById(postId);
    }

    @Transactional
    public Page<Post> mainStory(long sessionId, Pageable pageable) {
        Page<Post> postList = postRepository.mainStory(sessionId, pageable);

        postList.forEach(post -> {
            post.updateLikesCount(post.getLikesList().size());
            post.getLikesList().forEach(likes -> {
                if(likes.getUser().getId() == sessionId) post.updateLikesState(true);
            });
        });

        return postList;
    }

    @Transactional
    public Page<Post> searchResult(String tag, long sessionId, Pageable pageable) {
        Page<Post> postList = postRepository.searchResult(tag, pageable);

        postList.forEach(post -> {
            post.updateLikesCount(post.getLikesList().size());
            post.getLikesList().forEach(likes -> {
                if(likes.getUser().getId() == sessionId) post.updateLikesState(true);
            });
        });

        return postList;
    }
}
