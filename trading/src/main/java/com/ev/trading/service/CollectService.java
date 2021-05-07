package com.ev.trading.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ev.trading.entity.Collect;

import java.util.List;

/**
 * @author EV
 * @date 2021/3/31 18:53
 */
public interface CollectService extends IService<Collect> {
    Collect getOneById(Integer userId, Integer id);

    List<Integer> listByUserId(Integer id);
}
