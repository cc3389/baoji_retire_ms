package com.wit.baojims.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Integer id;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDateTime Date;
    private String desc;
    private String comName;
    private Integer comId;
}
