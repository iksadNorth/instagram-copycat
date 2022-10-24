package me.iksadnorth.insta.controller;

import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.request.AccountCreateRequest;
import me.iksadnorth.insta.model.request.AccountUpdateRequest;
import me.iksadnorth.insta.model.response.*;
import me.iksadnorth.insta.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
@RestController
public class AccountController {
    private final AccountService service;
    @PostMapping
    public Response<Void> accountCreate(@RequestBody AccountCreateRequest request) {
        service.accountCreate(request.toDto());
        return Response.success();
    }

    @GetMapping("/{id}")
    public Response<AccountReadResponse> accountRead(@PathVariable Long id) {
        AccountDto responses = service.loadById(id);
        return Response.success(AccountReadResponse.from(responses));
    }

    @GetMapping("/principal")
    public Response<AccountReadResponse> accountReadMine(@AuthenticationPrincipal AccountDto dto) {
        AccountDto responses = service.loadById(dto.getId());
        return Response.success(AccountReadResponse.from(responses));
    }

    @PutMapping("/{id}")
    public Response<Void> accountUpdate(@PathVariable Long id, @RequestBody AccountUpdateRequest request, Authentication auth) {
        service.accountUpdate(id, request.toDto(), ((UserDetails) auth.getPrincipal()));
        return Response.success();
    }

    @DeleteMapping("/{id}")
    public Response<Void> accountDelete(@PathVariable Long id, Authentication auth) {
        service.accountDelete(id, ((UserDetails) auth.getPrincipal()));
        return Response.success();
    }

    @GetMapping
    public Response<Page<AccountSearchResponse>> accountSearch(
            @RequestParam String nickName,
            @PageableDefault(page = 0, size = 5)Pageable pageable
            ) {
        Page<AccountDto> responses = service.loadByNickName(nickName, pageable);
        return Response.success(responses.map(AccountSearchResponse::from));
    }

    @GetMapping("/{id}/following/counts")
    public Response<CountsResponse> accountFollowingCounts(@PathVariable Long id) {
        Long counts = service.countFollowings(id);
        return Response.success(CountsResponse.of(counts));
    }

    @GetMapping("/{id}/follower/counts")
    public Response<CountsResponse> accountFollowerCounts(@PathVariable Long id) {
        Long counts = service.countFollowers(id);
        return Response.success(CountsResponse.of(counts));
    }

    @PostMapping("/follow/{follower_id}")
    public Response<Void> accountFollow(
            @PathVariable Long follower_id,
            Authentication auth
    ) {
        service.doFollow(((UserDetails) auth.getPrincipal()), follower_id);
        return Response.success();
    }

    @GetMapping("/follow/{follower_id}")
    public Response<FollowReadResponse> accountIsFollow(
            @PathVariable Long follower_id,
            Authentication auth
    ) {
        Boolean isFollow = service.isFollow(((UserDetails) auth.getPrincipal()), follower_id);
        return Response.success(FollowReadResponse.of(isFollow));
    }

    @DeleteMapping("/follow/{follower_id}")
    public Response<Void> accountUnFollow(
            @PathVariable Long follower_id,
            Authentication auth
    ) {
        service.undoFollow(((UserDetails) auth.getPrincipal()), follower_id);
        return Response.success();
    }

    @GetMapping("/{id}/articles/counts")
    public Response<CountsResponse> accountArticlesCounts(@PathVariable Long id) {
        Long counts = service.countArticles(id);
        return Response.success(CountsResponse.of(counts));
    }

    @GetMapping("/{id}/articles/follow")
    public Response<Page<ArticleReadResponse>> accountFeeds(
            @PathVariable Long id,
            @PageableDefault Pageable pageable
    ) {
        Page<ArticleDto> dtos = service.loadFeedById(id, pageable);
        return Response.success(dtos.map(ArticleReadResponse::from));
    }

    @GetMapping("/principal/articles/follow")
    public Response<Page<ArticleReadResponse>> accountFeedsWithAuth(
            @AuthenticationPrincipal AccountDto dto,
            @PageableDefault Pageable pageable
    ) {
        Page<ArticleDto> dtos = service.loadFeedById(dto.getId(), pageable);
        return Response.success(dtos.map(ArticleReadResponse::from));
    }

    @GetMapping("/principal/articles/recommended")
    public Response<Page<ArticleReadResponse>> accountRecommended(
            @AuthenticationPrincipal AccountDto dto,
            @PageableDefault Pageable pageable,
            Authentication auth
    ) {
        Page<ArticleDto> dtos = service.loadExploreById(dto.getId(), pageable, (UserDetails) auth.getPrincipal());
        return Response.success(dtos.map(ArticleReadResponse::from));
    }

    @GetMapping("/{id}/articles")
    public Response<Page<ArticleReadResponse>> accountArticlesMade(
            @PathVariable Long id,
            @PageableDefault Pageable pageable
    ) {
        Page<ArticleDto> dtos = service.loadArticlesById(id, pageable);
        return Response.success(dtos.map(ArticleReadResponse::from));
    }
}
