package knu.knu2025scdpteam06backend.service;

import knu.knu2025scdpteam06backend.domain.store.Role;
import knu.knu2025scdpteam06backend.domain.store.Store;
import knu.knu2025scdpteam06backend.domain.store.StoreRepository;
import knu.knu2025scdpteam06backend.dto.auth.AuthRequestDto;
import knu.knu2025scdpteam06backend.dto.auth.AuthResponseDto;
import knu.knu2025scdpteam06backend.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final StoreRepository storeRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponseDto login(AuthRequestDto dto) {
        Store store = storeRepository.findByMbId(dto.getMbId())
                .orElseThrow(() -> new RuntimeException("해당 매장이 존재하지 않습니다"));

        String token = jwtTokenProvider.generateToken(store.getMbId(), store.getId());

        return new AuthResponseDto(token);
    }

    public Long validateAndGetStoreId(String mbId, String password) {
        Store store = storeRepository.findByMbId(mbId)
                .filter(s -> s.getPassword().equals(password))
                .orElse(null);
        return store != null ? store.getId() : null;
    }

    public Long validateAndGetAdminId(String mbId, String password) {

        return storeRepository.findByMbId(mbId)
                .filter(s -> s.getPassword().equals(password))
                .filter(s -> Role.ADMIN.equals(s.getRole()))  // 안전한 enum 비교
                .map(Store::getId)
                .orElse(null);
    }
}
