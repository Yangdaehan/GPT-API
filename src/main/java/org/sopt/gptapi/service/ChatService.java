package org.sopt.gptapi.service;

import com.google.common.util.concurrent.RateLimiter;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.gptapi.common.dto.ErrorMessage;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@RequiredArgsConstructor
@Service
public class ChatService {

    private static final Logger log = LoggerFactory.getLogger(ChatService.class);
    private final ChatgptService chatgptService;

    @Cacheable("chatResponses")
    public String getChatResponse(String content) {
        try {
            return chatgptService.sendMessage(content);
        } catch (HttpClientErrorException.BadRequest e) {

            log.error("Bad request error: {}", e.getMessage());
            return ErrorMessage.BAD_REQUEST.getMessage();
        } catch (Exception e) {

            log.error("General error: {}", e.getMessage());
            return ErrorMessage.GENERAL_ERROR.getMessage();
        }
    }

    public String processChatRequest(String content) {
        String translationPrompt = "다음에 나오는 문장들을 영어로 번역해줘 [ " + content + "]";
        String translatedText = getChatResponse(translationPrompt);
        System.out.println(translatedText);
        String complimentPrompt = "Give a compliment on the following on the following event in Korean [   " + translationPrompt + "]";
        System.out.println(complimentPrompt);

        return getChatResponse(complimentPrompt);
    }

}