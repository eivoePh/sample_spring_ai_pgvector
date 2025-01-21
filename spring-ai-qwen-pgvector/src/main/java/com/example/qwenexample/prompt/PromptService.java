package com.example.qwenexample.prompt;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromptService {
    
    @Value("classpath:prompt/system-prompt.st")
    private Resource systemNdaPrompt;
    private final VectorStore vectorStore;

    public Prompt generatePrompt(String clientPrompt) {
        //search vector store
        var searchRequest = SearchRequest.builder().query(clientPrompt).topK(2).build();
        var documents = vectorStore.similaritySearch(searchRequest);
        var documentContent = documents.stream().map(Document::getText).collect(Collectors.joining("\n"));
        log.info("documentContent: {}", documentContent);

        // generate prompt
        var systemNdaPromptTemplate = new SystemPromptTemplate(systemNdaPrompt);
        var systemMessage = systemNdaPromptTemplate.createMessage(Map.of("documents", documentContent));
        log.info("systemMessage: {}", systemMessage);
        var userMessage = new UserMessage(clientPrompt);
        return new Prompt(List.of(systemMessage, userMessage));
    }
}
