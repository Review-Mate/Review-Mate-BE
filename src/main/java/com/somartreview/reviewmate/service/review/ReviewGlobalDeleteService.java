package com.somartreview.reviewmate.service.review;

import com.somartreview.reviewmate.domain.review.ReviewImageRepository;
import com.somartreview.reviewmate.domain.review.ReviewRepository;
import com.somartreview.reviewmate.domain.review.ReviewTagRepository;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewGlobalDeleteService {

    private final ReviewRepository reviewRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final ReviewImageRepository reviewImageRepository;

    @Transactional
    public void deleteById(Long id) {
        validateExistId(id);

        deleteAllByIds(List.of(id));
    }

    private void validateExistId(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new DomainLogicException(ErrorCode.REVIEW_NOT_FOUND);
        }
    }

    @Transactional
    public void deleteAllByIds(List<Long> ids) {
        deleteAllReviewTagsByIds(ids);
        deleteAllReviewImagesByIds(ids);
        reviewRepository.deleteAllByIdsInQuery(ids);
    }

    @Transactional
    public void deleteAllReviewTagsByIds(List<Long> ids) {
        reviewTagRepository.deleteAllByReviewIdInQuery(ids);
    }

    @Transactional
    public void deleteAllReviewImagesByIds(List<Long> ids) {
        reviewImageRepository.deleteAllByReviewIdInQuery(ids);
    }
}
