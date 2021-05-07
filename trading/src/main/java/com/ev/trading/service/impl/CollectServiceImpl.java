package com.ev.trading.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ev.trading.dao.CollectDao;
import com.ev.trading.entity.Collect;
import com.ev.trading.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author EV
 * @date 2021/3/31 19:01
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectDao, Collect> implements CollectService {

    @Autowired
    CollectDao collectDao;

    @Override
    public Collect getOneById(Integer userId, Integer commodityId) {
        return collectDao.getOneById(userId,commodityId);
    }

    @Override
    public List<Integer> listByUserId(Integer id) {
        List<Collect> collectList = collectDao.listByUserId(id);
        List<Integer> commodityIds = new ArrayList<>();
        for (Collect collect : collectList) {
            commodityIds.add(collect.getCollectCommodityId());
        }
        return commodityIds;
    }
}
