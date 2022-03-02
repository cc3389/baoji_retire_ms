package com.wit.baojims.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Classname VisitVo
 * @Description TODO
 * @Author Shawn Yue
 * @Date 14:10
 * @Version 1.0
 **/
@Data
public class VisitVo {
    private Integer visId;
    private LocalDateTime visDate;
    private String visDesc;
    private String comName;
}
