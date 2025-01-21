package com.example.qwenexample.ctl;

import java.util.List;

import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingOptions;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class EmbeddingCtl2 {

    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    @GetMapping("/embedding2")
    public EmbeddingResponse embedding(@RequestParam(value = "msg") String msg) {
        var dashScopeApi = new DashScopeApi(apiKey);
        var embeddingModel = new DashScopeEmbeddingModel(dashScopeApi,
                MetadataMode.EMBED,
                DashScopeEmbeddingOptions.builder().withModel("text-embedding-v2").build());
        var embeddingResponse = embeddingModel.embedForResponse(List.of("你好"));
        return embeddingResponse;
    }
}
