package com.wit.baojims.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Classname MemberForm
 * @Description TODO
 * @Author Shawn Yue
 * @Date 16:55
 * @Version 1.0
 **/
@Data
@Component
public class MemberForm {
    @NotBlank(message = "人员名字不能为空")
    private String name;
    @NotBlank(message = "手机号不能为空")
    private String phone;
    @NotBlank(message = "性别不能为空")
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "生日不能为空")
    private LocalDate birth;
    @NotBlank(message = "机构名称不能为空")
    private String insName;

}
