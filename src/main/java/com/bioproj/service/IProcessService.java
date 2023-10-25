package com.bioproj.service;

import com.bioproj.pojo.Workflows;

public interface IProcessService {
    void launch(Workflows workflows, Boolean resume);
}
