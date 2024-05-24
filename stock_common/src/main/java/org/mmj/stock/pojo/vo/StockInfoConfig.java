package org.mmj.stock.pojo.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 定义股票相关的值对象封装
 */
@ConfigurationProperties(prefix = "stock")
@Data
public class StockInfoConfig {
    /**
     * 封装国内A股大盘编码集合
     */
    private List<String> inner;

    /**
     * 外盘编码集合
     */
    private List<String> outer;

    /**
     * 股票区间
     */
    private List<String> upDownRange;

}
