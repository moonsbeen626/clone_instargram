package moon.clone.instargram.web.controller;

import moon.clone.instargram.domain.user.UserRepository;
import moon.clone.instargram.service.UserService;
import moon.clone.instargram.web.dto.user.UserSignupDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AccountControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }
//
//    @Test
//    @Transactional
//    public void login_success() throws Exception {
//        //given
//        String username = "test@test";
//        String password = "12345";
//
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        User newUser = createUser(username, encoder.encode(password), "", "");
//        userRepository.save(newUser);
//
//        //when then
//        mockMvc.perform(formLogin("/loginForm").user(username).password(password))
//                .andDo(print())
//                .andExpect(redirectedUrl("/user/story"));
//    }
//
//    @Test
//    @Transactional
//    public void login_fail() throws Exception {
//        //given
//        String username = "test@test";
//        String password = "12345";
//
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        User newUser = createUser(username, encoder.encode(password), "", "");
//        userRepository.save(newUser);
//
//        //when then
//        mockMvc.perform(formLogin("/loginForm").user(username).password("12121"))
//                .andDo(print())
//                .andExpect(redirectedUrl("/login?error"));
//    }
//
//    @Test
//    @Transactional
//    public void logout_success() throws Exception {
//        //when then
//        mockMvc.perform(get("/logout"))
//                .andExpect(redirectedUrl("/login"));
//    }
//
//    private User createUser(String email, String password, String phone, String name) {
//        User user = new User();
//        user.setEmail(email);
//        user.setPassword(password);
//        user.setPhone(phone);
//        user.setName(name);
//        return user;
//    }

//    @Test
//    @Transactional
//    public void signup_success() throws Exception {
//        //given
//        String email = "test@test";
//        String password = "12345";
//        String phone = "123-123-123";
//        String name = "test";
//        UserSignupDto userSignupDto = createUserLoginDto(email, password, phone, name);
//
//        //when
//        userService.save(userSignupDto);
//
//        //then
//        assertThat(userSignupDto.getEmail()).isEqualTo(userService.getUserDtoByEmail(email).getEmail());
//    }

//    @Test
//    @Transactional
//    public void signup_fail() throws Exception {
//        //given
//        String email = "test@test";
//        String password = "12345";
//        String phone = "123-123-123";
//        String name = "test";
//        UserSignupDto user1 = createUserLoginDto(email, password, phone, name);
//        UserSignupDto user2 = createUserLoginDto(email, password, phone, name);
//
//        //when
//        boolean isOk1 = userService.save(user1);
//        boolean isOk2 = userService.save(user2);
//
//        //then
//        assertThat(isOk1).isEqualTo(true);
//        assertThat(isOk2).isEqualTo(false);
//    }
//
//    private UserSignupDto createUserLoginDto(String email, String password, String phone, String name) {
//        UserSignupDto userSignupDto = new UserSignupDto();
//        userSignupDto.setEmail(email);
//        userSignupDto.setPassword(password);
//        userSignupDto.setPhone(phone);
//        userSignupDto.setName(name);
//        return userSignupDto;
//    }
}

