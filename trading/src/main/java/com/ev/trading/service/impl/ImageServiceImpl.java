package com.ev.trading.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ev.trading.dao.ImageDao;
import com.ev.trading.entity.Image;
import com.ev.trading.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author EV
 * @date 2021/3/31 18:59
 */
@Service
public class ImageServiceImpl extends ServiceImpl<ImageDao, Image> implements ImageService {

    @Autowired
    ImageService imageService;

    @Override
    public String getImgIds(List<Image> images) {
        StringBuilder ids = new StringBuilder();
        boolean flag = false;
        for (Image image : images) {
            if (flag) {
                ids.append(",");
            } else {
                flag = true;
            }
            ids.append(image.getId());
        }
        return ids.toString();
    }

    @Override
    public List<Image> listNew(Integer userId) {
        QueryWrapper<Image> status = new QueryWrapper<Image>().eq("status", 0).eq("user_id",userId);
        List<Image> list = imageService.list(status);
        UpdateWrapper<Image> update = new UpdateWrapper<Image>().set("status", 1);
        imageService.update(update);
        return list;
    }

    @Override
    public List<Image> listUpdate(Integer id,Integer userId) {
        QueryWrapper<Image> status = new QueryWrapper<Image>().eq("status", 0).eq("user_id",userId);
        List<Image> list = imageService.list(status);

        //将已存在的图片删除
        if (list.size() > 0) {
            QueryWrapper<Image> commodity_id = new QueryWrapper<Image>().eq("commodity_id", id);
            imageService.remove(commodity_id);
        }

        UpdateWrapper<Image> update = new UpdateWrapper<Image>().set("status", 1);
        imageService.update(update);
        return list;
    }


}
