package com.springboot.shiro.service.impl;

import com.springboot.shiro.bean.User;
import com.springboot.shiro.service.UserService;
import org.springframework.stereotype.Service;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Override
    public User getUserByName(String getMapByName) {
        //模拟数据库查询，正常情况此处是从数据库或者缓存查询。
        return getMapByName(getMapByName);
    }

    /**
     * 模拟数据库查询
     * @param getMapByName
     * @return
     */
    private User getMapByName(String getMapByName) {

       return null;
    }
}
