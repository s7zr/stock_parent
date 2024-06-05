package org.mmj.stock.pojo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mmj
 * @Description
 * @create 2024-06-05 22:01
 */
@Data
public class StockRtLimit10 {
    /**
     * 日期，eg:202201280809
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "Asia/Shanghai")
    private Date date;
    /**
     * 交易量
     */
    private Long tradeAmt;
    /**
     * 当前交易总金额
     */
    private BigDecimal tradeVol;
    /**
     * 交易价格
     */
    private BigDecimal tradePrice;
}
