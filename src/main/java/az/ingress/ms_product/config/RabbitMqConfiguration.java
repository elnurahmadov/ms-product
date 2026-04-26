package az.ingress.ms_product.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {
    private final String productQ;
    private final String productDLQ;
    private final String productQExchange;
    private final String productDLQExchange;
    private final String productQKey;
    private final String productDLQKey;

    public RabbitMqConfiguration(@Value("${rabbitmq.queue.product}") String productQ,
                                 @Value("${rabbitmq.queue.product-dlq}") String productDLQ) {

        this.productQ = productQ;
        this.productDLQ = productDLQ;
        this.productQExchange = productQ + "_EXCHANGE";
        this.productDLQExchange = productDLQ + "_EXCHANGE";
        this.productQKey = productQ + "_KEY";
        this.productDLQKey = productDLQ + "_KEY";
    }

    @Bean
    public DirectExchange productDLQExchange() {
        return new DirectExchange(productDLQExchange);
    }

    @Bean
    public DirectExchange productQExchange() {
        return new DirectExchange(productQExchange);
    }

    @Bean
    public Queue productDLQ() {
        return QueueBuilder.durable(productDLQ).build();
    }

    @Bean
    public Queue productQ() {
        return QueueBuilder.durable(productQ)
                .withArgument("x-dead-letter-exchange", productDLQExchange)
                .withArgument("x-dead-letter-routing-key", productDLQKey)
                .build();
    }

    @Bean
    public Binding productDLQBinding() {
        return BindingBuilder.bind(productDLQ())
                .to(productDLQExchange()).with(productDLQKey);
    }

    @Bean
    public Binding productQBinding() {
        return BindingBuilder.bind(productQ())
                .to(productQExchange()).with(productQKey);
    }
}