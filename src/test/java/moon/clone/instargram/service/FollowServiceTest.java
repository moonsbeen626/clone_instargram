package moon.clone.instargram.service;

import moon.clone.instargram.domain.follow.Follow;
import moon.clone.instargram.domain.follow.FollowRepository;
import moon.clone.instargram.domain.user.User;
import moon.clone.instargram.handler.ex.CustomApiException;
import moon.clone.instargram.web.dto.follow.FollowDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class FollowServiceTest {

    @InjectMocks
    private FollowService followService;

    @Mock
    private FollowRepository followRepository;

    @Mock
    private EntityManager em;

    @Mock
    private Query mock_query;

    private User from_user;
    private User to_user;

    @BeforeEach
    public void setUp() {
        from_user = User.builder().email("from@email").name("from").password("asd").phone("asd")
                .profileImgUrl(null).website(null).title(null).build();
        to_user = User.builder().email("to@email").name("to").password("asd").phone("asd")
                .profileImgUrl(null).website(null).title(null).build();
    }

    @Test
    public void follow_실패() throws Exception {
        //given
        Follow follow = new Follow(from_user, to_user);
        given(followRepository.findFollowByFromUserIdAndToUserId(anyLong(), anyLong())).willReturn(follow);

        //when
        assertThrows(CustomApiException.class, () -> {
            followService.follow(from_user.getId(), to_user.getId());
        });
    }

    @Test
    public void getFollower_성공() {
        //given
        Follow follow = new Follow(from_user, to_user);
        followRepository.save(follow);

        List<Object> followDtoList = new ArrayList<>(); // jparesultmapper의 return타입은 arraylist
        Object[] result_object = {to_user.getId(), to_user.getName(), ".jpg", 1, 1};
        followDtoList.add(result_object);

        given(em.createNativeQuery(any())).willReturn(mock_query);
        given(em.createNativeQuery(any()).setParameter(anyInt(), any())).willReturn(mock_query);
        given(mock_query.getResultList()).willReturn(followDtoList);

        //when
        List<FollowDto> followDtoList_result = followService.getFollower(to_user.getId(), to_user.getId());

        //then
        assertThat(followDtoList_result.size()).isEqualTo(followDtoList.size());
    }

    @Test
    public void getFollowing_성공() {
        //given
        Follow follow = new Follow(from_user, to_user);
        followRepository.save(follow);

        List<Object> followDtoList = new ArrayList<>(); // jparesultmapper의 return타입은 arraylist
        Object[] result_object = {from_user.getId(), from_user.getName(), ".jpg", 1, 1};
        followDtoList.add(result_object);

        given(em.createNativeQuery(any())).willReturn(mock_query);
        given(em.createNativeQuery(any()).setParameter(anyInt(), any())).willReturn(mock_query);
        given(mock_query.getResultList()).willReturn(followDtoList);

        //when
        List<FollowDto> followDtoList_result = followService.getFollowing(from_user.getId(), from_user.getId());

        //then
        assertThat(followDtoList_result.size()).isEqualTo(followDtoList.size());
    }
}
