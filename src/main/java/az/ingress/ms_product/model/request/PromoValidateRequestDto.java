package az.ingress.ms_product.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoValidateRequestDto {

    @NotBlank
    private String code;

    @NotNull
    private BigDecimal originalPrice;
}