package com.wit.baojims.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.time.Period;

/**
 * @Classname MemberPageVo
 * @Description TODO
 * @Author Shawn Yue
 * @Date 14:30
 * @Version 1.0
 **/
@Data
public class MemberPageVo {
    private Integer peoId;
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDateTime birth;
    private String gender;
}
