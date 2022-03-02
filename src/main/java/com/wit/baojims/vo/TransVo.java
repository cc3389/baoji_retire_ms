package com.wit.baojims.vo;

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
    private Integer peoId;
    private String peoName;
    private Integer transId;
    private String status;
    private String outCom;
    private String inCom;
    private LocalDateTime inDate;
    private LocalDateTime outDate;
    private Integer outComId;
    private Integer inComId;
}
