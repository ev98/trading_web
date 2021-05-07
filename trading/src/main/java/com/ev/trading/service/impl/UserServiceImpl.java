package com.ev.trading.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ev.trading.dao.UserDao;
import com.ev.trading.entity.User;
import com.ev.trading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author EV
 * @date 2021/3/31 18:55
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public void recharge(BigDecimal account, Integer id) {
        User user = userService.getById(id);
        BigDecimal newAccount = user.getAccount().add(account);
        System.out.println(newAccount);
        user.setAccount(newAccount);
        userService.updateById(user);
    }

    @Override
    @Transactional
    public void withdrawal(BigDecimal account, Integer id) {
        User user = userService.getById(id);
        BigDecimal newAccount = user.getAccount().subtract(account);
        System.out.println(newAccount);
        user.setAccount(newAccount);
        userService.updateById(user);
    }
}
