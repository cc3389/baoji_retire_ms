package com.wit.baojims.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class LoginForm {

    @NotEmpty(message = "用户名不能为空")
    private String name;
    @NotEmpty(message = "密码不能为空")
    private String password;
}
