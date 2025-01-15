package com.example.samplespringaipgvector.loaddata;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoadDataCtl {

    @Value("classpath:data/1.txt")
    private Resource dataResource;
    private final VectorStore vectorStore;

    @GetMapping("/load_data")
    public String loadData() {

        List<Document> documents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(dataResource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.info("load line: {}", line);
                if(StringUtils.hasText(line)) {
                    documents.add( new Document(line, Map.of("file", "1.txt")));
                }
            }
            vectorStore.add(documents);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Data loaded successfully, data size: " + documents.size();
    }
}
