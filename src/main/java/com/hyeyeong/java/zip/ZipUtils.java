package com.hyeyeong.java.zip;

import com.hyeyeong.java.zip.dto.ZipDto;
import lombok.experimental.UtilityClass;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 파일 압축 클래스
 * */
@UtilityClass
public class ZipUtils {

    private static final Logger logger = Logger.getLogger(ZipUtils.class.getName());

    private static final int BUFFER_SIZE = 8192; // 8KB
    private static final String COMPRESSION_TEMP_DIRECTORY = "compression-";
    private static final String ZIP_EXTENSION = "zip";


    public static InputStream compress(List<ZipDto> zipDtos) throws IOException {

        if(zipDtos == null || zipDtos.isEmpty()) {
            throw new IllegalArgumentException("files is not exist");
        }

        // 압축 파일 경로를 생성한다.
        Path zipFilePath = createZipPath();

        try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFilePath.toString())))) {

            for (ZipDto file : zipDtos) {
                addToZipFile(zos, file);
            }

            zos.finish();

            return Files.newInputStream(zipFilePath);

        } finally {
            logger.info("compression file path:" + zipFilePath);
            deleteTempFiles(zipFilePath);
        }
    }

    private static Path createZipPath() throws IOException {
        Path directory = Files.createTempDirectory(COMPRESSION_TEMP_DIRECTORY);
        String fileName = String.format("%s.%s", UUID.randomUUID(), ZIP_EXTENSION);
        return directory.resolve(fileName);
    }

    private static void addToZipFile(ZipOutputStream zos, ZipDto file) throws IOException {

        ZipEntry zipEntry = new ZipEntry(file.fileName());
        zos.putNextEntry(zipEntry);

        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = file.inputStream().read(buffer)) != -1) {
                zos.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new IOException("Error reading file: " + file.fileName(), e);
        } finally {
            if(file.inputStream() != null) {
                file.inputStream().close();
            }
            zos.closeEntry();
        }
    }

    private static void deleteTempFiles(Path zipPath) {
            try {
                Files.walk(zipPath.getParent())
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                // 오류가 발생해도 무시
            }

    }
}
