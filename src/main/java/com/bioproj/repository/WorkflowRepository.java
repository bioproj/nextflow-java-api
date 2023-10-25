package com.bioproj.repository;

import com.bioproj.pojo.Workflows;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowRepository  extends MongoRepository<Workflows, String> {


}
