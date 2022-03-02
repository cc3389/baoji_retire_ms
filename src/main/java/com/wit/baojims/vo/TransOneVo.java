package com.wit.baojims.vo;

import lombok.Data;

import java.awt.event.PaintEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Classname TransOneVo
 * @Description TODO
 * @Author Shawn Yue
 * @Date 13:15
 * @Version 1.0
 **/

@Data
public class TransOneVo {
    private Integer inComId;
    private Integer outComId;
    private LocalDate inDate;
    private LocalDate outDate;
}
