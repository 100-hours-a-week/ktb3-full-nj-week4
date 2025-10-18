package com.example.dance_community.repository;

import com.example.dance_community.dto.user.UserDto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {
    private ConcurrentHashMap<Long, UserDto> userMap = new ConcurrentHashMap<>();
    private static AtomicLong userId = new AtomicLong(0);

    @PostConstruct
    public void initData() {
        UserDto defaultUser = new UserDto();
        defaultUser.setUserId(userId.incrementAndGet());
        defaultUser.setEmail("test@gmail.com");
        defaultUser.setPassword("1234");
        defaultUser.setUsername("namjin");
        defaultUser.setClubId(null);
        defaultUser.setProfileImage(null);

        userMap.put(defaultUser.getUserId(), defaultUser);
    }

    public UserDto saveUser(UserDto userDto){
        if(userDto.getUserId() == null){
            userDto.setUserId(userId.incrementAndGet());
        }
        userMap.put(userDto.getUserId(), userDto);
        return userDto;
    }

    public Optional<UserDto> findById(Long userId){
        return Optional.ofNullable(userMap.get(userId));
    }

    public Optional<UserDto> findByEmail(String email){
        return userMap.values().stream()
                .filter(userDto -> userDto.getEmail().equals(email))
                .findFirst();
    }

    public void deleteUser(Long userId) {
        userMap.remove(userId);
    }
}
