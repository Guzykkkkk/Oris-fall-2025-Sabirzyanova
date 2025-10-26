package org.example.FourthOctober.service;

import org.example.FourthOctober.dto.FileInfoDto;

import java.io.IOException;
import java.io.OutputStream;

public interface FileInfoService {
    String save(FileInfoDto dto) throws IOException;

    FileInfoDto findByStorageName(String storageName);

    void writeFile(String storageName, OutputStream outputStream);

    boolean existsByStorageName(String storageName);
}
