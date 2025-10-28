package com.example.dance_community.encoder;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {
    public static String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}