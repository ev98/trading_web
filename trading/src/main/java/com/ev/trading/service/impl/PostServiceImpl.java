package com.ev.trading.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ev.trading.dao.PostDao;
import com.ev.trading.entity.Post;
import com.ev.trading.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author EV
 * @date 2021/4/21 17:00
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostDao, Post> implements PostService {

    @Autowired
    PostDao postDao;

    @Override
    public List<Post> findAll() {
        return postDao.findAll();
    }

    @Override
    public List<Post> findByUserId(Integer userId) {
        return postDao.findByUserId(userId);
    }

    @Override
    public Post findById(Integer id) {
        return postDao.findById(id);
    }

    @Override
    public List<Post> findBySearch(String content) {
        return postDao.findBySearch(content);
    }
}
