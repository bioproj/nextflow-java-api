package com.bioproj.controller;


import com.bioproj.pojo.Log;
import com.bioproj.pojo.Workflows;
import com.bioproj.service.IProcessService;
import com.bioproj.service.IWorkflowService;
import com.bioproj.utils.BaseResponse;
import com.bioproj.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/api/workflows")
@Slf4j
public class WorkflowController {

    @Value("${workDir}")
    String workDir;

    @Autowired
    IWorkflowService workflowService;

    @Autowired
    IProcessService processService;

    @GetMapping
    public List<Workflows> workflowQueryHandler(@PageableDefault(sort = {"id"},direction = DESC) Pageable pageable){
        return workflowService.page(pageable).getContent();
    }
    @GetMapping("/0")
    public Workflows WorkflowCreate(){
        Workflows workflows = new Workflows();
        workflows.setId("0");
        workflows.setProfiles("standard");
        workflows.setRevision("master");
        workflows.setInputDir("input");
        workflows.setOutputDir("output");
        return workflows;
    }
    @PostMapping("/0")
    public Workflows WorkflowCreateHandler(@RequestBody Workflows workflows ){
        workflows.setId(null);
        workflows.setDateCreated(new Date());
        workflows.setStatus("nascent");
        workflows.setWorkDir(workDir);
        workflows.setAttempts(1);
        workflowService.save(workflows);

        Path workPath = Paths.get(workflows.getWorkDir(),workflows.getId());
        if(workflows.getParamsFormat()!=null && workflows.getParamsData()!=null){
            Path paramPath = Paths.get(workPath.toString(), "params." + workflows.getParamsFormat());
            FileUtils.saveFile(paramPath.toFile(),workflows.getParamsData());
        }

        return workflows;
    }

    @GetMapping("/{id}")
    public Workflows workflowEditHandler(@PathVariable("id") String id){
        Workflows workflows = workflowService.findById(id);
        return workflows;
    }
    @DeleteMapping("/{id}")
    public Workflows workflowDeleteHandler(@PathVariable("id") String id){
        Workflows workflows = workflowService.del(id);
        return workflows;
    }

    @PostMapping("/{id}")
    public Workflows workflowUpdateHandler(@PathVariable("id") String id, @RequestBody Workflows workflowsParam){

        Workflows workflows = workflowService.update(id,workflowsParam);

        Path workPath = Paths.get(workflows.getWorkDir(),workflows.getId());
        if(workflows.getParamsFormat()!=null && workflows.getParamsData()!=null){
            Path paramPath = Paths.get(workPath.toString(), "params." + workflows.getParamsFormat());
            FileUtils.saveFile(paramPath.toFile(),workflows.getParamsData());
        }

        return workflows;
    }


    @RequestMapping("/{id}/launch")
    public BaseResponse WorkflowLaunchHandler(@PathVariable("id") String id){
        Workflows workflows = workflowService.findById(id);
        if(workflows.getWorkDir()==null){
            workflows.setWorkDir(workDir);
        }


//        String inputDir = workflows.getInputDir();
//        Path src = Paths.get(inputDir,"nextflow.config");
//        Path dst = Paths.get(workDir, "nextflow.config");
//
//        if(dst.toFile().exists()){
//            dst.toFile().delete();
//        }
//        if(src.toFile().exists()){
//            try {
//                Files.copy(src,dst);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        // append additional settings to nextflow.config
//        // TODO

        workflows.setStatus("running");
        workflows.setDateSubmitted(new Date());
        if(workflows.getAttempts()==null){
            workflows.setAttempts(1);
        }else {
            workflows.setAttempts(workflows.getAttempts()+1);
        }

        workflows = workflowService.update(workflows.getId(), workflows);
        Boolean resume =true;

        processService.launch(workflows,resume);

        return BaseResponse.ok("success!");
    }
    @RequestMapping("/{id}/cancel")
    public Workflows WorkflowCancelHandler(@PathVariable("id") String id){
        Workflows workflows = workflowService.findById(id);
        workflows.setStatus("failed");
        workflows.setPid(-1);
        workflows = workflowService.save(workflows);
        return workflows;

    }

    @GetMapping("/{id}/log")
    public Log WorkflowLogHandler(@PathVariable("id") String id){
        Workflows workflows = workflowService.findById(id);
        Path logFile = Paths.get(workflows.getWorkDir(),workflows.getId(),".workflow.log");
        String logStr = "";
        if(logFile.toFile().exists()){
            logStr = FileUtils.openFile(logFile.toFile());
        }

        Log log = new Log();
        log.setLog(logStr);
        log.setAttempts(workflows.getAttempts());
        log.setStatus(workflows.getStatus());
        log.setId(workflows.getId());
        return log;

    }

    @GetMapping("/add")
    public String add( ){
        Workflows workflow = new Workflows();


        workflow.setOutputFiles(Arrays.asList("aaa","bbb"));
        workflow.setName("12346");

        workflowService.save(workflow);
        return "";
    }
}
