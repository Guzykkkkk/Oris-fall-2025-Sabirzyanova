package org.example.FourthOctober.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileInfoDto {
    InputStream fileInputStream;

    String initialName;

    String mime;

    Long length;
}
