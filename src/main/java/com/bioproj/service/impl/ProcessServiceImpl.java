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
import java.util.List;

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
    public void launch(Workflows workflows, Boolean resume) {

        try {
            String runName = getRunName(workflows);

            Path workPath = Paths.get(workflows.getWorkDir(),workflows.getId());
            if(!workPath.toFile().exists()){
                Files.createDirectories(workPath);
            }
            String cmdLog = workPath+ File.separator+".workflow.log";
            String logPath = workflows.getOutputDir()+File.separator+"nextflow.log";




            final String[] args  = new String[]{
                    "nf","-log",logPath,"run",workflows.getPipeline(),"-name",runName,"-profile",workflows.getProfiles()
            };

            OutputStream logStream=null;
            try {
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.directory(workPath.toFile());

                processBuilder.command(args);
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();
                long pid = process.pid();
                log.info(workPath.toString()+", pid:"+pid);
                log.info("{}: saving workflow pid...",pid);
                workflows.setPid(pid);
                workflowService.save(workflows);
                log.info("{}: waiting for workflow to finish...",pid);

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
                if(exit==0){
                    workflows.setStatus("completed");
                    log.info("{}: workflow completed",pid);
                    workflowService.save(workflows);
                }else {
                    workflows.setStatus("failed");
                    log.info("{}: workflow failed",pid);
                    workflowService.save(workflows);
                }
//                log.info("{}: saving output data...",pid);


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



//            executorService.submit(new Runnable() {
//                @Override
//                public void run() {
//
//            });
//


        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }
}
