package org.example.FourthOctober.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.FourthOctober.dto.FileInfoDto;
import org.example.FourthOctober.model.FileInfoEntity;
import org.example.FourthOctober.model.UserEntity;
import org.example.FourthOctober.repository.FileInfoRepository;
import org.example.FourthOctober.repository.UserRepository;
import org.example.FourthOctober.service.AvatharService;
import org.example.FourthOctober.service.FileInfoService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AvatharServiceImple implements AvatharService {
    private final FileInfoService fileInfoService;
    private final FileInfoRepository fileInfoRepository;
    private final UserRepository userRepository;

    @Override
    public List<FileInfoDto> getAllUsersWithAvatars() {
        List<UserEntity> users = userRepository.findAllWithAvatars();

        return users.stream()
                .map(user -> {
                    if (user.getAvatarFile() != null) {
                        Optional<FileInfoEntity> fileInfo = fileInfoRepository.findById(user.getAvatarFile());
                        if (fileInfo.isPresent()) {
                            FileInfoEntity avatar = fileInfo.get();
                            return FileInfoDto.builder()
                                    .initialName(avatar.getFileInitialName())
                                    .mime(avatar.getFileMime())
                                    .length(avatar.getFileLength())
                                    .build();
                        }
                    }
                    return FileInfoDto.builder()
                            .initialName("default-avatar.png")
                            .mime("image/png")
                            .length(0L)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public void updateUserAvathar(Long userId, FileInfoDto fileInfoDto) {
        try {
            String storageName = fileInfoService.save(fileInfoDto);

            Optional<FileInfoEntity> savedFile = fileInfoRepository.findByStorageName(storageName);
            if (savedFile.isEmpty()) {
                throw new RuntimeException("Failed to save avatar file");
            }

            Long avatarFileId = savedFile.get().getFileId();
            userRepository.avatarUpdate(userId, avatarFileId);

        } catch (IOException e) {
            throw new RuntimeException("Failed to update user avatar", e);
        }
    }
}