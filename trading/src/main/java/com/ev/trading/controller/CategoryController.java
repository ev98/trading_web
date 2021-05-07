package com.ev.trading.controller;

import com.ev.trading.entity.Category;
import com.ev.trading.entity.Commodity;
import com.ev.trading.service.CategoryService;
import com.ev.trading.service.CommodityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author EV
 * @date 2021/4/14 17:04
 */
@Controller
public class CategoryController {

    @Autowired
    CommodityService commodityService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/toCategory/{id}")
    public String toCategory(@PathVariable Integer id, @RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model) {
        PageHelper.startPage(pageNum, 12);
        List<Commodity> commodityList = commodityService.findAllByCategoryIdAndViews(id);
        Category category = categoryService.getById(id);
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        model.addAttribute("commodityList", commodityList);
        model.addAttribute("category", category);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("rank","toCategory");  //排序分页
        model.addAttribute("sort","排序");
        return "category";
    }

    @RequestMapping("/toCategoryByView/{id}")
    public String toCategoryByView(@PathVariable Integer id, @RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model) {
        PageHelper.startPage(pageNum, 12);
        List<Commodity> commodityList = commodityService.findAllByCategoryIdAndViews(id);
        Category category = categoryService.getById(id);
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        model.addAttribute("commodityList", commodityList);
        model.addAttribute("category", category);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("rank","toCategoryByView");  //排序分页
        model.addAttribute("sort","热度最高");
        return "category";
    }

    @RequestMapping("/toCategoryByTime/{id}")
    public String toCategoryByTime(@PathVariable Integer id, @RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model) {
        PageHelper.startPage(pageNum, 12);
        List<Commodity> commodityList = commodityService.findAllByCategoryIdAndUpdateTime(id);
        Category category = categoryService.getById(id);
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        model.addAttribute("commodityList", commodityList);
        model.addAttribute("category", category);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("rank","toCategoryByTime");
        model.addAttribute("sort","最新发布");
        return "category";
    }

    @RequestMapping("/toCategoryByPriceAsc/{id}")
    public String toCategoryByPriceAsc(@PathVariable Integer id, @RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model) {
        PageHelper.startPage(pageNum, 12);
        List<Commodity> commodityList = commodityService.findAllByCategoryIdAndPriceAsc(id);
        Category category = categoryService.getById(id);
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        model.addAttribute("commodityList", commodityList);
        model.addAttribute("category", category);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("rank","toCategoryByPriceAsc");
        model.addAttribute("sort","价格升序");  //从小到大
        return "category";
    }

    @RequestMapping("/toCategoryByPriceDesc/{id}")
    public String toCategoryByPriceDesc(@PathVariable Integer id, @RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model) {
        PageHelper.startPage(pageNum, 12);
        List<Commodity> commodityList = commodityService.findAllByCategoryIdAndPriceDesc(id);
        Category category = categoryService.getById(id);
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        model.addAttribute("commodityList", commodityList);
        model.addAttribute("category", category);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("rank","toCategoryByPriceDesc");
        model.addAttribute("sort","价格降序");  //从大到小
        return "category";
    }

}
