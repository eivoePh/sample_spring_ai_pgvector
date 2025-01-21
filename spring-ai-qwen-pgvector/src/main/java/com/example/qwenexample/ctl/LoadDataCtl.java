package com.example.qwenexample.ctl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoadDataCtl {
    
    @Value("classpath:data.txt")
    private Resource dataResource;
    private final VectorStore vectorStore;

    @GetMapping("/load_data")
    public String loadData() {
        var documents = new ArrayList<Document>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(dataResource.getInputStream()))) {
            var line = "";
            while ((line = reader.readLine()) != null) {
                log.info("line: {}", line);
                if(StringUtils.hasText(line)) {
                    documents.add(new Document(line, Map.of("file", "data.txt")));
                }
            }
            vectorStore.add(documents);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return "data load success, data size: " + documents.size();
    }
}
