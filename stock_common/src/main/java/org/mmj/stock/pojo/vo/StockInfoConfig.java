package org.mmj.stock.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 定义股票相关的值对象封装
 */
@ApiModel(description = "定义股票相关的值对象封装")
@ConfigurationProperties(prefix = "stock")
@Data
public class StockInfoConfig {
    /**
     * 封装国内A股大盘编码集合
     */
    @ApiModelProperty(value = "封装国内A股大盘编码集合", position = 1)
    private List<String> inner;

    /**
     * 外盘编码集合
     */
    @ApiModelProperty(value = "外盘编码集合", position = 2)
    private List<String> outer;

    /**
     * 股票区间
     */
    @ApiModelProperty(value = "股票区间", position = 3)
    private List<String> upDownRange;

    /**
     * 大盘参数获取url
     */
    @ApiModelProperty(value = "大盘参数获取url", position = 4)
    private String marketUrl;
    /**
     * 板块参数获取url
     */
    @ApiModelProperty(value = "板块参数获取url", position = 5)
    private String blockUrl;

}
