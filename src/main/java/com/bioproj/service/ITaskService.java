package com.bioproj.service;

import com.bioproj.pojo.Task;
import com.bioproj.pojo.Workflows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ITaskService {

    Page<Task> page(Pageable pageable);

    Task save(Task task);

    Task findById(String id);

    Map<String, String> findLogById(String id);

    List<Task> list();


}
