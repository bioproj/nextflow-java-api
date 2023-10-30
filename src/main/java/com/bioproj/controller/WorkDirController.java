package com.bioproj.controller;

import com.bioproj.pojo.WorkDir;
import com.bioproj.repository.WorkDirRepository;
import com.bioproj.utils.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/api/workdir")
@Slf4j
public class WorkDirController {

    @Autowired
    private WorkDirRepository workDirRepository;

    @GetMapping("list")
    public BaseResponse list(){
        List<WorkDir> all = workDirRepository.findAll();
        return BaseResponse.ok(all);
    }
    @GetMapping("page")
    public Page<WorkDir> page(@PageableDefault(sort = {"id"},direction = DESC) Pageable pageable){
        return workDirRepository.findAll(pageable);
    }

    @GetMapping("id/{id}")
    public BaseResponse id(@PathVariable("id")String id){
        WorkDir all = workDirRepository.findById(id).orElse(null);
        return BaseResponse.ok(all);
    }

    @PutMapping("add")
    public BaseResponse add(@RequestBody WorkDir e){
        WorkDir all = workDirRepository.save(e);
        return BaseResponse.ok(all);
    }

    @PostMapping("update")
    public BaseResponse update(@RequestBody WorkDir e){
        String id = e.getId();
        if (null == id) {
            throw new RuntimeException("id 不能为空");
        }
        WorkDir all = workDirRepository.save(e);
        return BaseResponse.ok(all);
    }

    @DeleteMapping("id/{id}")
    public BaseResponse delete(@PathVariable("id")String id){
        WorkDir all = new WorkDir();
        all.setId(id);
        workDirRepository.delete(all);
        return BaseResponse.ok("删除成功！");
    }





}
