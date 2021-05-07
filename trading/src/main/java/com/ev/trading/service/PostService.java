package com.ev.trading.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ev.trading.entity.Post;

import java.util.List;

/**
 * @author EV
 * @date 2021/4/21 17:00
 */
public interface PostService extends IService<Post> {
    List<Post> findAll();

    List<Post> findByUserId(Integer userId);

    Post findById(Integer id);

    List<Post> findBySearch(String content);
}
