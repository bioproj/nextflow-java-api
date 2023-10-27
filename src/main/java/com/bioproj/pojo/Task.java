package com.bioproj.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

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
