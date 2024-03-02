package com.kalgooksoo.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kalgooksoo.user.value.ContactNumber;
import com.kalgooksoo.user.value.Email;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

/**
 * 계정
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "tb_user")
@DynamicInsert
public class User {

    /**
     * 계정 식별자
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
    @JsonIgnore
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
            @AttributeOverride(name = "domain", column = @Column(name = "email_domain"))
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
        user.id = UUID.randomUUID().toString();
        user.username = username;
        user.password = password;
        user.name = name;
        user.email = email;
        user.contactNumber = contactNumber;
        user.createdAt = LocalDateTime.now();
        user.initializeAccountPolicy();
        return user;
    }

    public void update(String name, Email email, ContactNumber contactNumber) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.modifiedAt = LocalDateTime.now();
    }

    public void changePassword(String password) {
        Assert.notNull(password, "패스워드는 null이 될 수 없습니다.");
        this.password = password;
        this.credentialsExpiredAt = LocalDate.now().atTime(LocalTime.MIDNIGHT).plusDays(180L);
        this.modifiedAt = LocalDateTime.now();
    }

    /**
     * 만료 일시는 금일(00:00)로부터 2년 후 까지로 설정합니다.
     * 패스워드 만료 일시는 생성일(00:00)로부터 180일 후 까지로 설정합니다.
     */
    private void initializeAccountPolicy() {
        expiredAt = LocalDate.now().atTime(LocalTime.MIDNIGHT).plusYears(2L);
        credentialsExpiredAt = LocalDate.now().atTime(LocalTime.MIDNIGHT).plusDays(180L);
    }

    /**
     * 계정이 만료되지 않았는지 여부를 반환합니다.
     * @return 계정이 만료되지 않았는지 여부
     */
    public boolean isAccountNonExpired() {
        return expiredAt == null || expiredAt.isAfter(LocalDateTime.now());
    }

    /**
     * 계정이 잠겨있지 않은지 여부를 반환합니다.
     * @return 계정이 잠겨있지 않은지 여부
     */
    public boolean isAccountNonLocked() {
        return lockedAt == null || lockedAt.isBefore(LocalDateTime.now());
    }

    /**
     * 계정의 패스워드가 만료되지 않았는지 여부를 반환합니다.
     * @return 계정의 패스워드가 만료되지 않았는지 여부
     */
    public boolean isCredentialsNonExpired() {
        return credentialsExpiredAt == null || credentialsExpiredAt.isAfter(LocalDateTime.now());
    }

    /**
     * 계정이 사용 가능한지 여부를 반환합니다.
     * @return 계정이 사용 가능한지 여부
     */
    @SuppressWarnings("unused")
    public boolean isEnabled() {
        boolean accountNonLocked = isAccountNonLocked();
        boolean accountNonExpired = isAccountNonExpired();
        boolean credentialsNonExpired = isCredentialsNonExpired();
        return accountNonLocked && accountNonExpired && credentialsNonExpired;
    }

}