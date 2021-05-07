package com.ev.trading.controller.admin;

import com.ev.trading.entity.Category;
import com.ev.trading.entity.Commodity;
import com.ev.trading.entity.User;
import com.ev.trading.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author EV
 * @date 2021/4/25 13:01
 */
@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/toUser")
    public String toUser(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model) {
        PageHelper.startPage(pageNum, 10);
        List<User> userList = userService.list();
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        model.addAttribute("userList", userList);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/user";
    }

    @GetMapping("/toUser0/{id}")
    public String toUser0(@PathVariable Integer id){
        User user = userService.getById(id);
        user.setRole(0);
        userService.updateById(user);
        return "redirect:/admin/toUser";
    }

    @GetMapping("/toUser1/{id}")
    public String toUser1(@PathVariable Integer id){
        User user = userService.getById(id);
        user.setRole(1);
        userService.updateById(user);
        return "redirect:/admin/toUser";
    }

}
