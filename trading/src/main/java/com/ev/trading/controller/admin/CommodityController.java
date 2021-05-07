package com.ev.trading.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ev.trading.entity.Category;
import com.ev.trading.entity.Collect;
import com.ev.trading.entity.Commodity;
import com.ev.trading.entity.Image;
import com.ev.trading.service.CategoryService;
import com.ev.trading.service.CollectService;
import com.ev.trading.service.CommodityService;
import com.ev.trading.service.ImageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author EV
 * @date 2021/4/25 13:01
 */
@Controller
@RequestMapping("/admin")
public class CommodityController {

    @Autowired
    CommodityService commodityService;

    @Autowired
    ImageService imageService;

    @Autowired
    CollectService collectService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/toCommodity")
    public String toCommodity(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model) {
        PageHelper.startPage(pageNum, 10);
        List<Commodity> commodityList = commodityService.findAllCommodityByTime013();
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        List<Category> categoryList = categoryService.list();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("commodityList", commodityList);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/commodity";
    }

    @GetMapping("/toStatus1/{id}")
    public String toStatus1(@PathVariable Integer id) {
        commodityService.updateStatusTo1(id);
        return "redirect:/admin/toCommodity";
    }

    @GetMapping("/toStatus3/{id}")
    public String toStatus3(@PathVariable Integer id) {
        commodityService.updateStatusTo3(id);
        return "redirect:/admin/toCommodity";
    }

    @GetMapping("/deleteCommodity/{id}")
    public String deleteCommodity(@PathVariable Integer id) {
        //删除商品图片
        QueryWrapper<Image> image = new QueryWrapper<Image>().eq("commodity_id", id);
        imageService.remove(image);
        //删除相关收藏
        QueryWrapper<Collect> collect = new QueryWrapper<Collect>().eq("collect_commodity_id", id);
        collectService.remove(collect);
        //删除商品
        commodityService.removeById(id);

        return "redirect:/admin/toCommodity";
    }

    @PostMapping("/search")
    public String search(Commodity commodity,@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model){
        PageHelper.startPage(pageNum, 10);
        List<Commodity> commodityList = commodityService.findCommodityBySearch(commodity);
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        List<Category> categoryList = categoryService.list();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("commodityList", commodityList);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/commodity";
    }


}
