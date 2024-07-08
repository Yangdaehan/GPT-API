package org.sopt.gptapi.service;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import org.sopt.gptapi.common.dto.ErrorMessage;
import org.sopt.gptapi.common.dto.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatgptService chatgptService;
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    public String getChatResponse(String content) {
        try {
            String message = Prompt.MESSAGE.getMessage() + content + "\n칭찬 :";
            return chatgptService.sendMessage(message);
        } catch (HttpClientErrorException.Unauthorized e) {
            logger.error("Unauthorized error", e);
            return ErrorMessage.UNAUTHORIZED.getMessage();  // UNAUTHORIZED 메시지를 반환합니다.
        } catch (HttpClientErrorException.BadRequest e) {
            logger.error("Bad request error", e);
            return ErrorMessage.BAD_REQUEST.getMessage();
        } catch (Exception e) {
            logger.error("General error", e);
            return ErrorMessage.GENERAL_ERROR.getMessage();
        }
    }
}
