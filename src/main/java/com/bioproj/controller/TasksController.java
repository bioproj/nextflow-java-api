package com.bioproj.controller;

import com.bioproj.pojo.Task;
import com.bioproj.pojo.Workflows;
import com.bioproj.service.ITaskService;
import com.bioproj.utils.FileUtils;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    @RequestMapping
    public String  test(){
        return "aaa";
    }

    @Resource
    private ITaskService taskService;

    @GetMapping
    public List<Task> list(@PageableDefault(sort = {"id"},direction = DESC) Pageable pageable){
        return taskService.page(pageable).getContent();
    }

    @PostMapping
    public Task save(@RequestBody Task task){
        task.setTime(new Date());
        Task save = taskService.save(task);
        return save;
    }

    @GetMapping("/{id}/log")
    public Map<String, String> log(@RequestParam("id") String id){
        return taskService.findLogById(id);
    }

    @GetMapping("/pipelines")
    public List<Task> pipelines(){
            return taskService.list();
    }

    @GetMapping("/pipelines/{name}")
    public Map<String,String> pipelines(@RequestParam("name") String name){
        return new HashMap<>();
    }
    @GetMapping("/archive/{name}/download")
    public Map<String,String> download(@RequestParam("name") String name){
        return new HashMap<>();
    }
    @GetMapping("/archive/{name}")
    public Map<String,String> archive(@RequestParam("name") String name){
        return new HashMap<>();
    }
    @GetMapping("/visualize")
    public Map<String,String> visualize(@RequestParam("name") String name){
        return new HashMap<>();
    }
    @GetMapping("/{id}")
    public Task id(@RequestParam("id") String id){
        return taskService.findById(id);
    }




}
