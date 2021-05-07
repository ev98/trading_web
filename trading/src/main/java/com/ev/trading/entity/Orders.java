package com.ev.trading.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author EV
 * @date 2021/3/30 23:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @TableId
    private Integer id;
    private Integer boughtId;
    private Integer sellId;
    private Integer commodityId;
    private BigDecimal price;
    private Date createTime;

}
