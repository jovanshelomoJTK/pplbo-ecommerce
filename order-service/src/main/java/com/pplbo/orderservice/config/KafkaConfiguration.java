package com.pplbo.orderservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.pplbo.orderservice.command.ProductAvailableCommand;
import com.pplbo.orderservice.event.OrderEvent;
import com.pplbo.orderservice.command.ProductOutOfStockCommand;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfiguration {

    @Bean
    public ProducerFactory<String, OrderEvent> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, OrderEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    private Map<String, Object> consumerConfig() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "order-service");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class.getName());
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.pplbo.orderservice.command");

        return config;
    }
    
    @Bean
    public ConsumerFactory<String, ProductAvailableCommand> productAvailableConsumerFactory() {

        Map<String, Object> config = consumerConfig();

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProductAvailableCommand> productAvailableKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ProductAvailableCommand> factory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(productAvailableConsumerFactory());
        return factory;
    }

    @Bean
    public ProducerFactory<String, ProductAvailableCommand> productAvailableCommandProducerFactory() {
        Map<String, Object> config = consumerConfig();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, ProductAvailableCommand> productAvailableCommandKafkaTemplate() {
        return new KafkaTemplate<>(productAvailableCommandProducerFactory());
    }

    @Bean
    public ConsumerFactory<String, ProductOutOfStockCommand> productOutOfStockConsumerFactory() {
        Map<String, Object> config = consumerConfig();

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProductOutOfStockCommand> productOutOfStockKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ProductOutOfStockCommand> factory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(productOutOfStockConsumerFactory());
        return factory;
    }

    @Bean
    public ProducerFactory<String, ProductOutOfStockCommand> productOutOfStockCommandProducerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, ProductOutOfStockCommand> commandKafkaTemplate() {
        return new KafkaTemplate<>(productOutOfStockCommandProducerFactory());
    }


}
