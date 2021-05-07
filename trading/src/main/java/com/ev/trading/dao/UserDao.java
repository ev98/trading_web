package com.ev.trading.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ev.trading.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author EV
 * @date 2021/3/31 18:46
 */

@Mapper
public interface UserDao extends BaseMapper<User> {
}
