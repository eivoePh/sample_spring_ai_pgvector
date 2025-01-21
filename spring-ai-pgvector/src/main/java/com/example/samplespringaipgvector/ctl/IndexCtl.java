package com.example.samplespringaipgvector.ctl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.samplespringaipgvector.prompt.PromptService;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class IndexCtl {

    private final ChatClient chatClient;
    private final PromptService promptService;

    public IndexCtl(ChatClient.Builder builder, PromptService ragService) {
        this.chatClient = builder.build();
        this.promptService = ragService;
    }

    @GetMapping(value="/chat")
    public String chat(@RequestParam(value = "msg")String userPrompt) {
        var prompt = promptService.generatepromptFromClientPrompt(userPrompt);
        return chatClient.prompt(prompt).call().content();
    }
}
