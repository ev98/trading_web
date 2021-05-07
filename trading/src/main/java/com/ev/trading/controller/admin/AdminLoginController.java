package com.ev.trading.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ev.trading.entity.User;
import com.ev.trading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author EV
 * @date 2021/4/25 13:01
 */
@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("username", username).eq("password", password);
        User user = userService.getOne(wrapper);
        if (user!=null){
            if (user.getRole()!=0){
                attributes.addFlashAttribute("message", "用户权限不足");
                return "redirect:/admin";
            }else {
                session.setAttribute("user", user);
                return "redirect:/admin/toCommodity";
            }
        }else {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
