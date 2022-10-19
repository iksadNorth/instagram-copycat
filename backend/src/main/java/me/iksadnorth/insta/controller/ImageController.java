package me.iksadnorth.insta.controller;

import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.response.ImageCreateResponse;
import me.iksadnorth.insta.model.response.Response;
import me.iksadnorth.insta.service.ImageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
@RestController
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public Response<ImageCreateResponse> imageCreate(List<MultipartFile> files) {
        ImageCreateResponse response = ImageCreateResponse.from(imageService.create(files));
        return Response.success(response);
    }
}
