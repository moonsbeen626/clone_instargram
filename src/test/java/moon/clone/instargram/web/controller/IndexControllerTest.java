package moon.clone.instargram.web.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //실제 내장 톰캣 사용
class IndexControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void loading_login_fromIndex() {
        String body = this.testRestTemplate.getForObject("/" , String.class);
        assertThat(body).contains("로그인");
    }

    @Test
    public void loading_signup() {
        String body = this.testRestTemplate.getForObject("/signup", String.class);
        assertThat(body).contains("가입");
    }
}
