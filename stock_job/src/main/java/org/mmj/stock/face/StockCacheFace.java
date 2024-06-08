package org.mmj.stock.face;

import java.util.List;

/**
 * @author mmj
 * @Description
 * @create 2024-06-08 15:01
 */
public interface StockCacheFace {
    /**
     * 获取所有股票编码，并添加上证或者深证的股票前缀编号：sh sz
     * @return
     */
    List<String> getAllStockCodeWithPrefix();
}
