package knu.knu2025scdpteam06backend.service;

import knu.knu2025scdpteam06backend.domain.store.Role;
import knu.knu2025scdpteam06backend.domain.store.Store;
import knu.knu2025scdpteam06backend.domain.store.StoreRepository;
import knu.knu2025scdpteam06backend.dto.auth.AuthRequestDto;
import knu.knu2025scdpteam06backend.dto.auth.AuthResponseDto;
import knu.knu2025scdpteam06backend.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final StoreRepository storeRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDto login(AuthRequestDto dto) {
        Long storeId = validateAndGetStoreId(dto.getMbId(), dto.getPassword());
        if (storeId == null) {
            throw new RuntimeException("아이디 또는 비밀번호가 일치하지 않습니다");
        }

        String token = jwtTokenProvider.generateToken(dto.getMbId(), storeId);
        return new AuthResponseDto(token);
    }

    public Long validateAndGetStoreId(String mbId, String password) {
        return storeRepository.findByMbId(mbId)
                .filter(store -> passwordEncoder.matches(password, store.getPassword()))
                .map(Store::getId)
                .orElse(null);
    }

    public Long validateAndGetAdminId(String mbId, String password) {

        return storeRepository.findByMbId(mbId)
                .filter(s -> s.getPassword().equals(password))
                .filter(s -> Role.ADMIN.equals(s.getRole()))  // 안전한 enum 비교
                .map(Store::getId)
                .orElse(null);
    }
}
