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
    public Map<String,String> pipelines(@PathVariable("name") String name){
        return new HashMap<>();
    }
    @GetMapping("/archive/{name}/download")
    public Map<String,String> download(@PathVariable("name") String name){
        return new HashMap<>();
    }
    @GetMapping("/archive/{name}")
    public Map<String,String> archive(@PathVariable("name") String name){
        return new HashMap<>();
    }
    @GetMapping("/visualize")
    public Map<String,String> visualize(){
        return new HashMap<>();
    }
    @GetMapping("/{id}")
    public Task id(@PathVariable("id") String id){
        return taskService.findById(id);
    }




}
