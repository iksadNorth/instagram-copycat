package me.iksadnorth.insta.controller;

import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.dto.HashtagDto;
import me.iksadnorth.insta.model.request.HashtagCreateRequest;
import me.iksadnorth.insta.model.response.ArticleReadResponse;
import me.iksadnorth.insta.model.response.HashtagReadResponse;
import me.iksadnorth.insta.model.response.Response;
import me.iksadnorth.insta.service.HashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/hashtag")
@RestController
public class HashtagController {
    @Autowired HashtagService service;

    @PostMapping
    public Response<Void> hashtagCreate(HashtagCreateRequest request) {
        return Response.success();
    }

    @GetMapping
    public Response<Page<HashtagReadResponse>> hashtagRead(Pageable pageable) {
        Page<HashtagDto> dtos = service.hashtagRead(pageable);
        return Response.success(dtos.map(HashtagReadResponse::from));
    }

    @GetMapping("/{tag}")
    public Response<Page<ArticleReadResponse>> hashtagReadAbout(@PathVariable String tag, Pageable pageable) {
        Page<ArticleDto> dtos = service.hashtagReadAbout(tag, pageable);
        return Response.success(dtos.map(ArticleReadResponse::from));
    }

    @DeleteMapping("/{tag}")
    public Response<Void> hashtagDelete(@PathVariable String tag) {
        service.hashtagDelete(tag);
        return Response.success();
    }
}
