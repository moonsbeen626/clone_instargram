package moon.clone.instargram.service;

import moon.clone.instargram.config.auth.PrincipalDetails;
import moon.clone.instargram.domain.follow.FollowRepository;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import moon.clone.instargram.handler.ex.CustomValidationException;
import moon.clone.instargram.web.dto.user.UserProfileDto;
import moon.clone.instargram.web.dto.user.UserSignupDto;
import moon.clone.instargram.web.dto.user.UserUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FollowRepository followRepository;

    @Mock
    private User mock_user;

    @Mock
    private PrincipalDetails principalDetails;

    @Mock
    private MultipartFile multipartFile;

    private User user;

    @BeforeEach
    public void setUp() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user = User.builder()
                .email("test@test")
                .name("test")
                .password(encoder.encode("test1234!"))
                .phone("123123")
                .title(null)
                .website(null)
                .profileImgUrl(null)
                .build();
    }

    public UserSignupDto createUserSignupDto() {
       return UserSignupDto.builder()
               .email(user.getEmail())
               .password(user.getPassword())
                .name(user.getName())
               .phone(user.getPhone())
               .build();
    }


    @Test
    public void save_회원가입_성공() throws Exception {
        //given
        UserSignupDto userSignupDto = createUserSignupDto();
        given(userRepository.findUserByEmail(any())).willReturn(null);
        given(userRepository.save(any())).willReturn(user);

        //when
        User saveUser = userService.save(userSignupDto);

        //then
        assertThat(saveUser).isNotNull();
        assertThat(userSignupDto.getEmail()).isEqualTo(saveUser.getEmail());
        assertThat(userSignupDto.getName()).isEqualTo(saveUser.getName());
    }

    @Test
    public void save_회원가입_실패() throws Exception {
        //given
        UserSignupDto userSignupDto = createUserSignupDto();
        given(userRepository.findUserByEmail(any())).willReturn(user);

        //when
        assertThrows(CustomValidationException.class, () -> {
            userService.save(userSignupDto);
        });
    }

    public UserUpdateDto createUserUpdateDto() {
        return UserUpdateDto.builder()
                .name("수정한 이름")
                .password(user.getPassword())
                .title(user.getTitle())
                .phone(user.getPhone())
                .website(user.getWebsite())
                .build();
    }

    @Test
    public void update_회원정보수정_성공() throws Exception {
        //given
        UserUpdateDto userUpdateDto = createUserUpdateDto();
        given(userRepository.findById(any())).willReturn(java.util.Optional.ofNullable(user));
        given(principalDetails.getUser()).willReturn(user);
        given(multipartFile.isEmpty()).willReturn(true);

        //when
        userService.update(userUpdateDto, multipartFile, principalDetails);

        //then
        assertThat(userUpdateDto.getName()).isEqualTo(user.getName());
        assertThat(userUpdateDto.getPassword()).isNotEqualTo(user.getPassword());
    }

    @Test
    public void update_회원정보수정_실패() throws Exception {
        //given
        UserUpdateDto userUpdateDto = createUserUpdateDto();
        given(userRepository.findById(any())).willReturn(java.util.Optional.ofNullable(null));
        given(principalDetails.getUser()).willReturn(user);

        //when
        assertThrows(CustomValidationException.class, () -> {
            userService.update(userUpdateDto, multipartFile, principalDetails);
        });
    }

    @Test
    public void getUserProfileDto_성공() throws Exception {
        //given
        given(userRepository.findById(any())).willReturn(java.util.Optional.ofNullable(mock_user));
        given(followRepository.findFollowByFromUserIdAndToUserId(user.getId(), user.getId())).willReturn(null);
        given(followRepository.findFollowerCountById(user.getId())).willReturn(0);
        given(followRepository.findFollowingCountById(user.getId())).willReturn(0);

        //when
        UserProfileDto userProfileDto = userService.getUserProfileDto(user.getId(), user.getId());

        //then
        assertThat(userProfileDto.getUser()).isEqualTo(mock_user);
        assertThat(userProfileDto.getUserFollowingCount()).isEqualTo(0);
        assertThat(userProfileDto.getUserFollowerCount()).isEqualTo(0);
        assertThat(userProfileDto.isLoginUser()).isEqualTo(true);
        assertThat(userProfileDto.isFollow()).isEqualTo(false);
    }

    @Test
    public void getUserProfileDto_유저찾기_실패() throws Exception {
        //given
        given(userRepository.findById(any())).willReturn(java.util.Optional.ofNullable(null));

        //when
        assertThrows(CustomValidationException.class, () -> {
            UserProfileDto userProfileDto = userService.getUserProfileDto(user.getId(), user.getId());
        });
    }
}
