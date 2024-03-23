package com.kalgooksoo.core.file;

import org.springframework.core.io.InputStreamResource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 파일 입출력 서비스
 */
public interface FileIOService {

    /**
     * 파일을 저장합니다.
     *
     * @param pathname     저장할 파일명
     * @param data         저장할 파일의 데이터(byte 배열)
     * @throws IOException 파일 저장 중에 I/O 오류가 발생한 경우
     */
    static void write(final String pathname, final byte[] data) throws IOException {
        File file = new File(pathname);
        FileCopyUtils.copy(data, file);
    }

    /**
     * 해당 경로의 파일을 다운로드하여 반환합니다.
     *
     * @param filePath     다운로드할 파일의 경로
     * @return             다운로드한 파일을 나타내는 InputStreamResource
     * @throws IOException 파일을 읽는 동안 I/O 오류가 발생한 경우
     */
    static InputStreamResource download(final String filePath) throws IOException {
        return new InputStreamResource(Files.newInputStream(Paths.get(filePath)));
    }

    /**
     * 해당 파일을 물리적으로 삭제합니다.
     *
     * @param pathname 삭제할 파일의 경로
     * @return         삭제 성공유무
     */
    static boolean delete(final String pathname) {
        File file = new File(pathname);
        return file.delete();
    }

}