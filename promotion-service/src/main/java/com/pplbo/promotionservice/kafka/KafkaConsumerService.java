package com.pplbo.promotionservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.pplbo.promotionservice.command.ValidateFreeShippingPromotion;
import com.pplbo.promotionservice.service.PromotionService;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private PromotionService promotionService;

    @KafkaListener(topics = "promotion-response")
    public void listen(ValidateFreeShippingPromotion command) {
        boolean validationResult = promotionService.validateFreeShippingPromotion(command.getPromotionId());

        if(validationResult){
            logger.info("Receive message: {}", command);
            // LOGIKA
        }
    }

}
