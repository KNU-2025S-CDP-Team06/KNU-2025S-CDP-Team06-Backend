package knu.knu2025scdpteam06backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import knu.knu2025scdpteam06backend.dto.store.StoreCreateRequestDto;
import knu.knu2025scdpteam06backend.dto.store.StoreResponseDto;
import knu.knu2025scdpteam06backend.dto.store.StoreUpdateRequestDto;
import knu.knu2025scdpteam06backend.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @Operation(
            summary = "특정 ID의 매장 정보 조회",
            description = "ID를 이용하여 매장의 상세 정보를 조회합니다."
    )
    @GetMapping("/{id}")
    public ResponseEntity<StoreResponseDto> getStore(
            @PathVariable String id) {

        StoreResponseDto store = storeService.getStoreByMbId(id);
        return ResponseEntity.ok(store);
    }

    @Operation(
            summary = "매장 추가",
            description = "매장 정보를 추가합니다."
    )
    @PostMapping
    public ResponseEntity<Void> createStore(@RequestBody StoreCreateRequestDto dto) {
        Long id = storeService.createStore(dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "매장 정보 수정",
            description = "사업자번호, 매장명, 도로명주소, cluster를 변경할 수 있습니다. 바꾸길 원하는 것만 입력하여 바꿀 수 있습니다."
    )
    @PatchMapping("/{storeId}")
    public ResponseEntity<Void> updateStorePartially(

            @PathVariable Long storeId,
            @RequestBody StoreUpdateRequestDto dto) {
        storeService.updateStore(storeId, dto);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
