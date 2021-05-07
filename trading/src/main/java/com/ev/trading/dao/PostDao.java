package com.ev.trading.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ev.trading.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author EV
 * @date 2021/4/21 16:59
 */
@Mapper
public interface PostDao extends BaseMapper<Post> {

    List<Post> findAll();

    List<Post> findByUserId(@Param("id") Integer userId);

    Post findById(@Param("id") Integer id);

    List<Post> findBySearch(@Param("content") String content);
}
