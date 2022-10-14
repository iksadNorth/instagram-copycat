package me.iksadnorth.insta.controller;

import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.request.ArticleCreateRequest;
import me.iksadnorth.insta.model.request.ArticleUpdateRequest;
import me.iksadnorth.insta.model.response.ArticleReadResponse;
import me.iksadnorth.insta.model.response.CountsResponse;
import me.iksadnorth.insta.model.response.Response;
import me.iksadnorth.insta.service.ArticleService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
@RestController
public class ArticleController {
    private final ArticleService service;

    @PostMapping
    public Response<Void> articleCreate(ArticleCreateRequest request) {
        service.articleCreate(request.toDto());
        return Response.success();
    }

    @GetMapping("/{id}")
    public Response<ArticleReadResponse> articleRead(@PathVariable Long id) {
        ArticleDto dto = service.articleRead(id);
        return Response.success(ArticleReadResponse.from(dto));
    }

    @PutMapping("/{id}")
    public Response<Void> articleUpdate(@PathVariable Long id, ArticleUpdateRequest request, Authentication auth) {
        service.articleUpdate(id, request.toDto(), ((UserDetails) auth.getPrincipal()));
        return Response.success();
    }

    @DeleteMapping("/{id}")
    public Response<Void> articleDelete(@PathVariable Long id, Authentication auth) {
        service.articleDelete(id, ((UserDetails) auth.getPrincipal()));
        return Response.success();
    }

    @GetMapping("/{id}/view/counts")
    public Response<CountsResponse> articleViewCount(@PathVariable Long id) {
        Long views = service.articleViewCount(id);
        return Response.success(CountsResponse.of(views));
    }

    @PostMapping("/{id}/view")
    public Response<Void> articleViewAdd(@PathVariable Long id, Authentication auth) {
        service.articleViewAdd(id, ((UserDetails) auth.getPrincipal()));
        return Response.success();
    }

    @DeleteMapping("/{id}/view")
    public Response<Void> articleViewDelete(@PathVariable Long id, Authentication auth) {
        service.articleViewDelete(id, ((UserDetails) auth.getPrincipal()));
        return Response.success();
    }

    @GetMapping("/{id}/like/counts")
    public Response<CountsResponse> articleLikeCount(@PathVariable Long id) {
        Long views = service.articleLikeCount(id);
        return Response.success(CountsResponse.of(views));
    }

    @PostMapping("/{id}/like")
    public Response<Void> articleLikeAdd(@PathVariable Long id, Authentication auth) {
        service.articleLikeAdd(id, ((UserDetails) auth.getPrincipal()));
        return Response.success();
    }

    @DeleteMapping("/{id}/like")
    public Response<Void> articleLikeDelete(@PathVariable Long id, Authentication auth) {
        service.articleLikeDelete(id, ((UserDetails) auth.getPrincipal()));
        return Response.success();
    }
}
