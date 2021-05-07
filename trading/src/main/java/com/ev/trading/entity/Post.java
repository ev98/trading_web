package com.ev.trading.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author EV
 * @date 2021/4/21 16:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @TableId
    private Integer id;
    private String content;
    private Date createTime;
    private Date updateTime;
    private Integer userId;

    private User user;

}
