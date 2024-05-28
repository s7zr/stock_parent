package org.mmj.stock.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.mmj.stock.service.StockTimerTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定义股票相关数据的定时任务
 * @author mmj
 */
@Component
public class StockJob {
    @Autowired
    StockTimerTaskService stockTimerTaskService;
    @XxlJob("hema_job_test")
    public void jobTest(){
        System.out.println("jobTest run.....");
    }
    /**
     * 定时采集A股大盘数据
     * 针对不同的股票数据定义不同的采集任务，解耦合
     */
    @XxlJob("getInnerMarketInfo")
    public void getStockInfos(){
        stockTimerTaskService.getInnerMarketInfo();
    }
//    /**
//     * 板块定时任务
//     */
//    @XxlJob("getStockBlockInfoTask")
//    public void getStockBlockInfoTask(){
//        stockTimerTaskService.getStockSectorRtIndex();
//    }
}