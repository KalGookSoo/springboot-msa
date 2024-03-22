package com.kalgooksoo.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kalgooksoo.board.repository.ArticleMemoryRepository;
import com.kalgooksoo.board.repository.ArticleRepository;
import com.kalgooksoo.board.service.ArticleService;
import com.kalgooksoo.board.service.DefaultArticleService;
import com.kalgooksoo.core.exception.ExceptionHandlingController;
import com.kalgooksoo.core.principal.PrincipalProvider;
import com.kalgooksoo.core.principal.StubPrincipalProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 게시글 REST 컨트롤러 테스트
 */
class ArticleRestControllerTest {

    private ArticleRestController articleRestController;

    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        ArticleRepository articleRepository = new ArticleMemoryRepository();
        PrincipalProvider principalProvider = new StubPrincipalProvider();
        ArticleService articleService = new DefaultArticleService(articleRepository, principalProvider);
        articleRestController = new ArticleRestController(articleService);
        ExceptionHandlingController exceptionHandlingController = new ExceptionHandlingController();
        mockMvc = MockMvcBuilders.standaloneSetup(articleRestController, exceptionHandlingController).build();
    }

    @Test
    @DisplayName("게시글을 생성합니다. 성공 시 응답 코드 201을 반환합니다.")
    void createShouldReturnCreated() throws Exception {

    }

    @Test
    @DisplayName("게시글을 생성합니다. 실패 시 응답 코드 400을 반환합니다.")
    void createShouldReturnBadRequest() throws Exception {

    }

    @Test
    @DisplayName("게시글 목록을 조회합니다. 성공 시 응답 코드 200을 반환합니다.")
    void findAllShouldReturnOk() throws Exception {

    }

    @Test
    @DisplayName("게시글을 조회합니다. 성공 시 응답 코드 200을 반환합니다.")
    void findByIdShouldReturnOk() throws Exception {

    }

    @Test
    @DisplayName("게시글을 조회합니다. 존재하지 않는 게시글 조회 시 응답 코드 404를 반환합니다.")
    void findByIdShouldReturnNotFound() throws Exception {

    }

    @Test
    @DisplayName("게시글을 수정합니다. 성공 시 응답 코드 200을 반환합니다.")
    void updateByIdShouldReturnOk() throws Exception {

    }

    @Test
    @DisplayName("게시글을 수정합니다. 실패 시 응답 코드 400을 반환합니다.")
    void updateByIdShouldReturnBadRequest() throws Exception {

    }

    @Test
    @DisplayName("게시글을 수정합니다. 존재하지 않는 게시글 수정 시 응답 코드 404를 반환합니다.")
    void updateByIdShouldReturnNotFound() throws Exception {

    }

    @Test
    @DisplayName("게시글을 삭제합니다. 성공 시 응답 코드 204를 반환합니다.")
    void deleteByIdShouldReturnNoContent() throws Exception {

    }

    @Test
    @DisplayName("게시글을 삭제합니다. 존재하지 않는 게시글 삭제 시 응답 코드 404를 반환합니다.")
    void deleteByIdShouldReturnNotFound() throws Exception {

    }

    @Test
    @DisplayName("게시글을 이동합니다. 성공 시 응답 코드 200을 반환합니다.")
    void moveByIdShouldReturnOk() throws Exception {

    }

    @Test
    @DisplayName("게시글을 이동합니다. 존재하지 않는 게시글 이동 시 응답 코드 404를 반환합니다.")
    void moveByIdShouldReturnNotFound() throws Exception {

    }


}