package com.bioproj.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@Document
public class Workflows {
    @Id
//    @Field(name = "_id")
    private String id;
    private String name;
    @Field(name = "date_created")
    private Date dateCreated;
    @Field(name = "date_submitted")
    private Date dateSubmitted;
    @Field(name = "output_files")
    private List<String> outputFiles;

    private String pipeline;
    private String status;
    private String profiles;
    private String revision;
    @Field(name = "params_format")
    private String paramsFormat;
    @Field(name = "params_data")
    private String paramsData;


    @Field(name = "input_dir")
    private String inputDir;
    @Field(name = "output_dir")
    private String outputDir;

    @Field(name = "input_files")
    private List<String> inputFiles;

    @Field(name = "output_data")
    private Boolean outputData;

    private long pid;

    private String log;
    private Integer attempts;

    private String workDir;
}
