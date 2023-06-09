package com.ojeomme.domain.user.enums;

import com.ojeomme.common.enums.EnumCodeType;
import com.ojeomme.exception.ApiErrorCode;
import com.ojeomme.exception.ApiException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum OauthProvider implements EnumCodeType {

    KAKAO("카카오", "1"),
    NAVER("네이버", "2");

    private final String desc;
    private final String code;

    public static OauthProvider of(String oauthProvider) {
        return Arrays.stream(OauthProvider.values())
                .filter(v -> v.name().equalsIgnoreCase(oauthProvider))
                .findFirst()
                .orElseThrow(() -> new ApiException(ApiErrorCode.NOT_SUPPORT_OAUTH_PROVIDER));
    }
}
