package com.kalgooksoo.menu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kalgooksoo.menu.command.MenuCommand;
import com.kalgooksoo.menu.repository.MenuJpaRepository;
import com.kalgooksoo.menu.repository.MenuRepository;
import com.kalgooksoo.menu.service.DefaultMenuService;
import com.kalgooksoo.menu.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        mockMvc = MockMvcBuilders.standaloneSetup(menuRestController).build();
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
    @DisplayName("메뉴를 조회합니다. 존재하지 않는 메뉴 조회 시 응답 코드 404를 반환합니다.")
    void findByIdShouldReturnNotFound() throws Exception {
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
    @DisplayName("메뉴를 수정합니다. 성공 시 응답 코드 200을 반환합니다.")
    void updateByIdShouldReturnOk() throws Exception {
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
    @DisplayName("메뉴를 수정합니다. 실패 시 응답 코드 400을 반환합니다.")
    void updateByIdShouldReturnBadRequest() throws Exception {
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
    @DisplayName("메뉴를 수정합니다. 존재하지 않는 메뉴 수정 시 응답 코드 404를 반환합니다.")
    void updateByIdShouldReturnNotFound() throws Exception {
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
    @DisplayName("메뉴를 삭제합니다. 성공 시 응답 코드 204를 반환합니다.")
    void deleteByIdShouldReturnNoContent() throws Exception {
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

}