package com.somartreview.reviewmate.dto.request.review;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateRequest {

    @NotNull
    private Integer rating;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private List<MultipartFile> reviewImages;

    @NotNull
    private Long travelProductId;

    @NotNull
    private Long customerId;
}
