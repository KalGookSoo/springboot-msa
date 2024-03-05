package com.kalgooksoo.menu.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kalgooksoo.exception.ExceptionHandlingController;
import com.kalgooksoo.menu.command.MenuCommand;
import com.kalgooksoo.menu.domain.Menu;
import com.kalgooksoo.menu.repository.MenuJpaRepository;
import com.kalgooksoo.menu.repository.MenuRepository;
import com.kalgooksoo.menu.service.DefaultMenuService;
import com.kalgooksoo.menu.service.MenuService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 메뉴 REST 컨트롤러 테스트
 */
@DataJpaTest
@ActiveProfiles("test")
class MenuRestControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager entityManager;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        MenuRepository menuRepository = new MenuJpaRepository(entityManager.getEntityManager());
        MenuService menuService = new DefaultMenuService(menuRepository);
        MenuRestController menuRestController = new MenuRestController(menuService);
        ExceptionHandlingController exceptionHandlingController = new ExceptionHandlingController();
        mockMvc = MockMvcBuilders.standaloneSetup(menuRestController, exceptionHandlingController).build();
    }

    @Test
    @DisplayName("메뉴를 생성합니다. 성공 시 응답 코드 201을 반환합니다.")
    void createSouldReturnCreated() throws Exception {
        // Given
        MenuCommand command = new MenuCommand("오시는 길", "http://www.kalgooksoo.com/categories/2/articles", null, "anonymous");

        // When
        mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("오시는 길"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("메뉴를 생성합니다. 실패 시 응답 코드 400을 반환합니다.")
    void createSouldReturnBadRequest() throws Exception {
        // Given
        MenuCommand command = new MenuCommand(null, "http://www.kalgooksoo.com/categories/2/articles", null, "anonymous");

        // When
        mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("메뉴 목록을 조회합니다. 성공 시 응답 코드 200을 반환합니다.")
    void findAllShouldReturnOk() throws Exception {
        // Given
        MenuCommand command = new MenuCommand("오시는 길", "http://www.kalgooksoo.com/categories/2/articles", null, "anonymous");
        mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("오시는 길"))
                .andDo(MockMvcResultHandlers.print());

        // When
        mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("오시는 길"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("메뉴를 조회합니다. 성공 시 응답 코드 200을 반환합니다.")
    void findByIdShouldReturnOk() throws Exception {
        // Given
        MenuCommand command = new MenuCommand("오시는 길", "http://www.kalgooksoo.com/categories/2/articles", null, "anonymous");
        MockHttpServletResponse httpServletResponse = mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("오시는 길"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        EntityModel<Menu> entityModel = mapper.readValue(httpServletResponse.getContentAsString(), new TypeReference<>() {});
        Menu menu = entityModel.getContent();
        Assertions.assertThat(menu).isNotNull();

        // When
        mockMvc.perform(get("/menus/{id}", menu.getId()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("메뉴를 조회합니다. 존재하지 않는 메뉴 조회 시 응답 코드 404를 반환합니다.")
    void findByIdShouldReturnNotFound() throws Exception {
        // Given

        // When
        mockMvc.perform(get("/menus/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("메뉴를 수정합니다. 성공 시 응답 코드 200을 반환합니다.")
    void updateByIdShouldReturnOk() throws Exception {
        // Given
        MenuCommand command = new MenuCommand("오시는 길", "http://www.kalgooksoo.com/categories/2/articles", null, "anonymous");
        MockHttpServletResponse response = mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("오시는 길"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse();

        EntityModel<Menu> entityModel = mapper.readValue(response.getContentAsString(), new TypeReference<>() {});
        Menu menu = entityModel.getContent();
        Assertions.assertThat(menu).isNotNull();

        MenuCommand updateMenuCommand = new MenuCommand("공지사항", "http://www.kalgooksoo.com/categories/1/articles", null, "anonymous");

        // When
        mockMvc.perform(put("/menus/{id}", menu.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateMenuCommand)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("공지사항"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("메뉴를 수정합니다. 실패 시 응답 코드 400을 반환합니다.")
    void updateByIdShouldReturnBadRequest() throws Exception {
        // Given
        MenuCommand command = new MenuCommand("오시는 길", "http://www.kalgooksoo.com/categories/2/articles", null, "anonymous");
        MockHttpServletResponse response = mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("오시는 길"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse();

        EntityModel<Menu> entityModel = mapper.readValue(response.getContentAsString(), new TypeReference<>() {});
        Menu menu = entityModel.getContent();
        Assertions.assertThat(menu).isNotNull();

        MenuCommand updateMenuCommand = new MenuCommand(null, "http://www.kalgooksoo.com/categories/1/articles", null, "anonymous");

        // When
        mockMvc.perform(put("/menus/{id}", menu.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateMenuCommand)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("메뉴를 수정합니다. 존재하지 않는 메뉴 수정 시 응답 코드 404를 반환합니다.")
    void updateByIdShouldReturnNotFound() throws Exception {
        // Given
        MenuCommand command = new MenuCommand("오시는 길", "http://www.kalgooksoo.com/categories/2/articles", null, "anonymous");

        // When
        mockMvc.perform(put("/menus/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("메뉴를 삭제합니다. 성공 시 응답 코드 204를 반환합니다.")
    void deleteByIdShouldReturnNoContent() throws Exception {
        // Given
        MenuCommand command = new MenuCommand("오시는 길", "http://www.kalgooksoo.com/categories/2/articles", null, "anonymous");
        MockHttpServletResponse response = mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("오시는 길"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse();

        EntityModel<Menu> entityModel = mapper.readValue(response.getContentAsString(), new TypeReference<>() {});
        Menu menu = entityModel.getContent();
        Assertions.assertThat(menu).isNotNull();

        // When
        mockMvc.perform(delete("/menus/{id}", menu.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("메뉴를 삭제합니다. 존재하지 않는 메뉴 삭제 시 응답 코드 404를 반환합니다.")
    void deleteByIdShouldReturnNotFound() throws Exception {
        // Given

        // When
        mockMvc.perform(delete("/menus/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

}