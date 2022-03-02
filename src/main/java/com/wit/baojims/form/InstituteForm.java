package com.wit.baojims.form;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

@Data
@Component
public class InstituteForm {

    private Integer id;
    @NotBlank(message = "社区名不能为空")
    private String comName;
    @NotBlank(message = "电话不能为空")
    private String phone;
    @NotBlank(message = "机构名不能为空")
    private String name;
    @NotBlank(message = "地址不能为空")
    private String address;
    @NotBlank(message = "伊妹儿不能为空")
    private String email;
    @NotBlank(message = "总人数不能为空")
    private Integer sumPeople;
}
