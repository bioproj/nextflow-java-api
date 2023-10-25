package com.bioproj.controller;

import com.bioproj.pojo.Repos;
import com.bioproj.service.IGiteaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/gitea")
@Slf4j
public class GiteaController {

    @Autowired
    IGiteaService giteaService;

    @GetMapping("{org}/list")
    public List<Repos> listRepos(@PathVariable("org") String org){
        return giteaService.listRepos(org);
    }


}
