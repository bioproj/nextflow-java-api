package com.bioproj.controller;

import com.bioproj.pojo.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/api/model")
public class ModelController {

    @GetMapping("/config")
    public List<Task> config(){
        return null;
    }

    @PostMapping("/train")
    public Task train(){
        return null;
    }
    @PostMapping("/predict")
    public Task predict(){
        return null;
    }

}
