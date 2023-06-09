package com.ojeomme.common.maps.entity;

import com.ojeomme.exception.ApiErrorCode;
import com.ojeomme.exception.ApiException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KakaoRegionCodeTest {

    @Nested
    class exist {

        @Test
        void 존재한다() {
            // given
            KakaoRegionCode kakaoRegionCode = KakaoRegionCode.builder()
                    .meta(KakaoRegionCode.Meta.builder()
                            .totalCount(1)
                            .build())
                    .documents(List.of(
                            KakaoRegionCode.Document.builder()
                                    .regionType("B")
                                    .build()
                    ))
                    .build();

            // when
            boolean exist = kakaoRegionCode.exist();

            // then
            assertThat(exist).isTrue();
        }

        @Test
        void 존재하지_않는다_count가_0() {
            // given
            KakaoRegionCode kakaoRegionCode = KakaoRegionCode.builder()
                    .meta(KakaoRegionCode.Meta.builder()
                            .totalCount(0)
                            .build())
                    .build();

            // when
            boolean exist = kakaoRegionCode.exist();

            // then
            assertThat(exist).isFalse();
        }

        @Test
        void 존재하지_않는다_법정동이_없음() {
            // given
            KakaoRegionCode kakaoRegionCode = KakaoRegionCode.builder()
                    .meta(KakaoRegionCode.Meta.builder()
                            .totalCount(1)
                            .build())
                    .documents(List.of(
                            KakaoRegionCode.Document.builder()
                                    .regionType("H")
                                    .build()
                    ))
                    .build();

            // when
            boolean exist = kakaoRegionCode.exist();

            // then
            assertThat(exist).isFalse();
        }
    }

    @Nested
    class getCode {

        @Test
        void 지역코드를_가져온다() {
            // given
            KakaoRegionCode kakaoRegionCode = KakaoRegionCode.builder()
                    .documents(List.of(
                            KakaoRegionCode.Document.builder().regionType("H").code("111").build(),
                            KakaoRegionCode.Document.builder().regionType("B").code("222").build()
                    ))
                    .build();

            // when
            String code = kakaoRegionCode.getCode();

            // then
            assertThat(code).isEqualTo("222");
        }

        @Test
        void document가_없으면_KakaoSearchRegionCodeException를_발생한다() {
            // given
            KakaoRegionCode kakaoRegionCode = KakaoRegionCode.builder().documents(Collections.emptyList()).build();

            // then
            ApiException exception = assertThrows(ApiException.class, kakaoRegionCode::getCode);

            // then
            assertThat(exception.getErrorCode()).isEqualTo(ApiErrorCode.KAKAO_SEARCH_REGION_CODE);
        }
    }
}