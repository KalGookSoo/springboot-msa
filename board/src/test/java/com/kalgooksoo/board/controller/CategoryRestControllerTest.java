package com.kalgooksoo.board.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kalgooksoo.board.command.CreateCategoryCommand;
import com.kalgooksoo.board.command.UpdateCategoryCommand;
import com.kalgooksoo.board.domain.Category;
import com.kalgooksoo.board.domain.CategoryType;
import com.kalgooksoo.board.repository.CategoryJpaRepository;
import com.kalgooksoo.board.repository.CategoryRepository;
import com.kalgooksoo.board.service.CategoryService;
import com.kalgooksoo.board.service.DefaultCategoryService;
import com.kalgooksoo.exception.ExceptionHandlingController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 카테고리 REST 컨트롤러 테스트
 */
@DataJpaTest
@ActiveProfiles("test")
class CategoryRestControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager entityManager;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        CategoryRepository categoryRepository = new CategoryJpaRepository(entityManager.getEntityManager());
        CategoryService categoryService = new DefaultCategoryService(categoryRepository);
        CategoryRestController categoryRestController = new CategoryRestController(categoryService);
        ExceptionHandlingController exceptionHandlingController = new ExceptionHandlingController();
        mockMvc = MockMvcBuilders.standaloneSetup(categoryRestController, exceptionHandlingController).build();
    }

    @Test
    @DisplayName("카테고리를 생성합니다. 성공 시 응답 코드 201을 반환합니다.")
    void createSouldReturnCreated() throws Exception {
        // Given
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name(), "admin");

        // When
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createCategoryCommand)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("카테고리를 생성합니다. 실패 시 응답 코드 400을 반환합니다.")
    void createSouldReturnBadRequest() throws Exception {
        // Given
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, null, CategoryType.PUBLIC.name(), "admin");

        // When
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createCategoryCommand)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("카테고리 목록을 조회합니다. 성공 시 응답 코드 200을 반환합니다.")
    void findAllSouldReturnOk() throws Exception {
        // Given
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name(), "admin");
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createCategoryCommand)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());

        // When
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("카테고리를 조회합니다. 성공 시 응답 코드 200을 반환합니다.")
    void findByIdShouldReturnOk() throws Exception {
        // Given
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name(), "admin");
        MockHttpServletResponse httpServletResponse = mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createCategoryCommand)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        EntityModel<Category> entityModel = mapper.readValue(httpServletResponse.getContentAsString(), new TypeReference<>() {});
        Category menu = entityModel.getContent();
        Assertions.assertThat(menu).isNotNull();

        // When
        mockMvc.perform(get("/categories/{id}", menu.getId()))
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
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name(), "admin");
        MockHttpServletResponse response = mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createCategoryCommand)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        EntityModel<Category> entityModel = mapper.readValue(response.getContentAsString(), new TypeReference<>() {});
        Category menu = entityModel.getContent();
        Assertions.assertThat(menu).isNotNull();

        UpdateCategoryCommand updateCategoryCommand = new UpdateCategoryCommand("공지사항 수정", CategoryType.PUBLIC.name());

        // When
        mockMvc.perform(put("/categories/{id}", menu.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateCategoryCommand)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("카테고리를 수정합니다. 실패 시 응답 코드 400을 반환합니다.")
    void updateShouldReturnBadRequest() throws Exception {
        // Given
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name(), "admin");
        MockHttpServletResponse response = mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createCategoryCommand)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        EntityModel<Category> entityModel = mapper.readValue(response.getContentAsString(), new TypeReference<>() {});
        Category menu = entityModel.getContent();
        Assertions.assertThat(menu).isNotNull();

        UpdateCategoryCommand updateCategoryCommand = new UpdateCategoryCommand(null, CategoryType.PUBLIC.name());

        // When
        mockMvc.perform(put("/categories/{id}", menu.getId())
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
    void deleteSouldReturnNoContent() throws Exception {
        // Given
        CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand(null, "공지사항", CategoryType.PUBLIC.name(), "admin");
        MockHttpServletResponse response = mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createCategoryCommand)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse();

        EntityModel<Category> entityModel = mapper.readValue(response.getContentAsString(), new TypeReference<>() {});
        Category menu = entityModel.getContent();
        Assertions.assertThat(menu).isNotNull();

        // When
        mockMvc.perform(delete("/categories/{id}", menu.getId()))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("카테고리를 삭제합니다. 존재하지 않는 카테고리 삭제 시 응답 코드 404를 반환합니다.")
    void deleteSouldReturnNotFound() throws Exception {
        // Given

        // When
        mockMvc.perform(delete("/categories/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

}