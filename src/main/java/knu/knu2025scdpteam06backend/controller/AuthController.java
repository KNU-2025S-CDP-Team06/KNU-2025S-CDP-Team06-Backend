package knu.knu2025scdpteam06backend.controller;

import knu.knu2025scdpteam06backend.dto.auth.AuthRequestDto;
import knu.knu2025scdpteam06backend.dto.auth.AuthResponseDto;
import knu.knu2025scdpteam06backend.security.jwt.JwtTokenProvider;
import knu.knu2025scdpteam06backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {

        if (!request.getMbId().matches("\\d{10}") || !request.getPassword().matches("\\d{7}")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 매장 인증 (존재 여부 + 비밀번호 확인) 및 storeId 가져오기
        Long storeId = authService.validateAndGetStoreId(request.getMbId(), request.getPassword());
        if (storeId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // JWT 생성 (storeId 포함)
        String token = jwtTokenProvider.generateToken(request.getMbId(), storeId);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}