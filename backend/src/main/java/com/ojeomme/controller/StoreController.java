package com.ojeomme.controller;

import com.ojeomme.config.auth.LoginUser;
import com.ojeomme.dto.request.store.SearchPlaceListRequestDto;
import com.ojeomme.dto.response.store.*;
import com.ojeomme.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/searchPlaceList")
    public ResponseEntity<SearchPlaceListResponseDto> searchPlaceList(@Valid SearchPlaceListRequestDto requestDto) {
        SearchPlaceListResponseDto responseDto = storeService.searchPlaceList(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StorePreviewImagesResponseDto> getStoreReviews(@PathVariable Long storeId) {
        StorePreviewImagesResponseDto responseDto = storeService.getStore(storeId);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{storeId}/like")
    public ResponseEntity<Boolean> likeStore(@LoginUser Long userId, @PathVariable Long storeId) {
        boolean savedYn = storeService.likeStore(userId, storeId);
        return ResponseEntity.ok(savedYn);
    }

    @GetMapping("/{storeId}/like")
    public ResponseEntity<Boolean> getStoreLikeLogOfUser(@LoginUser Long userId, @PathVariable Long storeId) {
        boolean exist = storeService.getStoreLikeLogOfUser(userId, storeId);
        return ResponseEntity.ok(exist);
    }

    @GetMapping("/{storeId}/reviewImageList")
    public ResponseEntity<ReviewImageListResponseDto> getReviewImageList(@PathVariable Long storeId, @RequestParam(required = false) Long reviewImageId) {
        ReviewImageListResponseDto responseDto = storeService.getReviewImageList(storeId, reviewImageId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/todayRanking")
    public ResponseEntity<RealTimeStoreRankingResponseDto> getTodayStoreRanking(@RequestParam String regionCode) {
        RealTimeStoreRankingResponseDto responseDto = storeService.getTodayStoreRanking(regionCode);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/list")
    public ResponseEntity<StoreListResponseDto> getStoreList(@RequestParam String regionCode, @RequestParam(required = false) Long category, @RequestParam(required = false) Integer page) {
        StoreListResponseDto responseDto = storeService.getStoreList(regionCode, category, page);
        return ResponseEntity.ok(responseDto);
    }
}
