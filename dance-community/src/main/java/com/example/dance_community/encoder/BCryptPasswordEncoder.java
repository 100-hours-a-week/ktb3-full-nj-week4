package com.example.dance_community.encoder;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

// BCrypt 선택 이유 : Spring Security 기본 지원 및 다른 함수에 비해 메모리 적게 사용 / Salt가 해시에 포함되며 단방향 해시함수로 보안성 높음

@Component
public class BCryptPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}