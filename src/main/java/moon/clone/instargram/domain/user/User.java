package moon.clone.instargram.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {

    @Id
    private String email; //PK

    private String password;
    private String phone;
    private String name;

}
