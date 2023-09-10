package com.somartreview.reviewmate.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Impl the requests
@Tag(name = "대시보드")
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class DashboardController {
}
