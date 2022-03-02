package com.wit.baojims.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 闄堝畤鏂�
 * @date 2022/03/02 15:27
 **/
@Data
public class MemberOneVo {
    private String name;
    private String phone;
    private String avatar;
    private Integer age;
    private String gender;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDateTime birth;
    private String isDeath;
    private String insName;
}
