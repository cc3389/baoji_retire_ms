package com.wit.baojims.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @Classname MemberUpdateForm
 * @Description TODO
 * @Author Shawn Yue
 * @Date 12:06
 * @Version 1.0
 **/
@Data
public class MemberUpdateForm {
    @NotNull(message = "人员id不能为空")
    private Integer peoId;

    private String name;
    private String phone;
    private String avatar;
    private String gender;
    private LocalDate birth;
}
