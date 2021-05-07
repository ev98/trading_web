package com.ev.trading.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ev.trading.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author EV
 * @date 2021/4/20 19:01
 */
@Mapper
public interface OrdersDao extends BaseMapper<Orders> {
}
