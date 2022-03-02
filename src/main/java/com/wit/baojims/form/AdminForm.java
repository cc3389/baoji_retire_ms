package com.wit.baojims.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

/**
 * @author: zz
 * createTime: 2022/2/26 15:22
 * description:
 */

@Data
@Component
public class AdminForm {
    @NotBlank(message = "id不能为空")
    private Integer id;
    @NotBlank(message = "名字不能为空")
    private String name;
    @NotBlank(message = "区域类型不能为空")
    private String type;
    @NotBlank(message = "区域名不能为空")
    private String areaName;
}
