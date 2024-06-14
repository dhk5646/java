package com.hyeyeong.java.zip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InputStream;

public record ZipDto(String fileName, InputStream inputStream) {
    public static ZipDto create(String fileName, InputStream inputStream) {
        return new ZipDto(fileName, inputStream);
    }

}
