package com.wit.baojims.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Classname ActivityVo
 * @Description TODO
 * @Author Shawn Yue
 * @Date 14:38
 * @Version 1.0
 **/
@Data
public class ActivityVo {
    private Integer actId;
    private String name;
    private LocalDateTime date;
    private String description;
    private Integer comId;
    private String comName;
}
