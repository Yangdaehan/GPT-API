package org.sopt.gptapi.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Platform {

    KAKAO("kakao"),
    GOOGLE("google"),
    APPLE("apple");

    private final String name;

}
