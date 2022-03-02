package com.wit.baojims.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Classname ActivityForm
 * @Description TODO
 * @Author Shawn Yue
 * @Date 15:02
 * @Version 1.0
 **/
@Data
public class ActivityForm {
    @NotEmpty(message = "活动名不能为空")
    private String name;
    @NotEmpty(message = "活动描述不能为空")
    private String description;
    @NotEmpty(message = "所在社区名不能为空")
    private String comName;
    @NotNull(message = "活动时间不能为空")
    private LocalDateTime date;
}
