package com.ev.trading.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ev.trading.entity.Image;

import java.util.List;

/**
 * @author EV
 * @date 2021/3/31 18:53
 */
public interface ImageService extends IService<Image> {

    String getImgIds(List<Image> images);

    List<Image> listNew(Integer userId);

    List<Image> listUpdate(Integer id,Integer userId);
}
