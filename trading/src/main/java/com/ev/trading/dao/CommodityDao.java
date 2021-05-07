package com.ev.trading.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ev.trading.entity.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author EV
 * @date 2021/3/31 18:47
 */
@Mapper
public interface CommodityDao extends BaseMapper<Commodity> {

    List<Commodity> findAllCommodityByView();

    List<Commodity> findAllCommodityByTime();

    void updateFirstPicture(@Param("id") Integer id, @Param("firstPicture") String firstPicture);

    List<Commodity> findAll();

    List<Commodity> findAllByUserId(@Param("id") Integer id);

    List<Commodity> findAllByUserId013(@Param("id") Integer id);

    Commodity findById(@Param("id") Integer id);

    List<Commodity> findByIds(@Param("collectCommodityIds") List<Integer> collectCommodityIds);

    List<Commodity> findAllByCategoryIdAndViews(@Param("id") Integer id);

    List<Commodity> findAllByCategoryIdAndUpdateTime(@Param("id") Integer id);

    List<Commodity> findAllByCategoryIdAndPriceAsc(@Param("id") Integer id);

    List<Commodity> findAllByCategoryIdAndPriceDesc(@Param("id") Integer id);

    List<Commodity> search(@Param("query") String query);

    List<Commodity> searchByView(@Param("query") String query);

    List<Commodity> searchByTime(@Param("query") String query);

    List<Commodity> searchByPriceAsc(@Param("query") String query);

    List<Commodity> searchByPriceDesc(@Param("query") String query);

    void updateStatusTo2(@Param("id") Integer id,@Param("date") Date date);

    List<Commodity> findAllByUserId12(@Param("id") Integer id);

    void updateView(@Param("id") Integer id, @Param("view2") Integer view2);

    List<Commodity> findAllCommodityByTime013();

    void updateStatusTo1(@Param("id") Integer id);

    void updateStatusTo3(@Param("id") Integer id);

    List<Commodity> findCommodityBySearch(Commodity commodity);
}
