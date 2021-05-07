package com.ev.trading.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ev.trading.entity.*;
import com.ev.trading.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author EV
 * @date 2021/4/13 22:05
 */
@Controller
public class DetailController {

    @Autowired
    CommodityService commodityService;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @Autowired
    CollectService collectService;

    @Autowired
    OrdersService orderService;

    @RequestMapping("/commodityDetail/{id}")
    public String commodityDetail(@PathVariable Integer id, Model model, HttpSession session) {
        Commodity commodity = commodityService.findById(id);
        Integer view1 = commodity.getView();
        Integer view2 = view1 + 1;
        commodityService.updateView(commodity.getId(),view2);
        QueryWrapper<Image> wrapper = new QueryWrapper<Image>().eq("commodity_id", commodity.getId());
        List<Image> images = imageService.list(wrapper);
        model.addAttribute("commodity", commodity);
        model.addAttribute("images", images);

        //判断收藏
        User userInfo = (User) session.getAttribute("user");
        if (userInfo != null) {
            Integer userId = userInfo.getId();
            Collect collect = collectService.getOneById(userId, id);
            if (collect != null) {
                model.addAttribute("collect", 1);   //有收藏
            } else {
                model.addAttribute("collect", 0);   //没有收藏
            }
        } else {
            model.addAttribute("collect", 0);   //未登录
        }
        return "detail";
    }

    @RequestMapping("/addCollect/{id}")
    public String addCollect(@PathVariable Integer id, HttpSession session, RedirectAttributes attributes) {
        User userInfo = (User) session.getAttribute("user");
        //判断是否登录
        if (userInfo == null) {
            attributes.addFlashAttribute("message", "权限不足，请先登录");
            return "redirect:/toLogin";
        } else {
            Integer userId = userInfo.getId();
            Collect collect = new Collect();
            collect.setCollectUserId(userId);
            collect.setCollectCommodityId(id);
            collect.setCollectTime(new Date());
            collectService.save(collect);
            return "redirect:/commodityDetail/" + id;
        }
    }

    @RequestMapping("/cancelCollect/{id}")
    public String cancelCollect(@PathVariable Integer id, HttpSession session) {
        User userInfo = (User) session.getAttribute("user");
        Integer userId = userInfo.getId();
        QueryWrapper<Collect> wrapper = new QueryWrapper<Collect>().eq("collect_user_id", userId).eq("collect_commodity_id", id);
        collectService.remove(wrapper);
        return "redirect:/commodityDetail/" + id;
    }

    @RequestMapping("/want")
    public String want(RedirectAttributes attributes) {
        attributes.addFlashAttribute("message", "权限不足，请先登录");
        return "redirect:/toLogin";
    }

    @RequestMapping("/toBuy/{id}")
    public String toBuy(@PathVariable Integer id, Model model) {
        Commodity commodity = commodityService.findById(id);
        model.addAttribute("commodity", commodity);
        return "buy";
    }

    @PostMapping("/buyCommodity/{id}")
    public String buyCommodity(@PathVariable Integer id, HttpSession session, Model model) {
        User userInfo = (User) session.getAttribute("user");
        Integer boughtId = userInfo.getId();
        User boughtUser = userService.getById(boughtId);
        Commodity commodity = commodityService.findById(id);
        User sellUser = userService.getById(commodity.getUserId());
        //如果用户余额大于商品价格
        if (boughtUser.getAccount().compareTo(commodity.getPrice()) == 1 || boughtUser.getAccount().compareTo(commodity.getPrice()) == 0) {
            //买家扣钱，卖家加钱
            boughtUser.setAccount(boughtUser.getAccount().subtract(commodity.getPrice()));
            sellUser.setAccount(sellUser.getAccount().add(commodity.getPrice()));
            userService.updateById(boughtUser);
            userService.updateById(sellUser);
            //改变商品状态
            commodityService.updateStatusTo2(id, new Date());
            //添加order
            Orders order = new Orders();
            order.setBoughtId(boughtId);
            order.setSellId(sellUser.getId());
            order.setCommodityId(id);
            order.setPrice(commodity.getPrice());
            order.setCreateTime(new Date());
            orderService.save(order);
            //购买成功
            model.addAttribute("message", "购买成功!");
            model.addAttribute("commodity", commodity);
            return "buy";
        } else {
            model.addAttribute("message", "您的账户余额不足，请前往充值");
            model.addAttribute("commodity", commodity);
            return "buy";
        }
    }

    //用户详情
    @GetMapping("/toUserDetail/{id}")
    public String toUserDetail(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, @PathVariable Integer id, Model model) {
        User user = userService.getById(id);
        List<Commodity> commodityList = commodityService.findByUserId12(id, pageNum);
        System.out.println("--------------------");
        if (commodityList.size() > 0) {
            PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
            model.addAttribute("active","1");
//          //将手机号中间4位隐去
            String phone = user.getPhone();
            String phone2 = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1*****$2");
            user.setPhone(phone2);
            model.addAttribute("user", user);
            model.addAttribute("commodityList", commodityList);
            model.addAttribute("pageInfo", pageInfo);
        } else {
            PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
            model.addAttribute("active","1");
            model.addAttribute("none", 1);
            //将手机号中间4位隐去
            String phone = user.getPhone();
            String phone2 = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1*****$2");
            user.setPhone(phone2);
            model.addAttribute("user", user);
            model.addAttribute("commodityList", commodityList);
            model.addAttribute("pageInfo", pageInfo);
        }
        return "userDetail";
    }
}
