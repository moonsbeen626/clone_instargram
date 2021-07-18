package moon.clone.instargram.service;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.config.auth.PrincipalDetails;
import moon.clone.instargram.domain.follow.FollowRepository;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import moon.clone.instargram.web.dto.user.UserProfileDto;
import moon.clone.instargram.web.dto.user.UserSignupDto;
import moon.clone.instargram.web.dto.user.UserUpdateDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public boolean save(UserSignupDto userSignupDto) {
        if(userRepository.findUserByEmail(userSignupDto.getEmail()) != null) return false;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userRepository.save(User.builder()
                .email(userSignupDto.getEmail())
                .password(encoder.encode(userSignupDto.getPassword()))
                .phone(userSignupDto.getPhone())
                .name(userSignupDto.getName())
                .title(null)
                .website(null)
                .profileImgUrl(null)
                .build());
        return true;
    }

    @Value("${profileImg.path}")
    private String uploadFolder;

    @Transactional
    public void update(UserUpdateDto userUpdateDto, MultipartFile multipartFile, PrincipalDetails principalDetails) {
        User user = userRepository.findUserById(userUpdateDto.getId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String imageFileName = user.getId() + "_" + multipartFile.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        if(multipartFile.getSize() != 0) { //파일이 업로드 되었는지 확인
            try {
                if (user.getProfileImgUrl() != null) { // 이미 프로필 사진이 있을경우
                    File file = new File(uploadFolder + user.getProfileImgUrl());
                    file.delete(); // 원래파일 삭제
                }
                Files.write(imageFilePath, multipartFile.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            user.updateProfileImgUrl(imageFileName);
        }

        user.update(
                encoder.encode(userUpdateDto.getPassword()),
                userUpdateDto.getPhone(),
                userUpdateDto.getName(),
                userUpdateDto.getTitle(),
                userUpdateDto.getWebsite()
        );

        //세션 정보 변경
        principalDetails.setUser(user);
    }

    @Transactional
    public UserProfileDto getUserProfileDto(long profileId, long sessionId) {
        UserProfileDto userProfileDto = new UserProfileDto();

        User user = userRepository.getById(profileId);
        userProfileDto.setUser(user);
        userProfileDto.setPostCount(user.getPostList().size());

        // loginEmail 활용하여 currentId가 로그인된 사용자 인지 확인
        User loginUser = userRepository.findUserById(sessionId);
        userProfileDto.setLoginUser(loginUser.getId() == user.getId());

        // currentId를 가진 user가 loginEmail을 가진 user를 구독 했는지 확인
        userProfileDto.setFollow(followRepository.findFollowByFromUserAndToUser(loginUser, user) != null);

        //currentId를 가진 user의 팔로워, 팔로잉 수를 확인한다.
        userProfileDto.setUserFollowerCount(followRepository.findFollowerCountById(profileId));
        userProfileDto.setUserFollowingCount(followRepository.findFollowingCountById(profileId));

        //좋아요 수 확인
        user.getPostList().forEach(post -> {
            post.setLikesCount(post.getLikeList().size());
        });

        return userProfileDto;
    }
}
