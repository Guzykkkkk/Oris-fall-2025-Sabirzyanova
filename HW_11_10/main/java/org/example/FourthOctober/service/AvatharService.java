package org.example.FourthOctober.service;

import org.example.FourthOctober.dto.FileInfoDto;

import java.util.List;


public interface AvatharService {
    List<FileInfoDto> getAllUsersWithAvatars();
    void updateUserAvathar(Long userId, FileInfoDto fileInfoDto);
}
