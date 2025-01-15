package com.example.samplespringaipgvector.csvdata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

@SpringBootTest
public class CsvParserServiceTest {

    @Autowired
    private CsvParserService csvParserService;
    
    @Test
    public void testParseCsv() {
        var data = csvParserService.getContentFromCsv(new ClassPathResource("sample_nda.csv"));
        assertEquals(data.size(), 18);
    }

}