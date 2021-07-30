package moon.clone.instargram.config;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.config.auth.PrincipalDetailsService;
import moon.clone.instargram.config.oauth.Oauth2DetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalDetailsService principalDetailsService;
    private final Oauth2DetailsService oauth2DetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/login", "/signup", "/style/**", "/js/**", "/img/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginForm")
                .defaultSuccessUrl("/")
            .and()
                .logout()
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true) // 세션 전체 삭제
            .and()
                .oauth2Login() //form 로그인도 하고, oauth2로그인도 한다.
                .loginPage("/login")//내가 만든 로그인 페이지 사용
                .userInfoEndpoint() //oauth2로그인시 최종 유저의 정보를 바로 받아온다.
                .userService(oauth2DetailsService)
        ;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
