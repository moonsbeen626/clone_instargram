package moon.clone.instargram.service;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.likes.LikesRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public void likes(long postId, long sessionId) {
        likesRepository.likes(postId, sessionId);
    }

    @Transactional
    public void unLikes(long postId, long sessionId) {
        likesRepository.unLikes(postId, sessionId);
    }
}
