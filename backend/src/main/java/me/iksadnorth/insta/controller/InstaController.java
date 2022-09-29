package me.iksadnorth.insta.controller;

import me.iksadnorth.insta.model.request.AccountLoginRequest;
import me.iksadnorth.insta.model.response.AccountLoginResponse;
import me.iksadnorth.insta.model.response.Response;
import me.iksadnorth.insta.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class InstaController {
    @Autowired AccountService service;

    @PostMapping("/login")
    public Response<AccountLoginResponse> login(@RequestBody AccountLoginRequest request) throws UsernameNotFoundException {
        String token = service.login(request.getEmail(), request.getPassword());
        AccountLoginResponse dto = AccountLoginResponse.from(token);
        return Response.success(dto);
    }
}
