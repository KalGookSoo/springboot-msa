package com.kalgooksoo.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kalgooksoo.board.command.CreateArticleCommand;
import com.kalgooksoo.board.command.MoveArticleCommand;
import com.kalgooksoo.board.command.UpdateArticleCommand;
import com.kalgooksoo.board.domain.Article;
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
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        // Given
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString());

        // When & Then
        mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createArticleCommand)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시글을 생성합니다. 실패 시 응답 코드 400을 반환합니다.")
    void createShouldReturnBadRequest() throws Exception {
        // Given
        CreateArticleCommand createArticleCommand = new CreateArticleCommand(null, "내용", UUID.randomUUID().toString());

        // When & Then
        mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createArticleCommand)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시글 목록을 조회합니다. 성공 시 응답 코드 200을 반환합니다.")
    void findAllShouldReturnOk() throws Exception {
        // Given
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString());
        articleRestController.create(createArticleCommand);

        // When & Then
        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시글을 조회합니다. 성공 시 응답 코드 200을 반환합니다.")
    void findByIdShouldReturnOk() throws Exception {
        // Given
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString());
        ResponseEntity<EntityModel<Article>> responseEntity = articleRestController.create(createArticleCommand);
        @SuppressWarnings("DataFlowIssue")
        String articleId = responseEntity.getBody()
                .getContent()
                .getId();

        // When & Then
        mockMvc.perform(get("/articles/" + articleId))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시글을 조회합니다. 존재하지 않는 게시글 조회 시 응답 코드 404를 반환합니다.")
    void findByIdShouldReturnNotFound() throws Exception {
        // Given
        String articleId = UUID.randomUUID().toString();

        // When & Then
        mockMvc.perform(get("/articles/{id}", articleId))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시글을 수정합니다. 성공 시 응답 코드 200을 반환합니다.")
    void updateByIdShouldReturnOk() throws Exception {
        // Given
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString());
        ResponseEntity<EntityModel<Article>> responseEntity = articleRestController.create(createArticleCommand);
        @SuppressWarnings("DataFlowIssue")
        String articleId = responseEntity.getBody()
                .getContent()
                .getId();
        UpdateArticleCommand updateArticleCommand = new UpdateArticleCommand("Updated title", "Updated content");

        // When & Then
        mockMvc.perform(put("/articles/{id}", articleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateArticleCommand)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시글을 수정합니다. 실패 시 응답 코드 400을 반환합니다.")
    void updateByIdShouldReturnBadRequest() throws Exception {
        // Given
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString());
        ResponseEntity<EntityModel<Article>> responseEntity = articleRestController.create(createArticleCommand);
        @SuppressWarnings("DataFlowIssue")
        String articleId = responseEntity.getBody()
                .getContent()
                .getId();
        UpdateArticleCommand updateArticleCommand = new UpdateArticleCommand(null, "Updated content");

        // When & Then
        mockMvc.perform(put("/articles/{id}", articleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateArticleCommand)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시글을 수정합니다. 존재하지 않는 게시글 수정 시 응답 코드 404를 반환합니다.")
    void updateByIdShouldReturnNotFound() throws Exception {
        // Given
        String articleId = UUID.randomUUID().toString();
        UpdateArticleCommand updateArticleCommand = new UpdateArticleCommand("Updated title", "Updated content");

        // When & Then
        mockMvc.perform(put("/articles/{id}", articleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateArticleCommand)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시글을 삭제합니다. 성공 시 응답 코드 204를 반환합니다.")
    void deleteByIdShouldReturnNoContent() throws Exception {
        // Given
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString());
        ResponseEntity<EntityModel<Article>> responseEntity = articleRestController.create(createArticleCommand);
        @SuppressWarnings("DataFlowIssue")
        String articleId = responseEntity.getBody()
                .getContent()
                .getId();

        // When & Then
        mockMvc.perform(delete("/articles/{id}", articleId))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시글을 삭제합니다. 존재하지 않는 게시글 삭제 시 응답 코드 404를 반환합니다.")
    void deleteByIdShouldReturnNotFound() throws Exception {
        // Given
        String articleId = UUID.randomUUID().toString();

        // When & Then
        mockMvc.perform(delete("/articles/{id}", articleId))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시글을 이동합니다. 성공 시 응답 코드 200을 반환합니다.")
    void moveByIdShouldReturnOk() throws Exception {
        // Given
        CreateArticleCommand createArticleCommand = new CreateArticleCommand("제목", "내용", UUID.randomUUID().toString());
        ResponseEntity<EntityModel<Article>> responseEntity = articleRestController.create(createArticleCommand);
        @SuppressWarnings("DataFlowIssue")
        String articleId = responseEntity.getBody()
                .getContent()
                .getId();
        MoveArticleCommand moveArticleCommand = new MoveArticleCommand(UUID.randomUUID().toString());

        // When & Then
        mockMvc.perform(put("/articles/{id}/move", articleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(moveArticleCommand)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("게시글을 이동합니다. 존재하지 않는 게시글 이동 시 응답 코드 404를 반환합니다.")
    void moveByIdShouldReturnNotFound() throws Exception {
        // Given
        String articleId = UUID.randomUUID().toString();
        MoveArticleCommand moveArticleCommand = new MoveArticleCommand(UUID.randomUUID().toString());

        // When & Then
        mockMvc.perform(put("/articles/{id}/move", articleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(moveArticleCommand)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

}