package org.example.FourthOctober.repository;

import org.example.FourthOctober.model.FileInfoEntity;

import java.util.Optional;

public interface FileInfoRepository {
    Optional<FileInfoEntity> findById(Long id);

    Optional<FileInfoEntity> findByStorageName(String storageName);

    void save(FileInfoEntity fileInfo);

    boolean existsByStorageName(String storageName);
}
