package com.ev.trading.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author EV
 * @date 2021/3/30 22:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collect {

    @TableId
    private Integer id;
    private Integer collectUserId;
    private Integer collectCommodityId;
    private Date collectTime;

    private User user;
    private Commodity commodity;
}
