package com.aks.study.file.uilts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InputStream;
@Getter
@AllArgsConstructor
public class ZipDto {
    private final String fileName;
    private final InputStream inputStream;

    public static ZipDto create(String fileName, InputStream inputStream) {
        return new ZipDto(fileName, inputStream);
    }

}
