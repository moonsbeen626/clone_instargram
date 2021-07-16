package moon.clone.instargram.service;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.likes.Likes;
import moon.clone.instargram.domain.likes.LikesRepository;
import moon.clone.instargram.domain.post.Post;
import moon.clone.instargram.domain.post.PostRepository;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;

    @Transactional
    public void likes(long postId, String loginEmail) {
        User user = userRepository.findUserByEmail(loginEmail);
        likesRepository.likes(postId, user.getId());
    }

    @Transactional
    public void unLikes(long postId, String loginEmail) {
        User user = userRepository.findUserByEmail(loginEmail);
        likesRepository.unLikes(postId, user.getId());
    }
}
