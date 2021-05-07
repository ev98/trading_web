package com.ev.trading.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ev.trading.dao.CategoryDao;
import com.ev.trading.entity.Category;
import com.ev.trading.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * @author EV
 * @date 2021/3/31 18:58
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {
}
