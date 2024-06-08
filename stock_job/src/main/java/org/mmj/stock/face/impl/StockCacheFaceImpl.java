package org.mmj.stock.face.impl;

import org.mmj.stock.face.StockCacheFace;
import org.mmj.stock.mapper.StockBusinessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mmj
 * @Description 定义股票缓存层的实现
 * @create 2024-06-08 15:01
 */
@Component("stockCacheFace")
public class StockCacheFaceImpl implements StockCacheFace {
    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    @Cacheable(cacheNames = "stock" ,key = "'stockCodes'")
    @Override
    public List<String> getAllStockCodeWithPrefix() {
        //1.获取所有个股的集合 3000+
        List<String> allCodes = stockBusinessMapper.getStockCodes();
        //添加大盘业务前缀 sh sz
        allCodes=allCodes.stream().map(code->code.startsWith("6")?"sh"+code:"sz"+code).collect(Collectors.toList());
        return allCodes;
    }
}
