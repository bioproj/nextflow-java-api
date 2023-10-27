package com.bioproj.service;

import com.bioproj.pojo.Users;

public interface IUsersService {
    boolean getUserNo(String userNo);

    boolean getUserNoAndpassWord(String userNo, String passWord);

    Users add(Users users);

    Users resetting(String userNo);
}
