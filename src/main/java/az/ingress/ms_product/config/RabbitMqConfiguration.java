package az.ingress.ms_product.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    public static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";
    public static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    public static final String EXCHANGE = "_EXCHANGE";
    private final String productApprovedQ;
    private final String productApprovedDLQ;
    private final String productRejectedQ;
    private final String productRejectedDLQ;
    private final String productCreatedQ;
    private final String productCreatedDLQ;

    private final String productApprovedQExchange;
    private final String productApprovedDLQExchange;
    private final String productRejectedQExchange;
    private final String productRejectedDLQExchange;
    private final String productCreatedQExchange;
    private final String productCreatedDLQExchange;

    private final String productApprovedQKey;
    private final String productApprovedDLQKey;
    private final String productRejectedQKey;
    private final String productRejectedDLQKey;
    private final String productCreatedQKey;
    private final String productCreatedDLQKey;

    public RabbitMqConfiguration(
            @Value("${rabbitmq.queue.product-approved}") String productApprovedQ,
            @Value("${rabbitmq.queue.product-approved-dlq}") String productApprovedDLQ,
            @Value("${rabbitmq.queue.product-rejected}") String productRejectedQ,
            @Value("${rabbitmq.queue.product-rejected-dlq}") String productRejectedDLQ,
            @Value("${rabbitmq.queue.product-created}") String productCreatedQ,
            @Value("${rabbitmq.queue.product-created-dlq}") String productCreatedDLQ) {

        this.productApprovedQ = productApprovedQ;
        this.productApprovedDLQ = productApprovedDLQ;
        this.productRejectedQ = productRejectedQ;
        this.productRejectedDLQ = productRejectedDLQ;
        this.productCreatedQ = productCreatedQ;
        this.productCreatedDLQ = productCreatedDLQ;

        this.productApprovedQExchange = productApprovedQ + EXCHANGE;
        this.productApprovedDLQExchange = productApprovedDLQ + EXCHANGE;
        this.productRejectedQExchange = productRejectedQ + EXCHANGE;
        this.productRejectedDLQExchange = productRejectedDLQ + EXCHANGE;
        this.productCreatedQExchange = productCreatedQ + EXCHANGE;
        this.productCreatedDLQExchange = productCreatedDLQ + EXCHANGE;

        this.productApprovedQKey = productApprovedQ + "_KEY";
        this.productApprovedDLQKey = productApprovedDLQ + "_KEY";
        this.productRejectedQKey = productRejectedQ + "_KEY";
        this.productRejectedDLQKey = productRejectedDLQ + "_KEY";
        this.productCreatedQKey = productCreatedQ + "_KEY";
        this.productCreatedDLQKey = productCreatedDLQ + "_KEY";
    }

    @Bean
    public DirectExchange productApprovedQExchange() {
        return new DirectExchange(productApprovedQExchange);
    }

    @Bean
    public DirectExchange productApprovedDLQExchange() {
        return new DirectExchange(productApprovedDLQExchange);
    }

    @Bean
    public Queue productApprovedQ() {
        return QueueBuilder.durable(productApprovedQ)
                .withArgument(X_DEAD_LETTER_EXCHANGE, productApprovedDLQExchange)
                .withArgument(X_DEAD_LETTER_ROUTING_KEY, productApprovedDLQKey)
                .build();
    }

    @Bean
    public Queue productApprovedDLQ() {
        return QueueBuilder.durable(productApprovedDLQ).build();
    }

    @Bean
    public Binding productApprovedQBinding() {
        return BindingBuilder.bind(productApprovedQ())
                .to(productApprovedQExchange()).with(productApprovedQKey);
    }

    @Bean
    public Binding productApprovedDLQBinding() {
        return BindingBuilder.bind(productApprovedDLQ())
                .to(productApprovedDLQExchange()).with(productApprovedDLQKey);
    }

    @Bean
    public DirectExchange productRejectedQExchange() {
        return new DirectExchange(productRejectedQExchange);
    }

    @Bean
    public DirectExchange productRejectedDLQExchange() {
        return new DirectExchange(productRejectedDLQExchange);
    }

    @Bean
    public Queue productRejectedQ() {
        return QueueBuilder.durable(productRejectedQ)
                .withArgument(X_DEAD_LETTER_EXCHANGE, productRejectedDLQExchange)
                .withArgument(X_DEAD_LETTER_ROUTING_KEY, productRejectedDLQKey)
                .build();
    }

    @Bean
    public Queue productRejectedDLQ() {
        return QueueBuilder.durable(productRejectedDLQ).build();
    }

    @Bean
    public Binding productRejectedQBinding() {
        return BindingBuilder.bind(productRejectedQ())
                .to(productRejectedQExchange()).with(productRejectedQKey);
    }

    @Bean
    public Binding productRejectedDLQBinding() {
        return BindingBuilder.bind(productRejectedDLQ())
                .to(productRejectedDLQExchange()).with(productRejectedDLQKey);
    }

    @Bean
    public DirectExchange productCreatedQExchange() {
        return new DirectExchange(productCreatedQExchange);
    }

    @Bean
    public DirectExchange productCreatedDLQExchange() {
        return new DirectExchange(productCreatedDLQExchange);
    }

    @Bean
    public Queue productCreatedQ() {
        return QueueBuilder.durable(productCreatedQ)
                .withArgument(X_DEAD_LETTER_EXCHANGE, productCreatedDLQExchange)
                .withArgument(X_DEAD_LETTER_ROUTING_KEY, productCreatedDLQKey)
                .build();
    }

    @Bean
    public Queue productCreatedDLQ() {
        return QueueBuilder.durable(productCreatedDLQ).build();
    }

    @Bean
    public Binding productCreatedQBinding() {
        return BindingBuilder.bind(productCreatedQ())
                .to(productCreatedQExchange()).with(productCreatedQKey);
    }

    @Bean
    public Binding productCreatedDLQBinding() {
        return BindingBuilder.bind(productCreatedDLQ())
                .to(productCreatedDLQExchange()).with(productCreatedDLQKey);
    }

    @Bean
    public JacksonJsonMessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         JacksonJsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}