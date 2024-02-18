package com.kalgooksoo.user.exception;

import com.kalgooksoo.user.value.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 예외 처리를 담당하는 컨트롤러 어드바이스 클래스입니다.
 */
@ControllerAdvice
public class ExceptionHandlingController {

    private static final String ERROR_KEY = "errors";

    /**
     * MethodArgumentNotValidException 예외를 처리하는 메서드입니다.
     * 이 예외는 유효하지 않은 메서드 인자가 전달될 때 발생합니다.
     *
     * @param ex 발생한 MethodArgumentNotValidException 예외
     * @return 에러 정보를 담은 ResponseEntity 객체를 반환합니다. 상태 코드는 BAD_REQUEST(400)입니다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationError(fieldError.getCode(), fieldError.getDefaultMessage(), fieldError.getField(), fieldError.getRejectedValue()))
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(ERROR_KEY, errors));
    }

    /**
     * UsernameAlreadyExistsException 예외를 처리하는 메서드입니다.
     * 이 예외는 이미 존재하는 사용자 이름으로 사용자를 생성하려고 할 때 발생합니다.
     *
     * @param ex 발생한 UsernameAlreadyExistsException 예외
     * @return 에러 정보를 담은 ResponseEntity 객체를 반환합니다. 상태 코드는 CONFLICT(409)입니다.
     */
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        List<ValidationError> errors = Collections.singletonList(new ValidationError(ex.getClass().getSimpleName(), ex.getMessage(), ex.getField(), ex.getRejectedValue()));

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(ERROR_KEY, errors));
    }
}