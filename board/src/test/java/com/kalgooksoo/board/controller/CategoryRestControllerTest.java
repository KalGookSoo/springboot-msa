package com.kalgooksoo.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kalgooksoo.board.repository.CategoryJpaRepository;
import com.kalgooksoo.board.repository.CategoryRepository;
import com.kalgooksoo.board.service.CategoryService;
import com.kalgooksoo.board.service.DefaultCategoryService;
import com.kalgooksoo.exception.ExceptionHandlingController;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

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



}