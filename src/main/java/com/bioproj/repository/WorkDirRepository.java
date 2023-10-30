package com.bioproj.repository;

import com.bioproj.pojo.WorkDir;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkDirRepository extends MongoRepository<WorkDir, String> {
}
