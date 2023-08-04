package com.somartreview.reviewmate.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("/")
    public ResponseEntity<String> greet() {
        return ResponseEntity.ok("hello");
    }
}
