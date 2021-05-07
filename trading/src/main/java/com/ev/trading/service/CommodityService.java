package com.ev.trading.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ev.trading.entity.Commodity;
import com.ev.trading.entity.Image;

import java.util.Date;
import java.util.List;

/**
 * @author EV
 * @date 2021/3/31 18:52
 */
public interface CommodityService extends IService<Commodity> {

    List<Commodity> listByView();

    List<Commodity> listByTime();

    List<Image> getImages(String imgIds,Integer commodityId);

    void updateFirstPicture(Integer id, String firstPicture);

    List<Commodity> listAll();

    Commodity findNew(Integer id);

    List<Commodity> findAllByUserId013(Integer id);

    Commodity findById(Integer id);

    List<Commodity> findAllByUserIdCollect(List<Integer> collectCommodityIds,int pageNum);

    List<Commodity> findAllByCategoryIdAndViews(Integer id);

    List<Commodity> findAllByCategoryIdAndUpdateTime(Integer id);

    List<Commodity> findAllByCategoryIdAndPriceAsc(Integer id);

    List<Commodity> findAllByCategoryIdAndPriceDesc(Integer id);

    List<Commodity> findAllByViews();

    List<Commodity> findAllTime();

    List<Commodity> search(String query);

    List<Commodity> searchByView(String query);

    List<Commodity> searchByTime(String query);

    List<Commodity> searchByPriceAsc(String query);

    List<Commodity> searchByPriceDesc(String query);

    void updateStatusTo2(Integer id, Date date);

    List<Commodity> findByUserId12(Integer id,int pageNum);

    void updateView(Integer id, Integer view2);

    List<Commodity> findAllCommodityByTime013();

    void updateStatusTo1(Integer id);

    void updateStatusTo3(Integer id);

    List<Commodity> findCommodityBySearch(Commodity commodity);
}
