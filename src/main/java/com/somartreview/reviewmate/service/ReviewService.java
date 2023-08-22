package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.Review.ReviewRepository;
import com.somartreview.reviewmate.domain.TravelProduct.SingleTravelProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomerService customerService;
    private final TravelProductService travelProductService;

}
