package com.ev.trading.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ev.trading.entity.Collect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author EV
 * @date 2021/3/31 18:47
 */
@Mapper
public interface CollectDao extends BaseMapper<Collect> {

    Collect getOneById(@Param("userId") Integer userId, @Param("commodityId") Integer commodityId);

    List<Collect> listByUserId(@Param("id") Integer id);
}
