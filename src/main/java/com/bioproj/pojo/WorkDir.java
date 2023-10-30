package com.bioproj.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class WorkDir {

    private String id;

    private String path;

    private String name;
}
