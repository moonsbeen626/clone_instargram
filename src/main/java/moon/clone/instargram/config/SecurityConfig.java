package moon.clone.instargram.config;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers( "/", "/login", "/signup", "/style/**", "/js/**", "/img/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginForm")
                .defaultSuccessUrl("/story");
    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.eraseCredentials(false).userDetailsService(userService).passwordEncoder(delegatingPasswordEncoder());
//    }
//
//    @Bean
//    public PasswordEncoder delegatingPasswordEncoder() {
//        PasswordEncoder defaultEncoder = new StandardPasswordEncoder();
//        Map<String, PasswordEncoder> encoders = new HashMap<>();
//        encoders.put("bcrypt", new BCryptPasswordEncoder());
//
//        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(
//                "bcrypt", encoders);
//        passwordEncoder.setDefaultPasswordEncoderForMatches(defaultEncoder);
//
//        return passwordEncoder;
//    }
}
