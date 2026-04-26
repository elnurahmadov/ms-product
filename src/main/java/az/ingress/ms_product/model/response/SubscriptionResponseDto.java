package az.ingress.ms_product.model.response;

import az.ingress.ms_product.model.enums.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionResponseDto {

    private UUID id;
    private UUID productId;
    private SubscriptionType type;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
}