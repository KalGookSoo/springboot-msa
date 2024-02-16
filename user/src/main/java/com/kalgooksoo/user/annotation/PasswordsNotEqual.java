package com.kalgooksoo.user.annotation;

import com.kalgooksoo.user.validator.PasswordsNotEqualValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 패스워드가 같지 않아야 하는 제약 조건을 나타내는 애노테이션입니다.
 * 이 애노테이션은 PasswordsNotEqualValidator에 의해 검증됩니다.
 * 이 애노테이션은 클래스 레벨에서 사용할 수 있습니다.
 * 이 애노테이션은 런타임에 유지됩니다.
 */
@Documented
@Constraint(validatedBy = PasswordsNotEqualValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordsNotEqual {

    /**
     * 제약 조건 위반 시 반환되는 기본 메시지입니다.
     * @return 기본 메시지
     */
    String message() default "Passwords must not be equal";

    /**
     * 제약 조건이 속하는 그룹입니다.
     * @return 그룹
     */
    Class<?>[] groups() default {};

    /**
     * 제약 조건 위반 시 연관된 페이로드를 반환합니다.
     * @return 페이로드
     */
    Class<? extends Payload>[] payload() default {};

}