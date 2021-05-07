package com.ev.trading.controller;

import com.ev.trading.entity.Category;
import com.ev.trading.entity.Commodity;
import com.ev.trading.entity.Image;
import com.ev.trading.service.CategoryService;
import com.ev.trading.service.CommodityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author EV
 * @date 2021/3/31 22:15
 */

@Controller
public class IndexController {

    @Autowired
    CommodityService commodityService;

    @Autowired
    CategoryService categoryService;

    @GetMapping({"/", "/index"})
    public String index(Model model, HttpSession session) {
        List<Commodity> commoditiesByView = commodityService.listByView();  //热门商品
        List<Commodity> commoditiesByTime = commodityService.listByTime();  //最新商品
        List<Category> categoryList = categoryService.list();  //商品分类
        model.addAttribute("commoditiesByView", commoditiesByView);
        model.addAttribute("commoditiesByTime", commoditiesByTime);
        model.addAttribute("categoryList", categoryList);
        session.setAttribute("categoryList", categoryList);
        return "index";
    }

    @RequestMapping("/search")
    public String search(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum,
                         @RequestParam String query, Model model,HttpSession session) {
        PageHelper.startPage(pageNum, 12);
        List<Commodity> commodityList = commodityService.search(query);
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        model.addAttribute("commodityList", commodityList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("rank", "searchCommodity");  //排序分页
        model.addAttribute("sort", "排序");
        session.setAttribute("query",query);
        return "search";
    }

    @RequestMapping("/searchCommodity")
    public String searchCommodity(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum,
                         Model model,HttpSession session) {
        PageHelper.startPage(pageNum, 12);
        String query = (String) session.getAttribute("query");
        List<Commodity> commodityList = commodityService.search(query);
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        model.addAttribute("commodityList", commodityList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("rank", "searchCommodity");  //排序分页
        model.addAttribute("sort", "排序");
        session.setAttribute("query",query);
        return "search";
    }

    @RequestMapping("/searchByView")
    public String searchByView(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum,
                               Model model,HttpSession session) {
        PageHelper.startPage(pageNum, 12);
        String query = (String) session.getAttribute("query");
        List<Commodity> commodityList = commodityService.searchByView(query);
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        model.addAttribute("commodityList", commodityList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("rank", "searchByView");  //排序分页
        model.addAttribute("sort", "热度最高");
        session.setAttribute("query",query);
        return "search";
    }

    @RequestMapping("/searchByTime")
    public String searchByTime(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum,
                               Model model,HttpSession session) {
        PageHelper.startPage(pageNum, 12);
        String query = (String) session.getAttribute("query");
        List<Commodity> commodityList = commodityService.searchByTime(query);
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        model.addAttribute("commodityList", commodityList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("rank", "searchByTime");  //排序分页
        model.addAttribute("sort", "最新发布");
        session.setAttribute("query",query);
        return "search";
    }

    @RequestMapping("/searchByPriceAsc")
    public String searchByPriceAsc(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum,
                                   Model model,HttpSession session) {
        PageHelper.startPage(pageNum, 12);
        String query = (String) session.getAttribute("query");
        List<Commodity> commodityList = commodityService.searchByPriceAsc(query);
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        model.addAttribute("commodityList", commodityList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("rank", "searchByPriceAsc");  //排序分页
        model.addAttribute("sort", "价格升序");
        session.setAttribute("query",query);
        return "search";
    }

    @RequestMapping("/searchByPriceDesc")
    public String searchByPriceDesc(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum,
                                    Model model,HttpSession session) {
        PageHelper.startPage(pageNum, 12);
        String query = (String) session.getAttribute("query");
        List<Commodity> commodityList = commodityService.searchByPriceDesc(query);
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        model.addAttribute("commodityList", commodityList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("rank", "searchByPriceDesc");  //排序分页
        model.addAttribute("sort", "价格降序");
        session.setAttribute("query",query);
        return "search";
    }

    @RequestMapping("/toPopularCommodity")
    public String toPopularCommodity(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model) {
        PageHelper.startPage(pageNum, 12);
        List<Commodity> commodityList = commodityService.findAllByViews();
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        model.addAttribute("commodityList", commodityList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("rank", "toPopularCommodity");  //排序分页
        model.addAttribute("title", "热门商品");
        return "commodityAll";
    }

    @RequestMapping("/toNewCommodity")
    public String toNewCommodity(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model) {
        PageHelper.startPage(pageNum, 12);
        List<Commodity> commodityList = commodityService.findAllTime();
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        model.addAttribute("commodityList", commodityList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("rank", "toNewCommodity");  //排序分页
        model.addAttribute("title", "最新发布");
        return "commodityAll";
    }

}
