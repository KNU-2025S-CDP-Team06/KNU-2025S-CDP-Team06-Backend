package knu.knu2025scdpteam06backend.service;

import knu.knu2025scdpteam06backend.domain.store.Store;
import knu.knu2025scdpteam06backend.domain.store.StoreRepository;
import knu.knu2025scdpteam06backend.dto.auth.AuthRequestDto;
import knu.knu2025scdpteam06backend.dto.auth.AuthResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final StoreRepository storeRepository;

    public AuthResponseDto validateStoreExists(AuthRequestDto authRequestDto) {
        Store store = storeRepository.findByMbIdAndPassword(
                authRequestDto.getMbId(), authRequestDto.getPassword())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));

        System.out.println(authRequestDto.getMbId());

        return AuthResponseDto.builder()
                .token(store.getMbId()) // 실제로는 JWT 토큰 등을 생성해야 함
                .build();
    }

}
