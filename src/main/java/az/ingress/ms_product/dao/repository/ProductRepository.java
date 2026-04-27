package az.ingress.ms_product.dao.repository;

import az.ingress.ms_product.dao.entity.Product;
import az.ingress.ms_product.model.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    Page<Product> findAllByStatus(ProductStatus status, Pageable pageable);

    @Query(value = "SELECT p FROM Product p WHERE p.supplierId = :supplierId",
            countQuery = "SELECT COUNT(p) FROM Product p WHERE p.supplierId = :supplierId")
    @EntityGraph(attributePaths = "images")
    Page<Product> findAllBySupplierId(@Param("supplierId") UUID supplierId, Pageable pageable);

    @EntityGraph(attributePaths = "images")
    Optional<Product> findByIdAndSupplierId(UUID id, UUID supplierId);
}