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

import static org.junit.jupiter.api.Assertions.*;
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

    private MenuRestController menuRestController;

    @Autowired
    private TestEntityManager entityManager;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        MenuRepository menuRepository = new MenuJpaRepository(entityManager.getEntityManager());
        MenuService menuService = new DefaultMenuService(menuRepository);
        menuRestController = new MenuRestController(menuService);
        mockMvc = MockMvcBuilders.standaloneSetup(menuRestController).build();
    }

    @Test
    @DisplayName("메뉴 생성")
    void create() throws Exception {
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

}