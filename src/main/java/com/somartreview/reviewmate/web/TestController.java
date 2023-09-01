package com.somartreview.reviewmate.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Greeting")
@RestController
public class TestController {

    @Operation(summary = "서버 확인", description = "서버가 살아있다면, 'hello'를 반환")
    @GetMapping("/")
    public ResponseEntity<String> greet() {
        return ResponseEntity.ok("hello");
    }
}
