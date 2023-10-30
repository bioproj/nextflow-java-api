package com.bioproj.service.impl;

import com.bioproj.pojo.Workflows;
import com.bioproj.service.IProcessService;
import com.bioproj.service.IWorkflowService;
import com.bioproj.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
@Slf4j
public class ProcessServiceImpl implements IProcessService {



    @Autowired
    IWorkflowService workflowService;

    @Autowired
    private  ThreadPoolTaskExecutor executorService;
    public String getRunName(Workflows workflows){
        return "workflow-"+workflows.getId()+"-"+workflows.getAttempts();
    }


    @Override
    @Async("taskExecutor")
    public void launchAndResume(Workflows workflows, Integer resume) {
        try {
            String runName = getRunName(workflows);
            Path workPath = Paths.get(workflows.getWorkDir(),workflows.getId());
            if(!workPath.toFile().exists()){
                Files.createDirectories(workPath);
            }
            String cmdLog = workPath+ File.separator+".workflow.log";
            String logPath = workflows.getOutputDir()+File.separator+"nextflow.log";
            String[] args  = null;
            if (resume == 1) {
                args  = new String[]{
                        "/home/wangyang/bin/nf","-log",logPath,"run",workflows.getPipeline(),"-name",runName,"-profile",workflows.getProfiles(),"-latest"
                };
            }else if (resume == 2) {
                args  = new String[]{
                        "/home/wangyang/bin/nf","-log",logPath,"run",workflows.getPipeline(),"-name",runName,"-profile",workflows.getProfiles(),"-latest"
                };
            }else if (resume == 3){
                args  = new String[]{
                        "/usr/bin/kill","-9",String.valueOf(workflows.getPid())
                };
            }
            OutputStream logStream=null;
            try {
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.directory(workPath.toFile());
                processBuilder.command(args);
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();
                long pid = process.pid();
                if (resume == 1 || resume == 2) {
                    log.info(workPath.toString()+", pid:"+pid);
                    log.info("{}: saving workflow pid...",pid);
                    workflows.setPid(pid);
                    workflowService.save(workflows);
                    log.info("{}: waiting for workflow to finish...",pid);
                }
                if(resume == 3){
                    log.info("kill OK  {}: ",pid);
                }
                logStream = new FileOutputStream(cmdLog);
                try (final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))){
                    String line;
                    while ((line = reader.readLine()) != null) {
                        log.info(line);
                        String msg = Thread.currentThread().getName()+": "+line+"\n";
                        logStream.write(msg.getBytes());
                    }
                }
                process.waitFor();
                int exit = process.exitValue();
                if (resume == 1 || resume == 2) {
                    if(exit==0){
                        workflows.setStatus("completed");
                        log.info("{}: workflow completed",pid);
                        workflowService.save(workflows);
                    }else {
                        workflows.setStatus("failed");
                        log.info("{}: workflow failed",pid);
                        workflowService.save(workflows);
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                if(logStream!=null){
                    try {
                        logStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
