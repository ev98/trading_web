package com.ev.trading.controller;

import com.ev.trading.entity.Comment;
import com.ev.trading.entity.Commodity;
import com.ev.trading.entity.Post;
import com.ev.trading.entity.User;
import com.ev.trading.service.CommentService;
import com.ev.trading.service.PostService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author EV
 * @date 2021/4/21 17:18
 */
@Controller
public class PurchaseController {

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @GetMapping("/toPurchase")
    public String toPurchase(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model, HttpSession session) {
        PageHelper.startPage(pageNum, 10);
        List<Post> postList = postService.findAll();
        PageInfo<Post> pageInfo = new PageInfo<>(postList);
        model.addAttribute("postList", postList);
        model.addAttribute("pageInfo", pageInfo);
        return "purchase";
    }

    @GetMapping("/toReleasePost")
    public String toReleasePost(HttpSession session, RedirectAttributes attributes) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return "releasePost";
        } else {
            attributes.addFlashAttribute("message", "权限不足，请先登录");
            return "redirect:/toLogin";
        }
    }

    @PostMapping("/releasePost")
    public String releasePost(Post post, HttpSession session) {
        User user = (User) session.getAttribute("user");
        post.setCreateTime(new Date());
        post.setUpdateTime(new Date());
        post.setUserId(user.getId());
        postService.save(post);
        return "redirect:/toPurchase";
    }

    @PostMapping("/updatePost")
    public String updatePost(Post post) {
        post.setUpdateTime(new Date());
        postService.updateById(post);
        return "redirect:/toPurchase";
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable Integer id, Model model) {
        //查询post
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        //查询评论
        List<Comment> commentList = commentService.findByPostId(id);
        if (commentList.size() < 1) {
            model.addAttribute("status", 1);
        }
        model.addAttribute("commentList", commentList);
        return "post";
    }

    @PostMapping("/releaseComment")
    public String releaseComment(Comment comment, HttpSession session, RedirectAttributes attributes) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setCreateTime(new Date());
            comment.setUserId(user.getId());
            commentService.save(comment);
            return "redirect:/post/" + comment.getPostId();
        } else {
            attributes.addFlashAttribute("message", "权限不足，请先登录");
            return "redirect:/toLogin";
        }
    }

}
