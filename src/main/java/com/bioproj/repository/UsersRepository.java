package com.bioproj.repository;

import com.bioproj.pojo.Users;
import com.bioproj.pojo.Workflows;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<Users, String> {
}
