package com.wit.baojims.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Classname VisitForm
 * @Description TODO
 * @Author Shawn Yue
 * @Date 18:54
 * @Version 1.0
 **/
@Data
public class VisitForm {
    @NotEmpty(message = "社区名不能为空")
    private String comName;
    @NotEmpty(message = "走访记录不能为空")
    private String desc;
    @NotEmpty(message = "走访时间不能为空")
    private LocalDate date;
}
