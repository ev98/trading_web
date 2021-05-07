package com.ev.trading.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author EV
 * @date 2021/3/30 22:30
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commodity {

    @TableId
    private Integer id;
    private String cName;
    private BigDecimal price;
    private BigDecimal startPrice;
    private String description;
    private Date createTime;
    private Date updateTime;
    private Integer view;
    private Integer status;
    private Integer userId;
    private Integer categoryId;
    private String imgIds;
    private String firstPicture;

    private User user;
    private Category category;

}
