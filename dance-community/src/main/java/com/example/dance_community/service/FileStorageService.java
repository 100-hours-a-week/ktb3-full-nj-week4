package com.example.dance_community.service;

import com.example.dance_community.config.FileProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {
    private final FileProperties fileProperties;

    public String saveProfileImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return fileProperties.getDefaultProfile();
        }

        try {
            String filename = generateFileName(file.getOriginalFilename());
            Path uploadPath = getProfileUploadPath();

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return String.format("/%s/%s/%s",
                    fileProperties.getBaseDir(),
                    fileProperties.getProfileDir(),
                    filename);

        } catch (IOException e) {
            throw new RuntimeException("프로필 이미지 저장 실패: " + e.getMessage(), e);
        }
    }

    public String savePostImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("이미지 파일이 없습니다");
        }

        try {
            String filename = generateFileName(file.getOriginalFilename());
            Path uploadPath = Paths.get(fileProperties.getBaseDir(), fileProperties.getPostDir());

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return String.format("/%s/%s/%s",
                    fileProperties.getBaseDir(),
                    fileProperties.getPostDir(),
                    filename);

        } catch (IOException e) {
            throw new RuntimeException("게시글 이미지 저장 실패: " + e.getMessage(), e);
        }
    }

    public void deleteFile(String filePath) {
        if (filePath == null || filePath.equals(fileProperties.getDefaultProfile())) {
            return;
        }

        try {
            String actualPath = filePath.startsWith("/") ? filePath.substring(1) : filePath;
            Path path = Paths.get(actualPath);

            if (Files.exists(path)) {
                Files.delete(path);
                System.out.println("파일 삭제 완료: " + filePath);
            }
        } catch (IOException e) {
            System.err.println("파일 삭제 실패: " + filePath + " - " + e.getMessage());
            // 삭제 실패는 치명적이지 않으므로 예외를 던지지 않음
        }
    }

    private String generateFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "_" + originalFilename;
    }

    private Path getProfileUploadPath() {
        return Paths.get(fileProperties.getBaseDir(), fileProperties.getProfileDir());
    }
}