package com.kalgooksoo.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kalgooksoo.category.command.CreateCategoryCommand;
import com.kalgooksoo.category.command.UpdateCategoryCommand;
import com.kalgooksoo.category.domain.Category;
import com.kalgooksoo.category.domain.CategoryType;
import com.kalgooksoo.category.repository.CategoryMemoryRepository;
import com.kalgooksoo.category.repository.CategoryRepository;
import com.kalgooksoo.category.service.CategoryService;
import com.kalgooksoo.category.service.DefaultCategoryService;
import com.kalgooksoo.core.exception.ExceptionHandlingController;
import com.kalgooksoo.core.principal.PrincipalProvider;
import com.kalgooksoo.core.principal.StubPrincipalProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 카테고리 REST 컨트롤러 테스트
 */
class CategoryRestControllerTest {

    private CategoryRestController categoryRestController;

    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        CategoryRepository categoryRepository = new CategoryMemoryRepository();
        PrincipalProvider principalProvider = new StubPrincipalProvider();
        CategoryService categoryService = new DefaultCategoryService(categoryRepository, principalProvider);
        categoryRestController = new CategoryRestController(categoryService);
        ExceptionHandlingController exceptionHandlingController = new ExceptionHandlingController();
        mockMvc = MockMvcBuilders.standaloneSetup(categoryRestController, exceptionHandlingController).build();
    }

    @Test
    @DisplayName("카테고리를 생성합니다. 성공 시 응답 코드 201을 반환합니다.")
    void createShouldReturnCreated() throws Exception {
        // Given
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name());

        // When
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createCategoryCommand)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("카테고리를 생성합니다. 실패 시 응답 코드 400을 반환합니다.")
    void createShouldReturnBadRequest() throws Exception {
        // Given
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, null, CategoryType.PUBLIC.name());

        // When
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createCategoryCommand)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("카테고리 목록을 조회합니다. 성공 시 응답 코드 200을 반환합니다.")
    void findAllShouldReturnOk() throws Exception {
        // Given
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name());
        categoryRestController.create(createCategoryCommand);

        // When
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("카테고리를 조회합니다. 성공 시 응답 코드 200을 반환합니다.")
    void findByIdShouldReturnOk() throws Exception {
        // Given
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name());
        @SuppressWarnings("DataFlowIssue") Category category = categoryRestController.create(createCategoryCommand)
                .getBody()
                .getContent();

        // When
        assert category != null;
        mockMvc.perform(get("/categories/{id}", category.getId()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("카테고리를 조회합니다. 존재하지 않는 카테고리 조회 시 응답 코드 404를 반환합니다.")
    void findByIdShouldReturnNotFound() throws Exception {
        // Given

        // When
        mockMvc.perform(get("/categories/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("카테고리를 수정합니다. 성공 시 응답 코드 200을 반환합니다.")
    void updateShouldReturnOk() throws Exception {
        // Given
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name());
        @SuppressWarnings("DataFlowIssue") Category category = categoryRestController.create(createCategoryCommand)
                .getBody()
                .getContent();

        UpdateCategoryCommand updateCategoryCommand = new UpdateCategoryCommand("공지사항 수정", CategoryType.PUBLIC.name());

        // When
        assert category != null;
        mockMvc.perform(put("/categories/{id}", category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateCategoryCommand)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("카테고리를 수정합니다. 실패 시 응답 코드 400을 반환합니다.")
    void updateShouldReturnBadRequest() throws Exception {
        // Given
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name());
        @SuppressWarnings("DataFlowIssue") Category category = categoryRestController.create(createCategoryCommand)
                .getBody()
                .getContent();

        UpdateCategoryCommand updateCategoryCommand = new UpdateCategoryCommand(null, CategoryType.PUBLIC.name());

        // When
        assert category != null;
        mockMvc.perform(put("/categories/{id}", category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateCategoryCommand)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("카테고리를 수정합니다. 존재하지 않는 카테고리 수정 시 응답 코드 404를 반환합니다.")
    void updateShouldReturnNotFound() throws Exception {
        // Given
        UpdateCategoryCommand updateCategoryCommand = new UpdateCategoryCommand("공지사항 수정", CategoryType.PUBLIC.name());

        // When
        mockMvc.perform(put("/categories/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateCategoryCommand)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("카테고리를 삭제합니다. 성공 시 응답 코드 204를 반환합니다.")
    void deleteShouldReturnNoContent() throws Exception {
        // Given
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name());
        @SuppressWarnings("DataFlowIssue") Category category = categoryRestController.create(createCategoryCommand)
                .getBody()
                .getContent();

        // When
        assert category != null;
        mockMvc.perform(delete("/categories/{id}", category.getId()))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("카테고리를 삭제합니다. 존재하지 않는 카테고리 삭제 시 응답 코드 404를 반환합니다.")
    void deleteShouldReturnNotFound() throws Exception {
        // Given

        // When
        mockMvc.perform(delete("/categories/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

}