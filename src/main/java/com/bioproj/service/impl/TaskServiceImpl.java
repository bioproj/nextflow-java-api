package com.bioproj.service.impl;

import com.bioproj.pojo.Task;
import com.bioproj.pojo.Workflows;
import com.bioproj.repository.TaskRepository;
import com.bioproj.service.ITaskService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskServiceImpl implements ITaskService {

    @Resource
    private TaskRepository repository;
    @Override
    public Page<Task> page(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Task save(Task task) {
        return repository.save(task);
    }

    @Override
    public Task findById(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Workflows not found."));
    }
}
