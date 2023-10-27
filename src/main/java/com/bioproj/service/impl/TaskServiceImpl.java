package com.bioproj.service.impl;

import com.bioproj.pojo.Task;
import com.bioproj.pojo.Workflows;
import com.bioproj.repository.TaskRepository;
import com.bioproj.repository.WorkflowRepository;
import com.bioproj.service.ITaskService;
import com.bioproj.service.IWorkflowService;
import com.bioproj.utils.FileUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class TaskServiceImpl implements ITaskService {
    @Value("${workDir}")
    String workDir;

    @Resource
    private TaskRepository repository;

    @Resource
    private IWorkflowService workflowService;


    @Override
    public Page<Task> page(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Task save(Task task) {
        String event = task.getEvent();
        Map<String, Object> trace = task.getTrace();
        if ("process_completed".equals(event)) {
            List<String> filenames = new ArrayList<>(Arrays.asList(".command.log",".command.out",".command.err"));
            String workdir1 = trace.get("workdir").toString();



        }
        if ("completed".equals(event)) {
            String workFlow_Id = task.getRunName().split("-")[1];
            Workflows workflows = workflowService.findById(workFlow_Id);
            String task_workflow_status = task.getMetadata().get("workflow").toString();
            if ("success".equals(task_workflow_status)) {
                workflows.setStatus("completed");
            }else{
                workflows.setStatus("failed");
            }
            workflowService.update(workFlow_Id,workflows);
        }
        return repository.save(task);
    }

    @Override
    public Task findById(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Workflows not found."));
    }

    @Override
    public Map<String, String> findLogById(String id) {
        Task task = repository.findById(id).orElseThrow(() -> new RuntimeException("Workflows not found."));
        String workdir_path = task.getTrace().get("workdir").toString();
        Map<String,String> data = new HashMap<>();
        data.put("id",task.getId());
        data.put("out","");
        data.put("err","");
        String out_file_path = workDir + workdir_path + ".command.out";
        String err_file_path = workDir + workdir_path + ".command.err";
        File out_file = new File(out_file_path);
        if (out_file.exists()){
            String out = FileUtils.openFile(out_file);
            data.put("out",out);
        }
        File err_file = new File(err_file_path);
        if (err_file.exists()){
            String err = FileUtils.openFile(err_file);
            data.put("err",err);
        }
        return data;
    }

    @Override
    public List<Task> list() {
        return repository.findAll();
    }
}
