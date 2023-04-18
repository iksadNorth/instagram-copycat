package me.iksadnorth.insta.controller;

import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.dto.CommentDto;
import me.iksadnorth.insta.model.request.CommentCreateRequest;
import me.iksadnorth.insta.model.request.CommentUpdateRequest;
import me.iksadnorth.insta.model.response.CommentReadResponse;
import me.iksadnorth.insta.model.response.LikeReadResponse;
import me.iksadnorth.insta.model.response.Response;
import me.iksadnorth.insta.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@RestController
public class CommentController {
    private final CommentService service;

    @PostMapping("/{id}/children")
    public Response<Void> commentCreateToComment(
            @PathVariable Long id,
            @AuthenticationPrincipal AccountDto dto,
            @RequestBody CommentCreateRequest request
    ) {
        service.commentCreateToComment(request.getContent(), dto, id);
        return Response.success();
    }

    @GetMapping("/{id}/children")
    public Response<Page<CommentReadResponse>> commentRead(
            @PathVariable Long id,
            @PageableDefault Pageable pageable
    ) {
        Page<CommentDto> dtos = service.childrenRead(id, pageable);
        return Response.success(dtos.map(CommentReadResponse::from));
    }

    @PutMapping("/{id}")
    public Response<Void> commentUpdate(
            @PathVariable Long id,
            @RequestBody CommentUpdateRequest request,
            @AuthenticationPrincipal AccountDto dto
    ) {
        service.commentUpdate(id, request.toDto(), dto);
        return Response.success();
    }

    @DeleteMapping("/{id}")
    public Response<Void> commentDelete(
            @PathVariable Long id,
            @AuthenticationPrincipal AccountDto dto
    ) {
        service.commentDelete(id, dto);
        return Response.success();
    }

    @PostMapping("/{id}/like")
    public Response<Void> commentCreateLikes(
            @PathVariable Long id,
            @AuthenticationPrincipal AccountDto dto
    ) {
        service.commentCreateLikes(dto, id);
        return Response.success();
    }

    @GetMapping("/{id}/like")
    public Response<LikeReadResponse> commentIsLikes(
            @PathVariable Long id,
            @AuthenticationPrincipal AccountDto dto
    ) {
        Boolean isLikes = service.commentIsLikes(dto, id);
        return Response.success(LikeReadResponse.of(isLikes));
    }

    @DeleteMapping("/{id}/like")
    public Response<Void> commentDeleteLikes(
            @PathVariable Long id,
            @AuthenticationPrincipal AccountDto dto
    ) {
        service.commentDeleteLikes(dto, id);
        return Response.success();
    }
}
