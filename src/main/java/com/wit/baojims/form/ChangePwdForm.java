package com.wit.baojims.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Classname ChangePwdForm
 * @Description TODO
 * @Author Shawn Yue
 * @Date 8:33
 * @Version 1.0
 **/
@Data
public class ChangePwdForm {
    @NotEmpty(message = "旧密码不能为空")
    private String oldPwd;
    @NotEmpty(message = "新密码不能为空")
    private String newPwd;
}
