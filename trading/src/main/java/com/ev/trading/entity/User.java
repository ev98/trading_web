package com.ev.trading.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author EV
 * @date 2021/3/30 22:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @TableId
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String avatar;
    private String phone;
    private String email;
    private BigDecimal account;
    private Date createTime;
    private Integer role;

}
