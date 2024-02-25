package com.kalgooksoo.user.service;

import com.kalgooksoo.user.command.UpdateUserCommand;
import com.kalgooksoo.user.domain.Authority;
import com.kalgooksoo.user.domain.User;
import com.kalgooksoo.user.exception.UsernameAlreadyExistsException;
import com.kalgooksoo.user.model.UserPrincipal;
import com.kalgooksoo.user.search.UserSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 계정 서비스
 */
public interface UserService {

    /**
     * 계정 생성
     *
     * @param user 계정
     * @return 생성된 계정
     * @throws UsernameAlreadyExistsException 계정이 이미 존재하는 경우
     */
    User createUser(User user) throws UsernameAlreadyExistsException;

    /**
     * 관리자 계정 생성
     *
     * @param user 계정
     * @return 생성된 계정
     * @throws UsernameAlreadyExistsException 계정이 이미 존재하는 경우
     */
    User createAdmin(User user) throws UsernameAlreadyExistsException;

    /**
     * 계정 수정
     *
     * @param id      계정 식별자
     * @param command 수정 명령
     * @return 수정된 계정
     */
    User update(String id, UpdateUserCommand command);

    /**
     * 계정 식별자로 계정 조회
     *
     * @param id 계정 식별자
     * @return 계정
     */
    Optional<User> findById(String id);

    /**
     * 계정 목록 조회
     *
     * @param pageable 페이지 정보
     * @return 계정 목록
     */
    Page<User> findAll(Pageable pageable);

    /**
     * 계정 목록 조회
     *
     * @param search   검색 조건
     * @param pageable 페이지 정보
     * @return 계정 목록
     */
    Page<User> findAll(UserSearch search, Pageable pageable);

    /**
     * 계정 삭제
     *
     * @param id 계정 식별자
     */
    void deleteById(String id);

    /**
     * 패스워드 변경
     *
     * @param id             계정 식별자
     * @param originPassword 기존 패스워드
     * @param newPassword    새로운 패스워드
     */
    void updatePassword(String id, String originPassword, String newPassword);

    /**
     * 계정 검증
     *
     * @param username 계정명
     * @param password 패스워드
     * @return 계정
     */
    UserPrincipal verify(String username, String password);

    /**
     * 계정 식별자로 권한 조회
     *
     * @param userId 계정 식별자
     * @return 권한 목록
     */
    List<Authority> findAuthoritiesByUserId(String userId);

}