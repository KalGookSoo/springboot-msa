package com.kalgooksoo.attachment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

/**
 * 첨부파일
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "tb_attachment")
@DynamicInsert
public class Attachment {

    /**
     * 식별자
     */
    @Id
    private String id;

    /**
     * 참조 식별자
     */
    private String referenceId;

    /**
     * 이름
     */
    private String name;

    /**
     * 경로명
     */
    private String pathName;

    /**
     * MIME 타입
     */
    private String mimeType;

    /**
     * 크기
     */
    private long size;

    /**
     * 생성 일시
     */
    private LocalDateTime createdAt;

    /**
     * 첨부파일을 생성합니다.
     *
     * @param referenceId 참조 식별자
     * @param name        이름
     * @param pathName    경로명
     * @param mimeType    MIME 타입
     * @param size        크기
     * @return 첨부파일
     */
    public static Attachment create(String referenceId, String name, String pathName, String mimeType, long size) {
        Attachment attachment = new Attachment();
        attachment.id = UUID.randomUUID().toString();
        attachment.referenceId = referenceId;
        attachment.name = name;
        attachment.pathName = pathName;
        attachment.mimeType = mimeType;
        attachment.size = size;
        attachment.createdAt = LocalDateTime.now();
        return attachment;
    }

}