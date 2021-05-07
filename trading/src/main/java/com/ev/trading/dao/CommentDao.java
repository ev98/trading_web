package com.ev.trading.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ev.trading.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author EV
 * @date 2021/4/21 23:18
 */
@Mapper
public interface CommentDao extends BaseMapper<Comment> {

    List<Comment> findByPostId(@Param("id") Integer id);

}
