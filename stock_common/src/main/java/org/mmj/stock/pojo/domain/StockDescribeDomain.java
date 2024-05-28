package org.mmj.stock.pojo.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author mmj
 * @Description
 * @create 2024-05-28 21:03
 */
@ApiModel(description = "")
@Data
public class StockDescribeDomain {


    /**
     *  股票编码
     */
    @ApiModelProperty(value = "股票编码", position = 1)
    private String code;

    /**
     * 行业，也就是行业板块名称
     */
    @ApiModelProperty(value = "行业，也就是行业板块名称", position = 2)
    private String trade;

    /**
     * 公司主营业务
     */
    @ApiModelProperty(value = "公司主营业务", position = 3)
    private String business;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称", position = 4)
    private String name;
}
