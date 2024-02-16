package com.kalgooksoo.user.domain;

import com.kalgooksoo.user.value.ContactNumber;
import com.kalgooksoo.user.value.Email;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = {"id"})

@Entity
@Table(name = "tb_account")
@DynamicInsert
public class User {

    /**
     * 엔티티 식별자입니다.
     * UUID를 사용하여 고유한 값을 가집니다.
     */
    @Id
    private String id;

    /**
     * 계정명
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * 패스워드
     */
    private String password;

    /**
     * 이름
     */
    private String name;

    /**
     * 이메일
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "email_id")),
            @AttributeOverride(name = "com/kalgooksoo/user/domain", column = @Column(name = "email_domain"))
    })
    private Email email;

    /**
     * 연락처
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "first", column = @Column(name = "first_contact_number")),
            @AttributeOverride(name = "middle", column = @Column(name = "middle_contact_number")),
            @AttributeOverride(name = "last", column = @Column(name = "last_contact_number"))
    })
    private ContactNumber contactNumber;

    /**
     * 권한 목록
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private final List<Authority> authorities = new ArrayList<>();

    /**
     * 엔티티가 생성된 시간입니다.
     * 엔티티가 데이터베이스에 처음 저장될 때의 시간을 자동으로 저장합니다.
     */
    private LocalDateTime createdAt;

    /**
     * 엔티티가 마지막으로 수정된 시간입니다.
     * 엔티티가 데이터베이스에 저장될 때마다 시간을 자동으로 업데이트합니다.
     */
    private LocalDateTime modifiedAt;

    /**
     * 만료 일시
     */
    private LocalDateTime expiredAt;

    /**
     * 잠금 일시
     */
    private LocalDateTime lockedAt;

    /**
     * 패스워드 만료 일시
     */
    private LocalDateTime credentialsExpiredAt;

    public static User create(String username, String password, String name, Email email, ContactNumber contactNumber) {
        User user = new User();
        user.username = username;
        user.password = password;
        user.name = name;
        user.email = email;
        user.contactNumber = contactNumber;
        user.createdAt = LocalDateTime.now();
        user.initializeAccountPolicy();
        return user;
    }

    public User update(String name, Email email, ContactNumber contactNumber) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.modifiedAt = LocalDateTime.now();
        return this;
    }

    public void changePassword(String password) {
        Assert.notNull(password, "Password must not be null");
        this.password = password;
        this.credentialsExpiredAt = LocalDate.now().atTime(LocalTime.MIDNIGHT).plusDays(180L);
    }

    /**
     * 만료 일시는 금일(00:00)로부터 2년 후 까지로 설정합니다.
     * 패스워드 만료 일시는 생성일(00:00)로부터 180일 후 까지로 설정합니다.
     */
    private void initializeAccountPolicy() {
        expiredAt = LocalDate.now().atTime(LocalTime.MIDNIGHT).plusYears(2L);
        credentialsExpiredAt = LocalDate.now().atTime(LocalTime.MIDNIGHT).plusDays(180L);
    }
}
