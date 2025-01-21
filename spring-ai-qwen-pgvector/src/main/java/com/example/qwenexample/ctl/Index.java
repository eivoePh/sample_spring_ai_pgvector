package com.example.qwenexample.ctl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.qwenexample.prompt.PromptService;

@RestController
public class Index {

    private final ChatClient chatClient;
    private final PromptService promptService;

    public Index(ChatClient.Builder builder, PromptService promptService) {
        this.chatClient = builder.build();
        this.promptService = promptService;
    }

    @GetMapping("/chat")
    public String index(@RequestParam(value = "msg") String msg) {
        var prompt = promptService.generatePrompt(msg);
        return this.chatClient.prompt(prompt).call().content();
    }
}