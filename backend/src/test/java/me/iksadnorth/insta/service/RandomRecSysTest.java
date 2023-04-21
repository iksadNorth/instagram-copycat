package me.iksadnorth.insta.service;

import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@Slf4j
@DisplayName("RandomRecSys 관련 테스트")
@ExtendWith(MockitoExtension.class)
class RandomRecSysTest {
    @Mock ArticleRepository articleRepository;
    @InjectMocks RandomRecSys randomRecSys;

    @Test
    @DisplayName("[recommend] 정상 작동 여부 테스트")
    void givenCountsAndSize_whenCallRecommend_thenReturnNonDuplicate() {
        // given
        int size = 10;
        long count = 1000L;
        Pageable pageable = PageRequest.of(0, size);
        given(articleRepository.count()).willReturn(count);

        // when
        List<Long> recommended = randomRecSys.recommend(0L, pageable);

        // then
        // 의도한 갯수만큼 출력해야 한다.
        long num = Math.min(size, count);

        assertThat(recommended.size()).isEqualTo(num);
        // 중복이 없어야 한다.
        assertThat(new HashSet<>(recommended).size()).isEqualTo(num);

        log.debug(recommended.toString());
    }
}