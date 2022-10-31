package me.iksadnorth.insta.controller;

import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.dto.CommentDto;
import me.iksadnorth.insta.model.request.ArticleCreateRequest;
import me.iksadnorth.insta.model.request.ArticleUpdateRequest;
import me.iksadnorth.insta.model.request.CommentCreateRequest;
import me.iksadnorth.insta.model.response.*;
import me.iksadnorth.insta.service.ArticleService;
import me.iksadnorth.insta.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
@RestController
public class ArticleController {
    private final ArticleService service;
    private final CommentService commentService;

    @PostMapping
    public Response<Void> articleCreate(@RequestBody ArticleCreateRequest request) {
        service.articleCreate(request.toDto());
        return Response.success();
    }

    @GetMapping("/{id}")
    public Response<ArticleReadResponse> articleRead(@PathVariable Long id) {
        ArticleDto dto = service.articleReadWithInfo(id);
        return Response.success(ArticleReadResponse.from(dto));
    }

    @PutMapping("/{id}")
    public Response<Void> articleUpdate(
            @PathVariable Long id,
            @RequestBody ArticleUpdateRequest request,
            Authentication auth
    ) {
        service.articleUpdate(id, request.toDto(), ((UserDetails) auth.getPrincipal()));
        return Response.success();
    }

    @DeleteMapping("/{id}")
    public Response<Void> articleDelete(@PathVariable Long id, Authentication auth) {
        service.articleDelete(id, ((UserDetails) auth.getPrincipal()));
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

    @GetMapping("/{id}/like")
    public Response<LikeReadResponse> articleIsLike(@PathVariable Long id, Authentication auth) {
        Boolean isLike = service.articleIsLike(id, ((UserDetails) auth.getPrincipal()));
        return Response.success(LikeReadResponse.of(isLike));
    }

    @DeleteMapping("/{id}/like")
    public Response<Void> articleLikeDelete(@PathVariable Long id, Authentication auth) {
        service.articleLikeDelete(id, ((UserDetails) auth.getPrincipal()));
        return Response.success();
    }

    @PostMapping("/{id}/comments")
    public Response<Void> commentCreateToArticle(
            @PathVariable Long id,
            @AuthenticationPrincipal AccountDto dto,
            @RequestBody CommentCreateRequest request) {
        commentService.commentCreateToArticle(request.getContent(), dto, id);
        return Response.success();
    }

    @GetMapping("/{id}/comments")
    public Response<Page<CommentReadResponse>> commentRead(@PathVariable Long id, @PageableDefault Pageable pageable) {
        Page<CommentDto> dtos = commentService.commentRead(id, pageable);
        return Response.success(dtos.map(CommentReadResponse::from));
    }
}
