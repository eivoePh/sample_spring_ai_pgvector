package com.example.samplespringaipgvector.prompt;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class PromptService {

    @Value("classpath:prompts/system-prompt.st")
    private Resource systemNdaPrompt;
    private final VectorStore vectorStore;

    public Prompt generatepromptFromClientPrompt(String userPrompt) {

        //search vector store
        SearchRequest searchRequest = SearchRequest.builder().query(userPrompt).topK(2).build();
        var documents = vectorStore.similaritySearch(searchRequest);
        var documentsText = Objects.isNull(documents) ? "" : documents.stream().map(Document::getText).collect(Collectors.joining("\n"));
        log.info("documentsText: {}", documentsText);

        //generate prompt
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemNdaPrompt);
        var systemMessage = systemPromptTemplate.createMessage(Map.of("documents", documentsText));
        log.info("systemPromptTemplate: {}", systemPromptTemplate);
        UserMessage userMessage = new UserMessage(userPrompt);
        return new Prompt(List.of(systemMessage, userMessage));
    }
    
}
