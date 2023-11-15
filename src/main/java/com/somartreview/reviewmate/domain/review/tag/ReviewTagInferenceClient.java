package com.somartreview.reviewmate.domain.review.tag;

import com.somartreview.reviewmate.config.FeignClientConfig;
import com.somartreview.reviewmate.dto.review.tag.ReviewTagInferenceRequest;
import com.somartreview.reviewmate.dto.review.tag.ReviewTagInferenceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "review-tag-inference-service",
        url = "https://e75umm0l87.execute-api.ap-northeast-2.amazonaws.com/stage",
        configuration = FeignClientConfig.class
)
public interface ReviewTagInferenceClient {

    @GetMapping
    ReviewTagInferenceResponse inferenceReview(@RequestBody ReviewTagInferenceRequest reviewTagInferenceRequest);
}
