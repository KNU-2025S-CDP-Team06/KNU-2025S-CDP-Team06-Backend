package knu.knu2025scdpteam06backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import knu.knu2025scdpteam06backend.dto.store.StoreResponseDto;
import knu.knu2025scdpteam06backend.dto.store.StoreUpdateRequestDto;
import knu.knu2025scdpteam06backend.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store"
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService {
        this.storeService = storeService;
    }

    @Operation(
            summary = "특정 ID의 매장 정보 조회",
            description = "ID를 이용하여 매장의 상세 정보를 조회합니다."
    
    @GetMapping("/{id}"
    public ResponseEntity<StoreResponseDto> getStore(
            @PathVariable Long id {
        StoreResponseDto store = storeService.getStoreById(id;
        return ResponseEntity.ok(store;
    }

    @PatchMapping("/{id}"
    public ResponseEntity<Void> updateStorePartially(
            @PathVariable Long id,
            @RequestBody StoreUpdateRequestDto dto {
        storeService.updateStore(id, dto;
        return ResponseEntity.noContent(.build(; // 204 No Content
    }

}
