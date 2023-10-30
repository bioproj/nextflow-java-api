package com.bioproj.pojo;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Data
@Document
public class Task {
    @Id
    private String id;
    private String event; //process_completed  completed
    private String runName;
    @CreatedDate
    private Date time;

    private Map<String,Object> metadata;

    private Map<String,Object> trace;

}
