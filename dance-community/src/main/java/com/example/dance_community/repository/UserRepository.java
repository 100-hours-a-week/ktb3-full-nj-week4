package com.example.dance_community.repository;

import com.example.dance_community.dto.user.UserDto;
import jakarta.annotation.PostConstruct;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {
    private final ConcurrentHashMap<Long, UserDto> idToUserMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> emailToIdMap = new ConcurrentHashMap<>();
    private final static AtomicLong userIdGen = new AtomicLong(0);

    @PostConstruct
    public void initData() {
        UserDto defaultUser = new UserDto();
        defaultUser.setUserId(userIdGen.incrementAndGet());
        defaultUser.setEmail("test@gmail.com");
        String hashedPW = BCrypt.hashpw("1234", BCrypt.gensalt());
        defaultUser.setPassword(hashedPW);
        defaultUser.setUsername("namjin");
        defaultUser.setClubId(null);
        defaultUser.setProfileImage(null);

        this.saveUser(defaultUser);
    }

    public UserDto saveUser(UserDto userDto){
        if(userDto.getUserId() == null){
            userDto.setUserId(userIdGen.incrementAndGet());
        }
        idToUserMap.put(userDto.getUserId(), userDto);
        emailToIdMap.put(userDto.getEmail(),userDto.getUserId());
        return userDto;
    }

    public Optional<UserDto> findById(Long userId){
        return Optional.ofNullable(idToUserMap.get(userId));
    }

    public Optional<UserDto> findByEmail(String email) {
        Long userId = emailToIdMap.get(email);
        return Optional.ofNullable(userId)
                .flatMap(this::findById);
    }

    public void deleteUser(Long userId) {
        UserDto removedUser = idToUserMap.remove(userId);
        if (removedUser != null) {
            emailToIdMap.remove(removedUser.getEmail());
        }
    }
}
