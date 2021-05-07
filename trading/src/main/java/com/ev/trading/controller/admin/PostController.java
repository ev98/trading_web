package com.ev.trading.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ev.trading.entity.Category;
import com.ev.trading.entity.Comment;
import com.ev.trading.entity.Commodity;
import com.ev.trading.entity.Post;
import com.ev.trading.service.CommentService;
import com.ev.trading.service.PostService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author EV
 * @date 2021/4/25 13:02
 */
@Controller
@RequestMapping("/admin")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @GetMapping("/toPost")
    public String toPost(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model) {
        PageHelper.startPage(pageNum, 10);
        List<Post> postList = postService.findAll();
        PageInfo<Post> pageInfo = new PageInfo<>(postList);
        model.addAttribute("postList", postList);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/post";
    }

    @RequestMapping("/deletePost/{id}")
    public String toDeletePost(@PathVariable Integer id) {
        //删除相关评论
        QueryWrapper<Comment> wrapper = new QueryWrapper<Comment>().eq("post_id", id);
        commentService.remove(wrapper);
        //删除求购
        postService.removeById(id);

        return "redirect:/admin/toPost";
    }

    @PostMapping("/searchPost")
    public String searchPost(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model, @RequestParam String content) {
        PageHelper.startPage(pageNum, 10);
        List<Post> postList = postService.findBySearch(content);
        PageInfo<Post> pageInfo = new PageInfo<>(postList);
        model.addAttribute("postList", postList);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/post";
    }

}
