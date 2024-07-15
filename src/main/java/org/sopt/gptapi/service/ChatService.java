package org.sopt.gptapi.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.gptapi.common.dto.Prompt;
import org.sopt.gptapi.domain.reply.Reply;
import org.sopt.gptapi.domain.reply.ReplyRepository;
import org.sopt.gptapi.domain.user.UserRepository;
import org.sopt.gptapi.service.reply.ReplyService;
import org.sopt.gptapi.service.user.UserRetriever;
import org.sopt.gptapi.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException.BadRequest;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ChatService {

    public static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    private final AsyncChatgptService asyncChatgptService;
    private final ReplyService replyService;
    private final UserService userService;
    private final UserRetriever userRetriever;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;

    public Mono<String> getChatResponse(String content, Long userId, String createdDate) {
        String message = Prompt.MESSAGE.getMessage() + content + "\n칭찬 :";
        return asyncChatgptService.sendMessage(message)
            .flatMap(response -> {
                logger.info("ChatGPT response: {}", response);
                return replyService.saveReply(response, userId, createdDate);
            })
            .doOnError(BadRequest.class, e -> {
                logger.error("BadRequest error: {}", e.getMessage(), e);
            })
            .onErrorResume(BadRequest.class, e -> Mono.just("BadRequest error"))
            .doOnError(e -> {
                logger.error("General error: {}", e.getMessage(), e);
            })
            .onErrorResume(e -> Mono.just("General error"))
            .doOnSuccess(result -> logger.info("Final result: {}", result));
    }

    public Mono<String> saveReply(String content, Long userId, String createdDate) {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
            .flatMap(user -> Mono.fromCallable(() -> {
                Reply reply = Reply.createDiary(content, false, LocalDateTime.parse(createdDate), user.getId());
                replyRepository.save(reply);  // 인스턴스 메서드 호출
                return content;
            }).subscribeOn(Schedulers.boundedElastic()));
    }
}
