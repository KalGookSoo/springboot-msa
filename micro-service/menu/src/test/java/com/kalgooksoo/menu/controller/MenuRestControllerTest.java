package com.kalgooksoo.menu.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kalgooksoo.core.exception.ExceptionHandlingController;
import com.kalgooksoo.core.principal.PrincipalProvider;
import com.kalgooksoo.core.principal.StubPrincipalProvider;
import com.kalgooksoo.menu.command.CreateMenuCommand;
import com.kalgooksoo.menu.command.MoveMenuCommand;
import com.kalgooksoo.menu.command.UpdateMenuCommand;
import com.kalgooksoo.menu.domain.Menu;
import com.kalgooksoo.menu.repository.MenuMemoryRepository;
import com.kalgooksoo.menu.repository.MenuRepository;
import com.kalgooksoo.menu.service.DefaultMenuService;
import com.kalgooksoo.menu.service.MenuService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 메뉴 REST 컨트롤러 테스트
 */
class MenuRestControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        MenuRepository menuRepository = new MenuMemoryRepository();
        PrincipalProvider principalProvider = new StubPrincipalProvider();
        MenuService menuService = new DefaultMenuService(menuRepository, principalProvider);
        MenuRestController menuRestController = new MenuRestController(menuService);
        ExceptionHandlingController exceptionHandlingController = new ExceptionHandlingController();
        mockMvc = MockMvcBuilders.standaloneSetup(menuRestController, exceptionHandlingController).build();
    }

    @Test
    @DisplayName("메뉴를 생성합니다. 성공 시 응답 코드 201을 반환합니다.")
    void createShouldReturnCreated() throws Exception {
        // Given
        CreateMenuCommand createMenuCommand = new CreateMenuCommand("오시는 길", "http://www.kalgooksoo.com/categories/2/articles", null);

        // When
        mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createMenuCommand)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("메뉴를 생성합니다. 실패 시 응답 코드 400을 반환합니다.")
    void createShouldReturnBadRequest() throws Exception {
        // Given
        CreateMenuCommand createMenuCommand = new CreateMenuCommand(null, "http://www.kalgooksoo.com/categories/2/articles", null);

        // When
        mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createMenuCommand)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("메뉴 목록을 조회합니다. 성공 시 응답 코드 200을 반환합니다.")
    void findAllShouldReturnOk() throws Exception {
        // Given
        CreateMenuCommand createMenuCommand = new CreateMenuCommand("오시는 길", "http://www.kalgooksoo.com/categories/2/articles", null);
        mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createMenuCommand)))
                .andExpect(status().isCreated());

        // When
        mockMvc.perform(get("/menus"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("메뉴를 조회합니다. 성공 시 응답 코드 200을 반환합니다.")
    void findByIdShouldReturnOk() throws Exception {
        // Given
        CreateMenuCommand createMenuCommand = new CreateMenuCommand("오시는 길", "http://www.kalgooksoo.com/categories/2/articles", null);
        MockHttpServletResponse httpServletResponse = mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createMenuCommand)))
                .andExpect(status().isCreated())
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
        CreateMenuCommand createMenuCommand = new CreateMenuCommand("오시는 길", "http://www.kalgooksoo.com/categories/2/articles", null);
        MockHttpServletResponse response = mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createMenuCommand)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        EntityModel<Menu> entityModel = mapper.readValue(response.getContentAsString(), new TypeReference<>() {});
        Menu menu = entityModel.getContent();
        Assertions.assertThat(menu).isNotNull();

        UpdateMenuCommand updateMenuCommand = new UpdateMenuCommand("공지사항", "http://www.kalgooksoo.com/categories/1/articles");

        // When
        mockMvc.perform(put("/menus/{id}", menu.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateMenuCommand)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("메뉴를 수정합니다. 실패 시 응답 코드 400을 반환합니다.")
    void updateByIdShouldReturnBadRequest() throws Exception {
        // Given
        CreateMenuCommand createMenuCommand = new CreateMenuCommand("오시는 길", "http://www.kalgooksoo.com/categories/2/articles", null);
        MockHttpServletResponse response = mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createMenuCommand)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        EntityModel<Menu> entityModel = mapper.readValue(response.getContentAsString(), new TypeReference<>() {});
        Menu menu = entityModel.getContent();
        Assertions.assertThat(menu).isNotNull();

        UpdateMenuCommand updateMenuCommand = new UpdateMenuCommand(null, "http://www.kalgooksoo.com/categories/1/articles");

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
        UpdateMenuCommand updateMenuCommand = new UpdateMenuCommand("오시는 길", "http://www.kalgooksoo.com/categories/2/articles");

        // When
        mockMvc.perform(put("/menus/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateMenuCommand)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("메뉴를 삭제합니다. 성공 시 응답 코드 204를 반환합니다.")
    void deleteByIdShouldReturnNoContent() throws Exception {
        // Given
        CreateMenuCommand createMenuCommand = new CreateMenuCommand("오시는 길", "http://www.kalgooksoo.com/categories/2/articles", null);
        MockHttpServletResponse response = mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createMenuCommand)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        EntityModel<Menu> entityModel = mapper.readValue(response.getContentAsString(), new TypeReference<>() {});
        Menu menu = entityModel.getContent();
        Assertions.assertThat(menu).isNotNull();

        // When
        mockMvc.perform(delete("/menus/{id}", menu.getId()))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("메뉴를 삭제합니다. 존재하지 않는 메뉴 삭제 시 응답 코드 404를 반환합니다.")
    void deleteByIdShouldReturnNotFound() throws Exception {
        // Given

        // When
        mockMvc.perform(delete("/menus/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("메뉴를 이동합니다. 성공 시 응답 코드 200을 반환합니다.")
    void moveByIdShouldReturnOk() throws Exception {
        // Given
        CreateMenuCommand createMenuCommand = new CreateMenuCommand("오시는 길", "http://www.kalgooksoo.com/categories/2/articles", null);
        MockHttpServletResponse response = mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createMenuCommand)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        EntityModel<Menu> entityModel = mapper.readValue(response.getContentAsString(), new TypeReference<>() {});
        Menu menu = entityModel.getContent();
        Assertions.assertThat(menu).isNotNull();

        MoveMenuCommand moveMenuCommand = new MoveMenuCommand(UUID.randomUUID().toString());

        // When
        mockMvc.perform(put("/menus/{id}/move", menu.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(moveMenuCommand)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("메뉴를 이동합니다. 존재하지 않는 메뉴 이동 시 응답 코드 404를 반환합니다.")
    void moveByIdShouldReturnNotFound() throws Exception {
        // Given
        MoveMenuCommand moveMenuCommand = new MoveMenuCommand(UUID.randomUUID().toString());

        // When
        mockMvc.perform(put("/menus/{id}/move", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(moveMenuCommand)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

}