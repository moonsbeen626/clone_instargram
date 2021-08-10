package moon.clone.instargram.service;

import lombok.RequiredArgsConstructor;
import moon.clone.instargram.domain.likes.LikesRepository;
import moon.clone.instargram.handler.ex.CustomApiException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public void likes(long postId, long sessionId) {
        try {
            likesRepository.likes(postId, sessionId);
        } catch (Exception e) {
            throw new CustomApiException("이미 좋아요 하였습니다.");
        }
    }

    @Transactional
    public void unLikes(long postId, long sessionId) {
        likesRepository.unLikes(postId, sessionId);
    }
}
