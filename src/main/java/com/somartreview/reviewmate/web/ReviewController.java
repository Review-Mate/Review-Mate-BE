package com.somartreview.reviewmate.web;

import com.somartreview.reviewmate.dto.request.review.ReviewCreateRequest;
import com.somartreview.reviewmate.dto.response.review.ReviewCreateResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    @PostMapping("/")
    public ResponseEntity<Void> createReview(@Valid @ModelAttribute ReviewCreateRequest reviewCreateRequest) {
        Long reviewId = 1L;

        return ResponseEntity.created(URI.create("/api/v1/review/" + reviewId)).build();
    }

    @PostMapping("/a")
    public ResponseEntity<Long> aa(@RequestParam(name = "ab") String ac) {

        return ResponseEntity.ok().build();
    }
}
