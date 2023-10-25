package com.bioproj.service;

import com.bioproj.pojo.Workflows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IWorkflowService {
    Workflows  del(String id);

    Page<Workflows> page(Pageable pageable);

    Workflows save(Workflows workflowParam);

    Workflows update(String id, Workflows workflowParam);

    Workflows findById(String id);
}
