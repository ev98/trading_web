package com.ev.trading.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author EV
 * @date 2021/3/30 22:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @TableId
    private Integer id;
    private String caName;

}
