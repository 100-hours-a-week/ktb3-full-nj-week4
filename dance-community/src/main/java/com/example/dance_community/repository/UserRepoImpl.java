package com.example.dance_community.repository;

import com.example.dance_community.dto.user.UserDto;
import jakarta.annotation.PostConstruct;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepoImpl implements UserRepo {
    private final ConcurrentHashMap<Long, UserDto> idToUserMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> emailToIdMap = new ConcurrentHashMap<>();
    private final static AtomicLong userIdGen = new AtomicLong(0);

    @PostConstruct
    public void initData() {
        UserDto defaultUser = UserDto.builder()
                .email("user@example.com")
                .password(BCrypt.hashpw("string", BCrypt.gensalt()))
                .username("tester")
                .build();

        this.saveUser(defaultUser);
    }

    @Override
    public UserDto saveUser(UserDto userDto){
        if(userDto.getUserId() == null){
            userDto = userDto.toBuilder()
                    .userId(userIdGen.incrementAndGet())
                    .build();
        }
        idToUserMap.put(userDto.getUserId(), userDto);
        emailToIdMap.put(userDto.getEmail(),userDto.getUserId());
        return userDto;
    }

    @Override
    public boolean existsById(Long userId){
        return idToUserMap.containsKey(userId);
    }

    @Override
    public Optional<UserDto> findById(Long userId){
        return Optional.ofNullable(idToUserMap.get(userId));
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        Long userId = emailToIdMap.get(email);
        if (userId == null) return Optional.empty();
        return findById(userId);
    }

    @Override
    public void deleteUser(Long userId) {
        UserDto removedUser = idToUserMap.remove(userId);
        if (removedUser != null) {
            emailToIdMap.remove(removedUser.getEmail());
        }
    }
}
