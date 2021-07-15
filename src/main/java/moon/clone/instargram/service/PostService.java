package moon.clone.instargram.service;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.post.Post;
import moon.clone.instargram.domain.post.PostRepository;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import moon.clone.instargram.web.dto.post.PostDto;
import moon.clone.instargram.web.dto.post.PostUpdateDto;
import moon.clone.instargram.web.dto.post.PostInfoDto;
import moon.clone.instargram.web.dto.post.PostUploadDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Value("${post.path}")
    private String uploadUrl;

    /**
     * 새로운 post를 저장한다.
     * @param postUploadDto post 업로드시 필요한 정보가 담겨있는 dto
     * @param userId post 업로드를 요청한 사용자의 id
     * @param multipartFile post 이미지
     */
    @Transactional
    public void save(PostUploadDto postUploadDto, long userId, MultipartFile multipartFile) {
        UUID uuid = UUID.randomUUID();
        String imgFileName = uuid + "_" + multipartFile.getOriginalFilename();

        Path imageFilePath = Paths.get(uploadUrl + imgFileName);
        try {
            Files.write(imageFilePath, multipartFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        User user = userRepository.findUserById(userId);
        postRepository.save(Post.builder()
            .postImgUrl(imgFileName)
            .tag(postUploadDto.getTag())
            .text(postUploadDto.getText())
            .user(user)
            .build());
    }

    /**
     * postinfodto를 반환한다.
     * @param postId 현재 포스트의 id
     * @param loginEmail 현재 로그인한 사용자의 email
     * @return 현재 포스트의 정보와 로그인한 사용자와의 관계를 담은 dto 반환
     */
    @Transactional
    public PostInfoDto getPostInfoDto(long postId, String loginEmail) {
        PostInfoDto postInfoDto = new PostInfoDto();
        postInfoDto.setId(postId);

        Post post = postRepository.findPostById(postId);
        postInfoDto.setTag(post.getTag());
        postInfoDto.setText(post.getText());
        postInfoDto.setPostImgUrl(post.getPostImgUrl());
        postInfoDto.setCreatedate(post.getCreateDate());

        User user = userRepository.findUserByEmail(loginEmail);
        postInfoDto.setUploaderId(user.getId());

        if(user.getId() == post.getUser().getId()) postInfoDto.setUploader(true);
        else postInfoDto.setUploader(false);

        return postInfoDto;
    }

    /**
     * 포스트 정보를 담은 dto를 반환한다.
     * @param postId 수정할 포스트의 id
     * @return
     */
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

    /**
     * 포스트 수정
     * @param postUpdateDto 수정된 정보를 가지고 있는 postupdatedto
     */
    @Transactional
    public void update(PostUpdateDto postUpdateDto) {
        Post post = postRepository.findPostById(postUpdateDto.getId());
        post.update(postUpdateDto.getTag(), postUpdateDto.getText());
    }

    @Value("${post.path}")
    private String uploadFolder;
    /**
     * 포스트 삭제
     * @param postId 삭제할 post의 id
     */
    @Transactional
    public void delete(long postId) {
        Post post = postRepository.findPostById(postId);

        File file = new File(uploadFolder + post.getPostImgUrl());
        file.delete();

        postRepository.deletePostById(postId);
    }
}
