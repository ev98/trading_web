package com.ev.trading.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author EV
 * @date 2021/3/30 22:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @TableId
    private Integer id;
    private String content;
    private Date createTime;
    private Integer postId;
    private Integer userId;

    private User user;

}
