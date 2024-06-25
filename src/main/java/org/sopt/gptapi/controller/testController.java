package org.sopt.gptapi.controller;


import com.google.common.util.concurrent.RateLimiter;
import io.github.flashvayne.chatgpt.dto.ChatRequest;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.gptapi.common.dto.ErrorMessage;
import org.sopt.gptapi.dto.UserRequest;
import org.sopt.gptapi.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/")
public class testController {

    private final ChatService chatService;
    private final ChatgptService chatgptService;

    // 초당 1개의 요청만 허용
    private final RateLimiter rateLimiter = RateLimiter.create(1.0);

    @PostMapping("chat-gpt")
    public String handleChatRequest(
        @RequestBody UserRequest userRequest
    )
    {
        String content = userRequest.getContent();
        // 요청 빈도 조절
        if (rateLimiter.tryAcquire()) {
            return chatService.processChatRequest(content);
        } else {
            return ErrorMessage.TOO_MANY_REQUEST.getMessage();
        }
    }
}