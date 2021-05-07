package com.ev.trading.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ev.trading.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author EV
 * @date 2021/3/31 18:47
 */
@Mapper
public interface CategoryDao extends BaseMapper<Category> {
}
