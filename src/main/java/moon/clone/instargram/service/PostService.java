package moon.clone.instargram.service;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.config.auth.PrincipalDetails;
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

        //포스트 정보 요청시 포스트 엔티티의 likesCount도 설정해준다.
        post.setLikesCount(post.getLikeList().size());
        postInfoDto.setLikesCount(post.getLikesCount());

        User user = userRepository.findUserById(sessionId);
        if(user.getId() == post.getUser().getId()) postInfoDto.setUploader(true);
        else postInfoDto.setUploader(false);

        if(likesRepository.findLikesByPostAndUser(post, user) != null) postInfoDto.setLikeState(true);
        else postInfoDto.setLikeState(false);

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

    @Value("${post.path}")
    private String uploadFolder;
    @Transactional
    public void delete(long postId) {
        Post post = postRepository.findPostById(postId);

        //관련된 likes의 정보 먼저 삭제해 준다.
        likesRepository.deleteLikesByPost(post);

        //관련 파일 저장 위치에서 삭제해 준다.
        File file = new File(uploadFolder + post.getPostImgUrl());
        file.delete();

        postRepository.deletePostById(postId);
    }
}
