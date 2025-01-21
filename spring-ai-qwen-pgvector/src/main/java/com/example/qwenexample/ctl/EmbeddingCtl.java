package com.example.qwenexample.ctl;

import java.util.List;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmbeddingCtl {
    
    private final EmbeddingModel embeddingModel;

    public EmbeddingCtl(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @GetMapping("/embedding")
    public EmbeddingResponse embedding(@RequestParam(value = "msg") String msg) {
        EmbeddingResponse embeddingResponse = embeddingModel.embedForResponse(List.of(msg));
        return embeddingResponse;
    }
}
