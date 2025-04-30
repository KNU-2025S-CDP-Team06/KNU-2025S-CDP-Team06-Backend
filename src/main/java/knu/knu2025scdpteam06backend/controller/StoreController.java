package knu.knu2025scdpteam06backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import knu.knu2025scdpteam06backend.dto.StoreResponseDto;
import knu.knu2025scdpteam06backend.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @Operation(
            summary = "특정 ID의 매장 정보 조회",
            description = "ID를 이용하여 매장의 상세 정보를 조회합니다."
    )
    @GetMapping("/{id}")
    public ResponseEntity<StoreResponseDto> getStore(
            @PathVariable Long id) {
        StoreResponseDto store = storeService.getStoreById(id);
        return ResponseEntity.ok(store);
    }
}
