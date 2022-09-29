package me.iksadnorth.insta.controller;

import me.iksadnorth.insta.service.ArticleService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {
    @MockBean
    ArticleService service;

}