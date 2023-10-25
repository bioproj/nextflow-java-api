package com.bioproj.pojo;

import lombok.Data;

@Data
public class Log {
    private String id;
    private String log;
    private String status;
    private int attempts;

}
