package instagram.instagram_CloneCoding.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByEmail(String email);
    User findUserById(Long Id);
}
