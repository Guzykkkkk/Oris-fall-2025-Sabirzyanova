package org.example.FourthOctober.model;

import lombok.Builder;
import lombok.Data;

@Data
    @Builder
    public class FileInfoEntity {

        private Long fileId;

        private String fileMime;

        private String fileInitialName;

        private String fileStorageName;

        private long fileLength;

        private byte[] entityFileInfo;
}
