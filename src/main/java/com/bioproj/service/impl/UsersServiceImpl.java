package com.bioproj.service.impl;

import com.bioproj.pojo.Users;
import com.bioproj.repository.UsersRepository;
import com.bioproj.service.IUsersService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements IUsersService {

    @Resource
    private UsersRepository repository;

    @Override
    public boolean getUserNo(String userNo) {
        Users user = new Users();
        user.setUserNo(userNo);
        Example<Users> example = Example.of(user);
        return !repository.findOne(example).isEmpty();
    }

    @Override
    public boolean getUserNoAndpassWord(String userNo, String passWord) {
        Users user = new Users();
        user.setUserNo(userNo);
        user.setPassWord(passWord);
        Example<Users> example = Example.of(user);
        return repository.findOne(example).isEmpty();
    }

    @Override
    public Users add(Users users) {
        boolean userNo = getUserNo(users.getUserNo());
        if (userNo) {
            throw new RuntimeException("用户名已存在！请重新设置一个");
        }else {
            return repository.save(users);
        }
    }

    @Override
    public Users resetting(String userNo) {
        boolean userNo1 = getUserNo(userNo);
        if (userNo1) {
            Users user = new Users();
            user.setUserNo(userNo);
            Example<Users> example = Example.of(user);
            Users users = repository.findOne(example).orElseThrow();
            users.setPassWord("123456");
            Users save = repository.save(users);
            return save;
        }else {
            throw new RuntimeException("用户名不已存在！请检查你输入的用户名");
        }
    }
}
