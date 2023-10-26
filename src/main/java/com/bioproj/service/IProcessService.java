package com.bioproj.service;

import com.bioproj.pojo.Workflows;

public interface IProcessService {
    /**
     *
     * @param workflows
     * @param resume 1开始 2 恢复 3 停止
     */
    void launchAndResume(Workflows workflows, Integer resume);

}
