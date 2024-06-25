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

    private final ChatgptService chatgptService;

    // 초당 1개의 요청만 허용
    private final RateLimiter rateLimiter = RateLimiter.create(1.0);


    public String getChatResponse(String content) {
        try {
            return chatgptService.sendMessage(content);
        } catch (HttpClientErrorException.BadRequest e) {
            return ErrorMessage.BAD_REQUEST.getMessage();
        } catch (Exception e) {
            return ErrorMessage.GENERAL_ERROR.getMessage();
        }
    }

    public String processChatRequest(String content) {
        if (rateLimiter.tryAcquire()) {
            String translationPrompt = "다음에 나오는 문장들을 영어로 번역해줘 [ " + content + "]";
            String translatedText = getChatResponse(translationPrompt);
            String complimentPrompt =
                "Give a long compliment on the following on the following event in Korean [   "
                    + translatedText + "]";

            return getChatResponse(complimentPrompt);
        }else {
            return ErrorMessage.TOO_MANY_REQUEST.getMessage();
        }

    }

}