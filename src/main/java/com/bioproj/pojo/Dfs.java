package com.bioproj.pojo;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Dfs {
    private String taskId;
    private String status;
    private String hash;
    private String name;
    private String exit;
    private String submit;
    private String start;
    private String process;
    private String tag;
    private String module;
    private String container;
    private String attempt;
    private String script;
    private String scratch;
    private String workdir;
    private String queue;
    private String cpus;
    private String memory;
    private String disk;
    private String time;
    private String env;
    private String errorAction;
    private String complete;
    private String duration;
    private String realtime;
    private String nativeId;
}
