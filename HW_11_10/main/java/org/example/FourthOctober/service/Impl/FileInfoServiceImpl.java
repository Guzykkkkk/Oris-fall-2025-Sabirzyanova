package org.example.FourthOctober.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.FourthOctober.dto.FileInfoDto;
import org.example.FourthOctober.model.FileInfoEntity;
import org.example.FourthOctober.repository.FileInfoRepository;
import org.example.FourthOctober.service.FileInfoService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
public class FileInfoServiceImpl implements FileInfoService {
    private final FileInfoRepository fileInfoRepository;

    @SneakyThrows
    @Override
    public String save(FileInfoDto dto) throws IOException {
        String fileExtension = "";
        if (dto.getInitialName() != null && dto.getInitialName().contains(".")) {
            fileExtension = dto.getInitialName().substring(dto.getInitialName().lastIndexOf("."));
        }
        String storageName = UUID.randomUUID() + fileExtension;

        FileInfoEntity fileInfoEntity = FileInfoEntity.builder()
                .fileMime(dto.getMime())
                .fileInitialName(dto.getInitialName())
                .fileLength(dto.getLength())
                .entityFileInfo(dto.getFileInputStream().readAllBytes())
                .fileStorageName(storageName)
                .build();

        fileInfoRepository.save(fileInfoEntity);
        return fileInfoEntity.getFileStorageName();
    }

    @Override
    public FileInfoDto findByStorageName(String storageName) {
        Optional<FileInfoEntity> fileInfoEntity = fileInfoRepository.findByStorageName(storageName);
        if (fileInfoEntity.isEmpty()) {
            throw new RuntimeException("File not found with storage name: " + storageName);
        }

        FileInfoEntity infoEntity = fileInfoEntity.get();
        return FileInfoDto.builder()
                .mime(infoEntity.getFileMime())
                .length(infoEntity.getFileLength())
                .initialName(infoEntity.getFileInitialName())
                .build();
    }

    @Override
    public void writeFile(String storageName, OutputStream outputStream) {
        FileInfoEntity fileInfoEntity = fileInfoRepository.findByStorageName(storageName)
                .orElseThrow(() -> new RuntimeException("File not found: " + storageName));
        try {
            outputStream.write(fileInfoEntity.getEntityFileInfo());
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsByStorageName(String storageName) {
        return fileInfoRepository.existsByStorageName(storageName);
    }
}