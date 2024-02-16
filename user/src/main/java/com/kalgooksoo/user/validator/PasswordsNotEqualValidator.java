package com.kalgooksoo.user.validator;

import com.kalgooksoo.user.annotation.PasswordsNotEqual;
import com.kalgooksoo.user.command.ChangeAccountPasswordCommand;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 패스워드가 같지 않아야 하는 제약 조건을 검증하는 클래스입니다.
 * 이 클래스는 ConstraintValidator 인터페이스를 구현하며, PasswordsNotEqual 애노테이션과 AccountPasswordCommand 클래스를 사용합니다.
 */
public class PasswordsNotEqualValidator implements ConstraintValidator<PasswordsNotEqual, ChangeAccountPasswordCommand> {

    /**
     * 제약 조건 초기화 메서드입니다.
     * 현재는 아무런 동작을 하지 않습니다.
     * @param constraintAnnotation 제약 조건 애노테이션
     */
    @Override
    public void initialize(PasswordsNotEqual constraintAnnotation) {
    }

    /**
     * 제약 조건이 유효한지 검증하는 메서드입니다.
     * 원래 패스워드와 새 패스워드가 같지 않은 경우에만 true를 반환합니다.
     * @param value 검증할 객체
     * @param context 제약 조건 컨텍스트
     * @return 제약 조건이 유효한 경우 true, 그렇지 않은 경우 false
     */
    @Override
    public boolean isValid(ChangeAccountPasswordCommand value, ConstraintValidatorContext context) {
        return !value.originPassword().equals(value.newPassword());
    }

}