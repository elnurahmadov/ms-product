package az.ingress.ms_product.dao.repository;

import az.ingress.ms_product.dao.entity.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, UUID> {

    Optional<PromoCode> findByCodeAndIsActiveTrue(String code);
}