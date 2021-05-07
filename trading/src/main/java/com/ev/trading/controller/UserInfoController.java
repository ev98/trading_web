package com.ev.trading.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ev.trading.entity.*;
import com.ev.trading.service.*;
import com.ev.trading.util.AliyunOssUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author EV
 * @date 2021/4/5 20:29
 */
@Controller
public class UserInfoController {

    @Autowired
    UserService userService;

    @Autowired
    CommodityService commodityService;

    @Autowired
    ImageService imageService;

    @Autowired
    CollectService collectService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    private AliyunOssUtil aliyunOssUtil;

    //个人信息
    @GetMapping("/toUserInfo")
    public String toUserInfo(HttpSession session) {

        User userInfo = (User) session.getAttribute("user");
        Integer id = userInfo.getId();

        User user = userService.getById(id);
        //更新
        session.setAttribute("user", user);

        return "userInfo";
    }

    @PostMapping("/updateUserInfo")
    public String updateUserInfo(User user, HttpSession session, RedirectAttributes attributes) {
        User userInfo = (User) session.getAttribute("user");
        Integer id = userInfo.getId();
        user.setId(id);
        userService.updateById(user);
        return "redirect:/toUserInfo";
    }

    @GetMapping("/findByName")
    @ResponseBody
    public void findName(String name, HttpSession session, HttpServletResponse response) throws IOException {
        User nowUser = (User) session.getAttribute("user");
        Integer id = nowUser.getId();
        response.setContentType("application/json;charset=utf-8");
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("name", name).ne("id", id);
        User userInfo = userService.getOne(wrapper);
        Map<String, Object> map = new HashMap<>();
        if (userInfo != null) {
            map.put("nameExit", true);
            map.put("msg", "用户名已存在");
        } else {
            map.put("nameExit", false);
            map.put("msg", "正确");
        }
        //将map转为json，并且传递给客户端
        String s = JSON.toJSONString(map);
        PrintWriter writer = response.getWriter();
        writer.write(s);
        writer.close();
    }

