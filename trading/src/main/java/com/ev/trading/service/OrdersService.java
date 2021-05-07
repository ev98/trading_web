package com.ev.trading.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ev.trading.entity.Orders;

import java.util.List;

/**
 * @author EV
 * @date 2021/4/20 19:03
 */
public interface OrdersService extends IService<Orders> {
    List<Integer> listById(List<Orders> ordersList);
}
