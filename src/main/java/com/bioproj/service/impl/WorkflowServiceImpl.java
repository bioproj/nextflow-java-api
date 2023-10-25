package com.bioproj.service.impl;

import com.bioproj.pojo.Workflows;
import com.bioproj.repository.WorkflowRepository;
import com.bioproj.service.IWorkflowService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WorkflowServiceImpl implements IWorkflowService {

    @Autowired
    private WorkflowRepository workflowRepository;

    @Override
    public  Workflows  del(String id){
        Workflows workflows = findById(id);
        workflowRepository.delete(workflows);
        return workflows;
    }

    @Override
    public  Page<Workflows>  page(Pageable pageable){
        Page<Workflows> workflowPage = workflowRepository.findAll(pageable);
        return workflowPage;
    }

    @Override
    public Workflows save(Workflows workflowParam){
        Workflows workflow = workflowRepository.save(workflowParam);
        return workflow;
    }
    @Override
    public Workflows update(String id, Workflows workflowParam){
        Workflows workflows = findById(id);
        BeanUtils.copyProperties(workflowParam,workflows,"id");
        workflows = workflowRepository.save(workflows);



        return workflows;
    }

    @Override
    public Workflows findById(String id){
        Workflows workflows = workflowRepository.findById(id).orElseThrow(() -> new RuntimeException("Workflows not found."));
        return workflows;
    }
}
