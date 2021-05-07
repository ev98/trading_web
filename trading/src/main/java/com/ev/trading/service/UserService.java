package com.ev.trading.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ev.trading.entity.User;

import java.math.BigDecimal;

/**
 * @author EV
 * @date 2021/3/31 18:51
 */
public interface UserService extends IService<User> {
    void recharge(BigDecimal account, Integer id);

    void withdrawal(BigDecimal account, Integer id);
}
