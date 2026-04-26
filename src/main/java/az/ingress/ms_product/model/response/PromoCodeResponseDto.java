package az.ingress.ms_product.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromoCodeResponseDto {

    private UUID id;
    private String code;
    private BigDecimal discountPercent;
    private LocalDate expireDate;
    private Boolean isActive;
}