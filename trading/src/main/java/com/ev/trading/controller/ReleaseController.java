package com.ev.trading.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ev.trading.entity.Commodity;
import com.ev.trading.entity.Image;
import com.ev.trading.entity.User;
import com.ev.trading.service.CategoryService;
import com.ev.trading.service.CommodityService;
import com.ev.trading.service.ImageService;
import com.ev.trading.service.UserService;
import com.ev.trading.util.AliyunOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author EV
 * @date 2021/4/6 18:52
 */
@Controller
public class ReleaseController {

    @Autowired
    UserService userService;

    @Autowired
    CommodityService commodityService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ImageService imageService;

    @Autowired
    private AliyunOssUtil aliyunOssUtil;

    @GetMapping("/toRelease")
    public String toRelease(HttpSession session, RedirectAttributes attributes) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return "release";
        } else {
            attributes.addFlashAttribute("message", "权限不足，请先登录");
            return "redirect:/toLogin";
        }
    }

    @PostMapping("/releaseCommodity")
    public String releaseCommodity(Commodity commodity, HttpSession session) {
        //添加商品
        commodity.setCreateTime(new Date());
        commodity.setUpdateTime(new Date());
        commodity.setView(0);
        User user = (User) session.getAttribute("user");
        commodity.setUserId(user.getId());

        List<Image> images = imageService.listNew(user.getId());
        commodity.setImgIds(imageService.getImgIds(images));
        commodityService.save(commodity);
        return "redirect:/relationImage";
    }

    @PostMapping("/updateCommodity")
    public String updateCommodity(Commodity commodity,HttpSession session) {
        User user = (User) session.getAttribute("user");
        //更新商品
        commodity.setUpdateTime(new Date());
        commodity.setStatus(0);
        List<Image> images = imageService.listUpdate(commodity.getId(),user.getId());
        commodity.setImgIds(imageService.getImgIds(images));

        commodityService.updateById(commodity);
        return "redirect:/relationImage";
    }

    @RequestMapping("/relationImage")
    public String relationImage(HttpSession session) {
        User user = (User) session.getAttribute("user");
        Commodity commodity = commodityService.findNew(user.getId());

        List<Image> images = commodityService.getImages(commodity.getImgIds(), commodity.getId());
        for (int i = 0; i < images.size(); i++) {
            if (i == 0) {
                commodity.setFirstPicture(images.get(i).getUrl()); //添加商品首图
            }
        }
        commodityService.updateFirstPicture(commodity.getId(), commodity.getFirstPicture());
        return "redirect:/toUserRelease";
    }

    @RequestMapping("/uploadImages")
    @ResponseBody
    public String uploadImages(@RequestParam("file") MultipartFile[] files, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        String imgUrl = "";
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                imgUrl = aliyunOssUtil.uploadImg(files[i]);
                Image image = new Image();
                image.setUrl(imgUrl);
                image.setUserId(user.getId());
                imageService.save(image);
            }
        }
        JSONObject res = new JSONObject();//生成一个json对象返回前端
        res.put("code", 0);
        res.put("msg", "");
        res.put("data", imgUrl);//存放图片路径
        return res.toJSONString();
    }


}
