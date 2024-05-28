package org.mmj.stock.pojo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mmj
 * @Description 定义封装国外大盘数据的实体类
 * @create 2024-05-28 17:11
 */
@ApiModel(description = "国外大盘数据的实体类")
@Data
public class OuterMarketDomain {
    /**
     * 大盘名称
     */
    @ApiModelProperty(value = "大盘名称", position = 1)
    private String name;
    /**
     * 当前大盘点
     */
    @ApiModelProperty(value = "当前大盘点", position = 2)
    private BigDecimal curPoint;
    /**
     * 涨跌值
     */
    @ApiModelProperty(value = "涨跌值", position = 3)
    private BigDecimal upDown;
    /**
     * 涨幅
     */
    @ApiModelProperty(value = "涨幅", position = 4)
    private BigDecimal rose;
    /**
     * 当前时间
     */
    @ApiModelProperty(value = "当前时间", position = 5)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date curTime;
}
