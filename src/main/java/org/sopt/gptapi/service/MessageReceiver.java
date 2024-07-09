package org.sopt.gptapi.service;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.gptapi.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageReceiver {

    private static final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);
    private final AsyncChatgptService asyncChatgptService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        logger.info("Received message: {}", message);
        asyncChatgptService.sendMessage(message)
                .doOnError(e -> logger.error("Error processing message: {}", e.getMessage()))
                .subscribe();
    }
}