    @RequestMapping("/uploadAvatar")
    public String uploadAvatar(@RequestParam("file") MultipartFile file, HttpSession session) {
        String filename = file.getOriginalFilename();
        try {
            if (file != null) {
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream outputStream = new FileOutputStream(newFile);
                    outputStream.write(file.getBytes());
                    outputStream.close();
                    file.transferTo(newFile);
                    //返回图片的URL
                    String url = aliyunOssUtil.upLoad(newFile);
                    User userInfo = (User) session.getAttribute("user");
                    userInfo.setAvatar(url);
                    userService.saveOrUpdate(userInfo);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/toUserInfo";
    }

    //我的钱包
    @GetMapping("/toUserAccount")
    public String toUserAccount(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "userAccount";
    }

    @PostMapping("/recharge")
    public String recharge(@RequestParam BigDecimal account, HttpSession session) {
        User user = (User) session.getAttribute("user");
        userService.recharge(account, user.getId());
        //更新User
        User newUser = userService.getById(user.getId());
        session.setAttribute("user", newUser);
        return "redirect:/toUserAccount";
    }

    @PostMapping("/withdrawal")
    public String withdrawal(@RequestParam BigDecimal account, HttpSession session) {
        User user = (User) session.getAttribute("user");
        userService.withdrawal(account, user.getId());
        //更新User
        User newUser = userService.getById(user.getId());
        session.setAttribute("user", newUser);
        return "redirect:/toUserAccount";
    }

    //我的收藏
    @GetMapping("/toUserCollect")
    public String toUserCollect(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, HttpSession session, Model model) {
        User userInfo = (User) session.getAttribute("user");
        Integer id = userInfo.getId();
        //得到用户收藏的商品id
        List<Integer> collectCommodityIds = collectService.listByUserId(id);
        if (collectCommodityIds.size() > 0) {
            //根据商品id查找商品
            List<Commodity> commodityList = commodityService.findAllByUserIdCollect(collectCommodityIds, pageNum);
            PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
            model.addAttribute("commodityList", commodityList);
            model.addAttribute("pageInfo", pageInfo);
        } else {
            model.addAttribute("none", 1);
            PageInfo<Integer> pageInfo = new PageInfo<>(collectCommodityIds);
            model.addAttribute("pageInfo", pageInfo);
        }
        return "userCollect";
    }

    @RequestMapping("/deleteCollect/{id}")
    public String cancelCollect(@PathVariable Integer id, HttpSession session) {
        User userInfo = (User) session.getAttribute("user");
        Integer userId = userInfo.getId();
        QueryWrapper<Collect> wrapper = new QueryWrapper<Collect>().eq("collect_user_id", userId).eq("collect_commodity_id", id);
        collectService.remove(wrapper);
        return "redirect:/toUserCollect";
    }

    //我的求购
    @GetMapping("/toUserPost")
    public String toUserPost(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, HttpSession session, Model model){
        PageHelper.startPage(pageNum, 4);
        User userInfo = (User) session.getAttribute("user");
        Integer userId = userInfo.getId();
        List<Post> postList =  postService.findByUserId(userId);
        if (postList.size() < 1) {
            model.addAttribute("none", 1);
        }
        PageInfo<Post> pageInfo = new PageInfo<>(postList);
        model.addAttribute("postList", postList);
        model.addAttribute("pageInfo", pageInfo);
        return "userPost";
    }

    @RequestMapping("/toUpdatePost/{id}")
    public String toUpdatePost(@PathVariable Integer id, Model model) {
        Post post =  postService.findById(id);
        model.addAttribute("post", post);
        return "updatePost";
    }

    @RequestMapping("/toDeletePost/{id}")
    public String toDeletePost(@PathVariable Integer id) {
        //删除相关评论
        QueryWrapper<Comment> wrapper = new QueryWrapper<Comment>().eq("post_id", id);
        commentService.remove(wrapper);
        //删除求购
        postService.removeById(id);

        return "redirect:/toUserPost";
    }

    //我的发布
    @GetMapping("/toUserRelease")
    public String toUserRelease(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, HttpSession session, Model model) {
        PageHelper.startPage(pageNum, 4);

        User userInfo = (User) session.getAttribute("user");
        Integer id = userInfo.getId();
        List<Commodity> commodityList = commodityService.findAllByUserId013(id);
        if (commodityList.size() < 1) {
            model.addAttribute("none", 1);
        }
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
        model.addAttribute("commodityList", commodityList);
        model.addAttribute("pageInfo", pageInfo);
        return "userRelease";
    }

    @RequestMapping("/toUpdateRelease/{id}")
    public String toUpdateRelease(@PathVariable Integer id, Model model) {
        Commodity commodity = commodityService.findById(id);
        model.addAttribute("commodityInfo", commodity);
        return "updateRelease";
    }

    @RequestMapping("/toDeleteRelease/{id}")
    public String toDeleteRelease(@PathVariable Integer id) {
        //删除商品图片
        QueryWrapper<Image> image = new QueryWrapper<Image>().eq("commodity_id", id);
        imageService.remove(image);
        //删除相关收藏
        QueryWrapper<Collect> collect = new QueryWrapper<Collect>().eq("collect_commodity_id", id);
        collectService.remove(collect);
        //删除商品
        commodityService.removeById(id);

        return "redirect:/toUserRelease";
    }

    //我卖出的
    @GetMapping("/toUserSell")
    public String toUserSell(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, HttpSession session, Model model) {
        User userInfo = (User) session.getAttribute("user");
        Integer id = userInfo.getId();
        //得到用户出售的商品id
        QueryWrapper<Orders> wrapper = new QueryWrapper<Orders>().eq("sell_id", id).orderByDesc("create_time");
        List<Orders> ordersList = ordersService.list(wrapper);
        List<Integer> sellCommodityIds = ordersService.listById(ordersList);
        if (sellCommodityIds.size() > 0) {
            //根据商品id查找商品
            List<Commodity> commodityList = commodityService.findAllByUserIdCollect(sellCommodityIds, pageNum);
            PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
            model.addAttribute("commodityList", commodityList);
            model.addAttribute("pageInfo", pageInfo);
        } else {
            model.addAttribute("none", 1);
            PageInfo<Integer> pageInfo = new PageInfo<>(sellCommodityIds);
            model.addAttribute("pageInfo", pageInfo);
        }
        return "userSell";
    }

    //我买到的
    @GetMapping("/toUserBought")
    public String toUserBought(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, HttpSession session, Model model) {
        User userInfo = (User) session.getAttribute("user");
        Integer id = userInfo.getId();
        //得到用户出售的商品id
        QueryWrapper<Orders> wrapper = new QueryWrapper<Orders>().eq("bought_id", id).orderByDesc("create_time");
        List<Orders> ordersList = ordersService.list(wrapper);
        List<Integer> boughtCommodityIds = ordersService.listById(ordersList);
        if (boughtCommodityIds.size() > 0) {
            //根据商品id查找商品
            List<Commodity> commodityList = commodityService.findAllByUserIdCollect(boughtCommodityIds, pageNum);
            PageInfo<Commodity> pageInfo = new PageInfo<>(commodityList);
            model.addAttribute("commodityList", commodityList);
            model.addAttribute("pageInfo", pageInfo);
        } else {
            model.addAttribute("none", 1);
            PageInfo<Integer> pageInfo = new PageInfo<>(boughtCommodityIds);
            model.addAttribute("pageInfo", pageInfo);
        }
        return "userBought";
    }

}
