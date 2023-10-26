package com.bioproj.pojo;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document
public class Task {
    @Id
    private String id;
    private String event;
    private String runName;

    private Map<String,Object> metadata;






}
