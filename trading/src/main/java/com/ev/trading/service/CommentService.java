package com.ev.trading.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ev.trading.entity.Comment;

import java.util.List;

/**
 * @author EV
 * @date 2021/4/21 23:19
 */
public interface CommentService extends IService<Comment> {
    List<Comment> findByPostId(Integer id);
}
