package com.bioproj.service;

import com.bioproj.pojo.Task;
import com.bioproj.pojo.Workflows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITaskService {

    Page<Task> page(Pageable pageable);

    Task save(Task task);

    Task findById(String id);


}
