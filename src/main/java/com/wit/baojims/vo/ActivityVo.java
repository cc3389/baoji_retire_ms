package com.wit.baojims.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Integer id;
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDateTime date;
    private String description;
    private Integer comId;
    private String comName;
}
