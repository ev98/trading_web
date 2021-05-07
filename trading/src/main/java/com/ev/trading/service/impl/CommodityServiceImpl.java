package com.ev.trading.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ev.trading.dao.CommodityDao;
import com.ev.trading.entity.Commodity;
import com.ev.trading.entity.Image;
import com.ev.trading.service.CollectService;
import com.ev.trading.service.CommodityService;
import com.ev.trading.service.ImageService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author EV
 * @date 2021/3/31 18:57
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityDao, Commodity> implements CommodityService {

    @Autowired
    CommodityService commodityService;

    @Autowired
    CommodityDao commodityDao;

    @Autowired
    ImageService imageService;

    @Autowired
    CollectService collectService;

    @Override
    public List<Commodity> listByView() {
        List<Commodity> allList = commodityDao.findAllCommodityByView();
        if (allList.size() > 8) {
            return allList.subList(0, 8);
        }
        return allList;

    }

    @Override
    public List<Commodity> listByTime() {
        List<Commodity> allList = commodityDao.findAllCommodityByTime();
        if (allList.size() > 8) {
            return allList.subList(0, 8);
        }
        return allList;
    }

    @Override
    public List<Image> getImages(String imgIds, Integer commodityId) {
        List<Image> images = new ArrayList<>();
        List<Integer> ids = convertToList(imgIds);
        for (Integer id : ids) {
            Image image = imageService.getById(id);
            image.setCommodityId(commodityId);
            imageService.updateById(image);  //绑定商品id
            images.add(image);
        }
        return images;
    }

    @Override
    public void updateFirstPicture(Integer id, String firstPicture) {
        commodityDao.updateFirstPicture(id, firstPicture);
    }

    @Override
    public List<Commodity> listAll() {
        List<Commodity> allList = commodityDao.findAll();
        return allList;
    }

    @Override
    public Commodity findNew(Integer id) {
        List<Commodity> idList = commodityDao.findAllByUserId(id);
        Commodity commodity = new Commodity();
        for (int i = 0; i < idList.size(); i++) {
            if (i == 0) {
                commodity = idList.get(i);
            }
        }
        return commodity;
    }

    @Override
    public List<Commodity> findAllByUserId013(Integer id) {
        return commodityDao.findAllByUserId013(id);
    }

    @Override
    public Commodity findById(Integer id) {
        return commodityDao.findById(id);
    }

    @Override
    public List<Commodity> findAllByUserIdCollect(List<Integer> collectCommodityIds,int pageNum) {
        PageHelper.startPage(pageNum,4);
        List<Commodity> commodityList = commodityDao.findByIds(collectCommodityIds);
        return commodityList;
    }

    @Override
    public List<Commodity> findAllByCategoryIdAndViews(Integer id) {
        return commodityDao.findAllByCategoryIdAndViews(id);
    }

    @Override
    public List<Commodity> findAllByCategoryIdAndUpdateTime(Integer id) {
        return commodityDao.findAllByCategoryIdAndUpdateTime(id);
    }

    @Override
    public List<Commodity> findAllByCategoryIdAndPriceAsc(Integer id) {
        return commodityDao.findAllByCategoryIdAndPriceAsc(id);
    }

    @Override
    public List<Commodity> findAllByCategoryIdAndPriceDesc(Integer id) {
        return commodityDao.findAllByCategoryIdAndPriceDesc(id);
    }

    @Override
    public List<Commodity> findAllByViews() {
        return commodityDao.findAllCommodityByView();
    }

    @Override
    public List<Commodity> findAllTime() {
        return commodityDao.findAllCommodityByTime();
    }

    @Override
    public List<Commodity> search(String query) {
        return commodityDao.search(query);
    }

    @Override
    public List<Commodity> searchByView(String query) {
        return commodityDao.searchByView(query);
    }

    @Override
    public List<Commodity> searchByTime(String query) {
        return commodityDao.searchByTime(query);
    }

    @Override
    public List<Commodity> searchByPriceAsc(String query) {
        return commodityDao.searchByPriceAsc(query);
    }

    @Override
    public List<Commodity> searchByPriceDesc(String query) {
        return commodityDao.searchByPriceDesc(query);
    }

    @Override
    public void updateStatusTo2(Integer id, Date date) {
        commodityDao.updateStatusTo2(id,date);
    }

    @Override
    public List<Commodity> findByUserId12(Integer id,int pageNum) {
        PageHelper.startPage(pageNum, 4);
        List<Commodity> commodityList = commodityDao.findAllByUserId12(id);
        return commodityList;
    }

    @Override
    public void updateView(Integer id, Integer view2) {
        commodityDao.updateView(id,view2);
    }

    @Override
    public List<Commodity> findAllCommodityByTime013() {
        return commodityDao.findAllCommodityByTime013();
    }

    @Override
    public void updateStatusTo1(Integer id) {
        commodityDao.updateStatusTo1(id);
    }

    @Override
    public void updateStatusTo3(Integer id) {
        commodityDao.updateStatusTo3(id);
    }

    @Override
    public List<Commodity> findCommodityBySearch(Commodity commodity) {
        return commodityDao.findCommodityBySearch(commodity);
    }

    private List<Integer> convertToList(String imgIds) {  //把imagesIds字符串转换为list集合
        List<Integer> list = new ArrayList<>();
        if (!"".equals(imgIds) && imgIds != null) {
            String[] ids = imgIds.split(",");
            for (String id : ids) {
                list.add(new Integer(id));
            }
        }
        return list;
    }

}
