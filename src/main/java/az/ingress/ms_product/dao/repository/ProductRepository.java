package az.ingress.ms_product.dao.repository;

import az.ingress.ms_product.dao.entity.Product;
import az.ingress.ms_product.model.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findAllByStatus(ProductStatus status, Pageable pageable);

    Page<Product> findAllBySupplierId(UUID supplierId, Pageable pageable);
}