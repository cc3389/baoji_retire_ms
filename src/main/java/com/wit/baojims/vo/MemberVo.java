package com.wit.baojims.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Classname ReturnMember
 * @Description TODO
 * @Author Shawn Yue
 * @Date 11:52
 * @Version 1.0
 **/
@Data
public class MemberVo {
    private String name;
    private String phone;
    private String avatar;
    private Integer age;
    private String gender;
    private LocalDateTime birth;
    private String isDeath;
}
