package org.mmj.stock.service.impl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.mmj.stock.constant.ParseType;
import org.mmj.stock.mapper.StockBusinessMapper;
import org.mmj.stock.mapper.StockMarketIndexInfoMapper;
import org.mmj.stock.pojo.entity.StockMarketIndexInfo;
import org.mmj.stock.pojo.entity.StockRtInfo;
import org.mmj.stock.pojo.vo.StockInfoConfig;
import org.mmj.stock.service.StockTimerTaskService;
import org.mmj.stock.utils.DateTimeUtil;
import org.mmj.stock.utils.IdWorker;
import org.mmj.stock.utils.ParserStockInfoUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;


import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service("stockTimerTaskService")
@Slf4j
public class StockTimerTaskServiceImpl implements StockTimerTaskService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    //注入格式解析bean
    @Autowired
    private ParserStockInfoUtil parserStockInfoUtil;
    @Autowired
    StockBusinessMapper stockBusinessMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    /**
     * 必须保证httpEntity无状态，也就是后续不能再修改
     */
    private HttpEntity<Object> httpEntity;
    @Override
    public void getInnerMarketInfo() {
        //1.定义采集的url接口
        String url=stockInfoConfig.getMarketUrl() + String.join(",",stockInfoConfig.getInner());
        //2.调用restTemplate采集数据
//        //2.1 组装请求头
//        HttpHeaders headers = new HttpHeaders();
//        //必须填写，否则数据采集不到
//        headers.add("Referer","https://finance.sina.com.cn/stock/");
//        headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
//        //2.2 组装请求对象
//        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        //2.3 resetTemplate发起请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(url,HttpMethod.GET,httpEntity,String.class);
        int statusCodeValue = responseEntity.getStatusCodeValue();
        if(statusCodeValue != 200){
            //当前请求失败
            log.error("当前时间点：{}，采集数据失败，http状态码：{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), statusCodeValue);
            return;
        }
        //获取返回的js数据
        String jsData = responseEntity.getBody();
        //3.数据解析（重要）
//        var hq_str_sh000001="上证指数,3267.8103,3283.4261,3236.6951,3290.2561,3236.4791,0,0,402626660,398081845473,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2022-04-07,15:01:09,00,";
//        var hq_str_sz399001="深证成指,12101.371,12172.911,11972.023,12205.097,11971.334,0.000,0.000,47857870369,524892592190.995,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,2022-04-07,15:00:03,00";
        String reg="var hq_str_(.+)=\"(.+)\";";
        //编译表达式,获取编译对象
        Pattern pattern = Pattern.compile(reg);
        //匹配字符串
        Matcher matcher = pattern.matcher(jsData);
        ArrayList<StockMarketIndexInfo> list = new ArrayList<>();
        //判断是否有匹配的数值
        while (matcher.find()){
            //获取大盘的code
            String marketCode = matcher.group(1);
            //获取其它信息，字符串以逗号间隔
            String otherInfo=matcher.group(2);
            //以逗号切割字符串，形成数组
            String[] splitArr = otherInfo.split(",");
            //大盘名称
            String marketName=splitArr[0];
            //获取当前大盘的开盘点数
            BigDecimal openPoint=new BigDecimal(splitArr[1]);
            //前收盘点
            BigDecimal preClosePoint=new BigDecimal(splitArr[2]);
            //获取大盘的当前点数
            BigDecimal curPoint=new BigDecimal(splitArr[3]);
            //获取大盘最高点
            BigDecimal maxPoint=new BigDecimal(splitArr[4]);
            //获取大盘的最低点
            BigDecimal minPoint=new BigDecimal(splitArr[5]);
            //获取成交量
            Long tradeAmt=Long.valueOf(splitArr[8]);
            //获取成交金额
            BigDecimal tradeVol=new BigDecimal(splitArr[9]);
            //时间
            Date curTime = DateTimeUtil.getDateTimeWithoutSecond(splitArr[30] + " " + splitArr[31]).toDate();
            //组装entity对象
            StockMarketIndexInfo info = StockMarketIndexInfo.builder()
                    .id(idWorker.nextId())
                    .marketCode(marketCode)
                    .marketName(marketName)
                    .curPoint(curPoint)
                    .openPoint(openPoint)
                    .preClosePoint(preClosePoint)
                    .maxPoint(maxPoint)
                    .minPoint(minPoint)
                    .tradeVolume(tradeVol)
                    .tradeAmount(tradeAmt)
                    .curTime(curTime)
                    .build();
            //收集封装的对象，方便批量插入
            list.add(info);
        }
        log.info("采集的当前大盘数据：{}",list);
        //批量插入
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        int count = stockMarketIndexInfoMapper.insertBatch(list);
        if (count > 0){
            //通知后台终端刷新本地缓存，发送的日期数据是告知对方当前更新的股票数据所在时间点
            // 发送日期对象，接收方通过接收的日期与当前日期比对，能判断出数据延迟的时长，用于运维通知处理
            //确定交换机和routingKey，和发送的消息.
            rabbitTemplate.convertAndSend("stockExchange","inner.market",new Date());
            log.info("当前时间：{}，插入大盘数据：{}成功",DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),list);
        }else{
            log.error("当前时间：{}，插入大盘数据：{}成功",DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),list);
        }
    }

    @Override
    public void getStockRtIndex() {
        List<String> allCodes = stockBusinessMapper.getStockCodes();
        System.out.println(allCodes);
        //添加大盘的业务前缀
        allCodes = allCodes.stream().map(code->code.startsWith("6")?"sh"+code:"sz"+code).collect(Collectors.toList());
        //将大数据分批次采集
        //使用guava工具类，对list做切分
        long startTime = System.currentTimeMillis();
        Lists.partition(allCodes, 15).forEach(codes-> {
            //原始方案,拆分
//            //分批次采集
//            String url = stockInfoConfig.getMarketUrl() + String.join(",",codes);
////            //2.1 组装请求头
////            HttpHeaders headers = new HttpHeaders();
////            //必须填写，否则数据采集不到
////            headers.add("Referer","https://finance.sina.com.cn/stock/");
////            headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
////            //2.2 组装请求对象
////            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
//            //2.3 resetTemplate发起请求
//            ResponseEntity<String> responseEntity = restTemplate.exchange(url,HttpMethod.GET,httpEntity,String.class);
//            int statusCodeValue = responseEntity.getStatusCodeValue();
//            if(statusCodeValue != 200){
//                //当前请求失败
//                log.error("当前时间点：{}，采集数据失败，http状态码：{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), statusCodeValue);
//                return;
//            }
//            //获取返回的js数据
//            String jsData = responseEntity.getBody();
//            // 调用工具类解析
//            List<StockRtInfo> list = parserStockInfoUtil.parser4StockOrMarketInfo(jsData, ParseType.ASHARE);
//            log.info("采集个股数据：{}", list);
//            int count = stockBusinessMapper.insertBatch(list);
//            if (count > 0){
//                log.info("当前时间：{}，插入个股数据：{}成功",DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),list);
//            }else{
//                log.error("当前时间：{}，插入个股数据：{}成功",DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),list);
//            }
//        });
            //方案1:原始方案采集个股数据时将集合分片，然后分批次串行采集数据，效率不高，存在较高的采集延迟:引入多线程
            //方案一：使用多线程
//            new Thread(() -> {
//                //分批次采集
//                String url = stockInfoConfig.getMarketUrl() + String.join(",", codes);
////            //2.1 组装请求头
////            HttpHeaders headers = new HttpHeaders();
////            //必须填写，否则数据采集不到
////            headers.add("Referer","https://finance.sina.com.cn/stock/");
////            headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
////            //2.2 组装请求对象
////            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
//                //2.3 resetTemplate发起请求
//                ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
//                int statusCodeValue = responseEntity.getStatusCodeValue();
//                if (statusCodeValue != 200) {
//                    //当前请求失败
//                    log.error("当前时间点：{}，采集数据失败，http状态码：{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), statusCodeValue);
//                    return;
//                }
//                //获取返回的js数据
//                String jsData = responseEntity.getBody();
//                // 调用工具类解析
//                List<StockRtInfo> list = parserStockInfoUtil.parser4StockOrMarketInfo(jsData, ParseType.ASHARE);
//                log.info("采集个股数据：{}", list);
//                int count = stockBusinessMapper.insertBatch(list);
//                if (count > 0) {
//                    log.info("当前时间：{}，插入个股数据：{}成功", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), list);
//                } else {
//                    log.error("当前时间：{}，插入个股数据：{}成功", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), list);
//                }
//            }).start();
//            方案2：使用多线程
            threadPoolTaskExecutor.execute(()->{
                //分批次采集
                String url = stockInfoConfig.getMarketUrl() + String.join(",", codes);
//            //2.1 组装请求头
//            HttpHeaders headers = new HttpHeaders();
//            //必须填写，否则数据采集不到
//            headers.add("Referer","https://finance.sina.com.cn/stock/");
//            headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
//            //2.2 组装请求对象
//            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
                //2.3 resetTemplate发起请求
                ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
                int statusCodeValue = responseEntity.getStatusCodeValue();
                if (statusCodeValue != 200) {
                    //当前请求失败
                    log.error("当前时间点：{}，采集数据失败，http状态码：{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), statusCodeValue);
                    return;
                }
                //获取返回的js数据
                String jsData = responseEntity.getBody();
                // 调用工具类解析
                List<StockRtInfo> list = parserStockInfoUtil.parser4StockOrMarketInfo(jsData, ParseType.ASHARE);
                log.info("采集个股数据：{}", list);
                int count = stockBusinessMapper.insertBatch(list);
                if (count > 0) {
                    log.info("当前时间：{}，插入个股数据：{}成功", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), list);
                } else {
                    log.error("当前时间：{}，插入个股数据：{}成功", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), list);
                }
            });
        });
        long takeTime = System.currentTimeMillis() - startTime;
        log.info("本地采集花费时间：{}ms",takeTime);
    }

    /**
     * bean生命周期初始化回调方法
     */
    @PostConstruct
    public void initData(){
        HttpHeaders headers = new HttpHeaders();
        //必须填写，否则数据采集不到
        headers.add("Referer","https://finance.sina.com.cn/stock/");
        headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        //2.2 组装请求对象
        httpEntity = new HttpEntity<>(headers);

    }
}