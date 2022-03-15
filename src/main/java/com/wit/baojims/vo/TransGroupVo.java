package com.wit.baojims.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * @Classname TransGroupVo
 * @Description TODO
 * @Author Shawn Yue
 * @Date 11:12
 * @Version 1.0
 **/
@Data
@TableName("transGroupVo")
public class TransGroupVo {

    private Integer month;
    private Integer total;
}
