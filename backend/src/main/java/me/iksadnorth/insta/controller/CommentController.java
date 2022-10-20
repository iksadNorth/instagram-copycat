package me.iksadnorth.insta.controller;

import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.dto.CommentDto;
import me.iksadnorth.insta.model.request.ArticleCreateRequest;
import me.iksadnorth.insta.model.request.ArticleUpdateRequest;
import me.iksadnorth.insta.model.request.CommentCreateRequest;
import me.iksadnorth.insta.model.request.CommentUpdateRequest;
import me.iksadnorth.insta.model.response.ArticleReadResponse;
import me.iksadnorth.insta.model.response.CommentReadResponse;
import me.iksadnorth.insta.model.response.Response;
import me.iksadnorth.insta.service.ArticleService;
import me.iksadnorth.insta.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@RestController
public class CommentController {
    private final CommentService service;

    @PostMapping
    public Response<Void> commentCreate(@PathVariable Long id, @RequestBody CommentCreateRequest request) {
        service.commentCreate(request.toDto());
        return Response.success();
    }

    @GetMapping("/{Id}")
    public Response<Page<CommentReadResponse>> commentRead(@PathVariable Long id, @PageableDefault Pageable pageable) {
        Page<CommentDto> dtos = service.commentRead(id, pageable);
        return Response.success(dtos.map(CommentReadResponse::from));
    }

    @PutMapping("/{Id}")
    public Response<Void> commentUpdate(@PathVariable Long id, @RequestBody CommentUpdateRequest request, Authentication auth) {
        service.commentUpdate(id, request.toDto(), ((UserDetails) auth.getPrincipal()));
        return Response.success();
    }

    @DeleteMapping("/{Id}")
    public Response<Void> commentDelete(@PathVariable Long id, Authentication auth) {
        service.commentDelete(id, ((UserDetails) auth.getPrincipal()));
        return Response.success();
    }
}
