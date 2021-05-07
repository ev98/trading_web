package com.ev.trading.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ev.trading.dao.CommentDao;
import com.ev.trading.entity.Comment;
import com.ev.trading.service.CommentService;
import com.ev.trading.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author EV
 * @date 2021/4/21 23:19
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Autowired
    CommentDao commentDao;

    @Override
    public List<Comment> findByPostId(Integer id) {
        return commentDao.findByPostId(id);
    }
}
