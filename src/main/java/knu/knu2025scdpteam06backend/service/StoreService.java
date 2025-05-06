package knu.knu2025scdpteam06backend.service;

import jakarta.transaction.Transactional;
import knu.knu2025scdpteam06backend.domain.store.Store;
import knu.knu2025scdpteam06backend.domain.store.StoreRepository;
import knu.knu2025scdpteam06backend.dto.store.StoreCreateRequestDto;
import knu.knu2025scdpteam06backend.dto.store.StoreResponseDto;
import knu.knu2025scdpteam06backend.dto.store.StoreUpdateRequestDto;
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
        return new StoreResponseDto(store.getId(, store.getMbId(, store.getName(, store.getAddress(;
    }

    public StoreResponseDto getStoreByMbId(String id {
        Store store = storeRepository.findByMbId(id
                .orElseThrow(( -> new IllegalArgumentException("해당 매장이 존재하지 않습니다. id=" + id;
        return new StoreResponseDto(store.getId(, store.getMbId(, store.getName(, store.getAddress(;
    }

    @Transactional
    public Long createStore(StoreCreateRequestDto dto {
        Store store = Store.builder(
                .mbId(dto.getMbId(
                .name(dto.getName(
                .address(dto.getAddress(
                .latitude(dto.getLatitude(
                .longitude(dto.getLongitude(
                .build(;
        storeRepository.save(store;

        return store.getId(;
    }

    @Transactional
    public void updateStore(String id, StoreUpdateRequestDto dto {
        Store store = storeRepository.findByMbId(id
                .orElseThrow(( -> new IllegalArgumentException("해당 매장이 존재하지 않습니다. id=" + id;

        if (dto.getMbId( != null store.setMbId(dto.getMbId(;
        if (dto.getName( != null store.setName(dto.getName(;
        if (dto.getAddress( != null store.setAddress(dto.getAddress(;
        if (dto.getLatitude( != null store.setLatitude(dto.getLatitude(;
        if (dto.getLongitude( != null store.setLongitude(dto.getLongitude(;
        if (dto.getCluster( != null store.setCluster(dto.getCluster(;
    }
}
