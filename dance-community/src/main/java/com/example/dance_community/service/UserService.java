package com.example.dance_community.service;

import com.example.dance_community.dto.user.UserDto;
import com.example.dance_community.dto.user.UserRequest;
import com.example.dance_community.exception.InvalidRequestException;
import com.example.dance_community.exception.NotFoundException;
import com.example.dance_community.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserDto getUser(Long userId) {
        if (userId == null) {
            throw new InvalidRequestException("사용자 없음");
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("사용자 인증 실패"));
    }

    public UserDto getUserById(Long userId) {
        return UserDto.changePassword(getUser(userId));
    }

    public UserDto updateUser(Long userId, UserRequest userRequest) {
        UserDto userDto = getUser(userId);

        String newPassword = userRequest.getPassword() != null
                ? BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt())
                : userDto.getPassword();

        UserDto updatedUser = userDto.toBuilder()
                .password(newPassword)
                .username(userRequest.getUsername() != null ? userRequest.getUsername() : userDto.getUsername())
                .clubId(userRequest.getClubId() != null ? userRequest.getClubId() : userDto.getClubId())
                .profileImage(userRequest.getProfileImage() != null ? userRequest.getProfileImage() : userDto.getProfileImage())
                .build();

        userRepository.saveUser(updatedUser);
        return UserDto.changePassword(updatedUser);
    }

    public void deleteCurrentUser(Long userId) {
        getUser(userId);
        userRepository.deleteUser(userId);
    }
}


//TODO : @Transactional 반영하기 서비스에서만 쓰는건지? 수정? 삭제?