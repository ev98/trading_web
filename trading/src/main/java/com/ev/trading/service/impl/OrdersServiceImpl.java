package com.ev.trading.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ev.trading.dao.OrdersDao;
import com.ev.trading.entity.Orders;
import com.ev.trading.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author EV
 * @date 2021/4/20 19:03
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersDao, Orders> implements OrdersService {

    @Autowired
    OrdersService ordersService;

    @Override
    public List<Integer> listById(List<Orders> ordersList) {
        List<Integer> ids = new ArrayList<>();
        for (Orders order : ordersList) {
            ids.add(order.getCommodityId());
        }
        return ids;
    }
}
