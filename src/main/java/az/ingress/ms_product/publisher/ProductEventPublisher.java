package az.ingress.ms_product.publisher;

import az.ingress.ms_product.dao.entity.Product;
import az.ingress.ms_product.model.dto.ProductEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventPublisher {

    public static final String EXCHANGE = "_EXCHANGE";
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.product-approved}")
    private String productApprovedQ;

    @Value("${rabbitmq.queue.product-rejected}")
    private String productRejectedQ;

    @Value("${rabbitmq.queue.product-created}")
    private String productCreatedQ;

    public void publishProductApproved(Product product) {
        ProductEventDto event = buildEvent(product);
        rabbitTemplate.convertAndSend(
                productApprovedQ + EXCHANGE,
                productApprovedQ + "_KEY",
                event
        );
        log.info("ActionLog.publishProductApproved.info: productId: {}", product.getId());
    }

    public void publishProductRejected(Product product) {
        ProductEventDto event = buildEvent(product);
        rabbitTemplate.convertAndSend(
                productRejectedQ + EXCHANGE,
                productRejectedQ + "_KEY",
                event
        );
        log.info("ActionLog.publishProductRejected.info: productId: {}", product.getId());
    }

    public void publishProductCreated(Product product) {
        ProductEventDto event = buildEvent(product);
        rabbitTemplate.convertAndSend(
                productCreatedQ + EXCHANGE,
                productCreatedQ + "_KEY",
                event
        );
        log.info("ActionLog.publishProductCreated.info: productId: {}", product.getId());
    }

    private ProductEventDto buildEvent(Product product) {
        return ProductEventDto.builder()
                .productId(product.getId())
                .productName(product.getName())
                .supplierId(product.getSupplierId())
                .status(product.getStatus())
                .build();
    }
}