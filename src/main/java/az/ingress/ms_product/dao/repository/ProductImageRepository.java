package az.ingress.ms_product.dao.repository;

import az.ingress.ms_product.dao.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {

    List<ProductImage> findAllByProductId(UUID productId);

    void deleteAllByProductId(UUID productId);
}