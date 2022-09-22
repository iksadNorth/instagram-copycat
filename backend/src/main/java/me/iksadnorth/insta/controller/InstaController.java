package me.iksadnorth.insta.controller;

import me.iksadnorth.insta.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class InstaController {
    @Autowired
    AccountService accountService;
}
