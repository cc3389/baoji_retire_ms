package com.wit.baojims.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Classname TransVo
 * @Description TODO
 * @Author Shawn Yue
 * @Date 16:18
 * @Version 1.0
 **/
@Data
public class TransVo {
    private Integer id;
    private String name;
    private Integer transId;
    private String status;
    private String outCom;
    private String inCom;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDateTime inDate;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDateTime outDate;
    private Integer outComId;
    private Integer inComId;
}
