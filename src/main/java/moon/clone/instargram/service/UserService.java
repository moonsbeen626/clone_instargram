package moon.clone.instargram.service;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.follow.FollowRepository;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import moon.clone.instargram.web.dto.user.UserDto;
import moon.clone.instargram.web.dto.user.UserLoginDto;
import moon.clone.instargram.web.dto.user.UserProfileDto;
import moon.clone.instargram.web.dto.user.UserUpdateDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    /**
     * Spring Security 필수 메소드
     * @param email 입력받은 사용자 이메일 정보
     * @return eamil로 찾은 user정보
     * @throws UsernameNotFoundException
     */
    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);

        if(user == null) return null;
        return user;
    }

    /**
     * 회원정보 추가
     * @param userLoginDto 회원 가입 폼으로 부터 전달받은 정보
     * @return 이미 저장된 email여부에 따라 사용자 추가가 되었는지에 대한 boolean값.
     */
    @Transactional
    public boolean save(UserLoginDto userLoginDto) {
        if(userRepository.findUserByEmail(userLoginDto.getEmail()) != null) return false;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userRepository.save(User.builder()
                .email(userLoginDto.getEmail())
                .password(encoder.encode(userLoginDto.getPassword()))
                .phone(userLoginDto.getPhone())
                .name(userLoginDto.getName())
                .title(null)
                .website(null)
                .profileImgUrl(null)
                .build());
        return true;
    }

    @Value("${profileImg.path}")
    private String uploadFolder;
    /**
     * 사용자 정보 업데이트
     * @param userUpdateDto 업데이트 할 사용자 정보
     */
    @Transactional
    public void update(UserUpdateDto userUpdateDto, MultipartFile multipartFile) {
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
            user.setProfileImgUrl(imageFileName);
        }

        user.update(
                encoder.encode(userUpdateDto.getPassword()),
                userUpdateDto.getPhone(),
                userUpdateDto.getName(),
                userUpdateDto.getTitle(),
                userUpdateDto.getWebsite()
        );
    }

    /**
     * UserProfileDto 정보 반환
     * @param currentId 현재 접속한 profile 페이지의 사용자
     * @param loginEmail 로그인한 사용자의 email
     * @return currentId에 해당하는 user profile정보 반환
     */
    @Transactional
    public UserProfileDto getProfile(long currentId, String loginEmail) {
        UserProfileDto userProfileDto = new UserProfileDto();

        // 현재 id에 해당하는 user정보로 UserDto 생성.
        User user = userRepository.getById(currentId);
        userProfileDto.setUserDto(UserDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .title(user.getTitle())
                        .phone(user.getPhone())
                        .website(user.getWebsite())
                        .profileImgUrl(user.getProfileImgUrl())
                        .build());

        // loginEmail 활용하여 currentId가 로그인된 사용자 인지 확인
        User loginUser = userRepository.findUserByEmail(loginEmail);
        userProfileDto.setLoginUser(loginUser.getId() == user.getId());
        userProfileDto.setLoginId(loginUser.getId());

        // currentId를 가진 user가 loginEmail을 가진 user를 구독 했는지 확인
        userProfileDto.setFollow(followRepository.findFollowByFromUserAndToUser(loginUser, user) != null);

        //currentId를 가진 user의 팔로워, 팔로잉 수를 확인한다.
        userProfileDto.setUserFollowerCount(followRepository.findFollowerCountById(currentId));
        userProfileDto.setUserFollowingCount(followRepository.findFollowingCountById(currentId));

        return userProfileDto;
    }

    /**
     * UserDto 정보 반환
     * @param email 사용자의 email 정보
     * @return 로그인한 사용자의 UserDto
     */
    @Transactional
    public UserDto getUserDtoByEmail(String email) {
        User user = userRepository.findUserByEmail(email);

        return UserDto.builder()
                .id(user.getId())
                .email(email)
                .name(user.getName())
                .title(user.getTitle())
                .phone(user.getPhone())
                .website(user.getWebsite())
                .profileImgUrl(user.getProfileImgUrl())
                .build();
    }
}
