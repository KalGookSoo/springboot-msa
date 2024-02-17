package com.kalgooksoo.user.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

/**
 * 예외 처리를 담당하는 컨트롤러 어드바이스 클래스입니다.
 */
@ControllerAdvice
public class ExceptionHandlingController {

    /**
     * MethodArgumentNotValidException 예외를 처리하는 메서드입니다.
     * 이 예외는 요청 본문의 유효성 검사가 실패했을 때 발생합니다.
     * 모든 오류 메시지를 개행 문자로 연결하여 응답 본문으로 반환합니다.
     *
     * @param ex 발생한 MethodArgumentNotValidException 예외
     * @return 오류 메시지를 담은 ResponseEntity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getBindingResult().getAllErrors());
    }

    /**
     * UsernameAlreadyExistsException 예외를 처리하는 메서드입니다.
     * 이 예외는 사용자 이름이 이미 존재할 때 발생합니다.
     * 예외 메시지를 응답 본문으로 반환합니다.
     *
     * @param ex 발생한 UsernameAlreadyExistsException 예외
     * @return 오류 메시지를 담은 ResponseEntity
     */
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }
}