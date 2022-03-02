package com.wit.baojims.vo;

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
    private LocalDateTime birth;
    private String isDeath;
}
