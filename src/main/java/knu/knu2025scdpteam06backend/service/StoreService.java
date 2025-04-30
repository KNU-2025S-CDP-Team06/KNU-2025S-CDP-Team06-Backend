package knu.knu2025scdpteam06backend.service;

import knu.knu2025scdpteam06backend.domain.store.Store;
import knu.knu2025scdpteam06backend.domain.store.StoreRepository;
import knu.knu2025scdpteam06backend.dto.StoreResponseDto;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository {
        this.storeRepository = storeRepository;
    }

    public StoreResponseDto getStoreById(Long id {
        Store store = storeRepository.findById(id
                .orElseThrow(( -> new IllegalArgumentException("해당 매장이 존재하지 않습니다. id=" + id;
        return new StoreResponseDto(store.getMbId(, store.getName(, store.getAddress(;
    }
}
