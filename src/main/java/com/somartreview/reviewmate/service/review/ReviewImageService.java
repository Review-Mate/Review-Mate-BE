package com.somartreview.reviewmate.service.review;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.somartreview.reviewmate.domain.review.Review;
import com.somartreview.reviewmate.domain.review.ReviewImage;
import com.somartreview.reviewmate.domain.review.ReviewImageRepository;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.somartreview.reviewmate.exception.ErrorCode.AWS_S3_CLIENT_ERROR;
import static com.somartreview.reviewmate.exception.ErrorCode.REVIEW_IMAGE_FILE_IO_ERROR;

@Service
@RequiredArgsConstructor
public class ReviewImageService {

    private final ReviewImageRepository reviewImageRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String s3ImageBucketName;
    public static String CDN_DOMAIN = "image.reviewmate.co.kr";


    public void createAll(List<MultipartFile> reviewImageFiles, Review review) {
        for (MultipartFile reviewImageFile : reviewImageFiles) {
            ReviewImage reviewImage = ReviewImage.builder()
                    .fileName(uploadReviewImageFilesOnS3(reviewImageFile))
                    .review(review)
                    .build();

            reviewImageRepository.save(reviewImage);
        }
    }

    private String uploadReviewImageFilesOnS3(MultipartFile reviewImage) {
        try {
            String fileName = reviewImage.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(reviewImage.getContentType());
            metadata.setContentLength(reviewImage.getSize());

            amazonS3Client.putObject(s3ImageBucketName, fileName, reviewImage.getInputStream(), metadata);
            return fileName;

        } catch (SdkClientException e) {
            throw new ExternalServiceException(AWS_S3_CLIENT_ERROR);
        } catch (IOException e) {
            throw new DomainLogicException(REVIEW_IMAGE_FILE_IO_ERROR);
        }
    }

    public void removeReviewImageFiles(List<ReviewImage> reviewImages) {
        for (ReviewImage reviewImage : reviewImages) {
            removeReviewImageFilesOnS3(reviewImage.getFileName());
        }
    }

    private void removeReviewImageFilesOnS3(String fileName) {
        try {
            amazonS3Client.deleteObject(s3ImageBucketName, fileName);

        } catch (SdkClientException e) {
            throw new ExternalServiceException(AWS_S3_CLIENT_ERROR);
        }
    }
}
