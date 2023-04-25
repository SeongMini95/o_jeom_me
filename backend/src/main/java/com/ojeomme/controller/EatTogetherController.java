package com.ojeomme.controller;

import com.ojeomme.config.auth.LoginUser;
import com.ojeomme.dto.request.eattogether.WriteEatTogetherPostRequestDto;
import com.ojeomme.service.EatTogetherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/eatTogether")
public class EatTogetherController {

    private final EatTogetherService eatTogetherService;

    @PostMapping
    public ResponseEntity<Long> writeEatTogetherPost(@LoginUser Long userId, @Valid @RequestBody WriteEatTogetherPostRequestDto requestDto) throws IOException {
        Long postId = eatTogetherService.writeEatTogetherPost(userId, requestDto);
        return ResponseEntity.ok(postId);
    }
}
