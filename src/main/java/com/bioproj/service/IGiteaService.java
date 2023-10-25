package com.bioproj.service;

import com.bioproj.pojo.Repos;

import java.util.List;

public interface IGiteaService {
    List<Repos> listRepos(String org);
}
