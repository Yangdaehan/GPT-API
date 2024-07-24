package org.sopt.gptapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.gptapi.dto.UserRequest;
import org.sopt.gptapi.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@Tag(name = "GPT 연동", description = "GPT 연동 관련 API입니다. (TestController)")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/")
public class testController {

    private final ChatService chatService;

    @PostMapping("chat-gpt")
    @Operation(summary = "GPT API를 활용해서 통신하는 API")
    public Mono<String> handleChatRequest(
        @RequestBody UserRequest userRequest
    ) {
        String content = userRequest.getContent();
        return chatService.getChatResponse(content)
            .doOnError(e -> log.error("Error during processing: {}", e.getMessage()))
            .onErrorReturn("An error has occurred. Please try again.");
    }
}
