package org.mmj.stock;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.mmj.stock.mapper.StockBusinessMapper;
import org.mmj.stock.service.StockTimerTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class TestStockRtIndex {

    @Autowired
    StockBusinessMapper stockBusinessMapper;
    /**
     * 获取A股数据
     */
    @Test
    public void test02(){
        List<String> allCodes = stockBusinessMapper.getStockCodes();
        System.out.println(allCodes);
        //添加大盘的业务前缀
        allCodes = allCodes.stream().map(code->code.startsWith("6")?"sh"+code:"sz"+code).collect(Collectors.toList());
        System.out.println(allCodes);
        //将所有个股编码组成的大的集合拆分成若干小的集合40--->15 15 10
        //使用guava工具类，对list做切分
        Lists.partition(allCodes, 15).forEach(codes->{
            System.out.println(codes);
        });
    }
}
