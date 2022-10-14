package me.iksadnorth.insta.controller;

import me.iksadnorth.insta.controller.HashtagController;
import me.iksadnorth.insta.service.HashtagService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(HashtagController.class)
class HashtagControllerTest {
    @MockBean
    HashtagService service;

}