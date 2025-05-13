package knu.knu2025scdpteam06backend.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByMbId(String mbId);

    Optional<Store> findByMbIdAndPassword(String mbId, String password);

}
