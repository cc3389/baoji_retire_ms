package com.wit.baojims.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.awt.event.PaintEvent;

/**
 * @Classname VisitGroupVo
 * @Description TODO
 * @Author Shawn Yue
 * @Date 14:14
 * @Version 1.0
 **/
@Data
@TableName("visitGroupVo")
public class VisitGroupVo {
    private String comName;
    private Integer total;
}
