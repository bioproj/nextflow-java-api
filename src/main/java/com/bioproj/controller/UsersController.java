package com.bioproj.controller;

import com.bioproj.pojo.Users;
import com.bioproj.service.IUsersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Resource
    private IUsersService usersService;


    @PostMapping("login")
    public String login(@RequestBody Users users){
        boolean userNo = usersService.getUserNo(users.getUserNo());
        if (userNo) {
            boolean userNoAndpassWord = usersService.getUserNoAndpassWord(users.getUserNo(), users.getPassWord());
            if (userNoAndpassWord) {
                throw  new RuntimeException("密码错误！");
            }else {
                return UUID.randomUUID().toString();
            }
        }else {
            throw  new RuntimeException("用户不存在");
        }
    }


    @PostMapping("add")
    public Users add(@RequestBody Users users){
        return usersService.add(users);
    }

    @PutMapping("resetting/{userNo}")
    public Users resetting(@PathVariable("userNo") String userNo){
        return usersService.resetting(userNo);
    }

}
