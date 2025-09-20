package org.example.controller;

import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/xiaoyuan")
public class XiaoyuanController {

    private String MODEL_NAME = "deepseek-r1:1.5b";
    private String BASE_URL = "http://localhost:11434";

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chat(@RequestParam String userMessage) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        StreamingChatLanguageModel model = OllamaStreamingChatModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODEL_NAME)
                .temperature(0.0)
                .build();

        CompletableFuture<ChatResponse> futureResponse = new CompletableFuture<>();

        model.chat(userMessage, new StreamingChatResponseHandler() {
            @Override
            public void onPartialResponse(String partialResponse) {
                try {
                    // 流式发送部分响应
                    emitter.send(partialResponse);
                } catch (IOException e) {
                    emitter.completeWithError(e);
                }
            }

            @Override
            public void onCompleteResponse(ChatResponse completeResponse) {
                // 完成流式传输
                emitter.complete();
                futureResponse.complete(completeResponse);
            }

            @Override
            public void onError(Throwable error) {
                emitter.completeWithError(error);
                futureResponse.completeExceptionally(error);
            }
        });

        return emitter;
    }
}
