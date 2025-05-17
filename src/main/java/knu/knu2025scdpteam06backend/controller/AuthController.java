package knu.knu2025scdpteam06backend.controller;

import knu.knu2025scdpteam06backend.dto.auth.AuthRequestDto;
import knu.knu2025scdpteam06backend.dto.auth.AuthResponseDto;
import knu.knu2025scdpteam06backend.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authLogin(@RequestBody AuthRequestDto request) {

        if (!request.getMbId().matches("\\d{10}") || !request.getPassword().matches("\\d{7}")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = jwtTokenProvider.generateToken(request.getMbId());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}
