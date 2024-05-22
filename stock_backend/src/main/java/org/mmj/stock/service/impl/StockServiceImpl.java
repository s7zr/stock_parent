package org.mmj.stock.service.impl;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.mmj.stock.mapper.StockBlockRtInfoMapper;
import org.mmj.stock.mapper.StockMarketIndexInfoMapper;
import org.mmj.stock.pojo.domain.InnerMarketDomain;
import org.mmj.stock.pojo.domain.StockBlockDomain;
import org.mmj.stock.pojo.vo.StockInfoConfig;
import org.mmj.stock.service.StockService;
import org.mmj.stock.utils.DateTimeUtil;
import org.mmj.stock.vo.resp.R;
import org.mmj.stock.vo.resp.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
@Service("stockService")
public class StockServiceImpl implements StockService {
    @Autowired
    private StockInfoConfig stockInfoConfig;//封装好的国内外大盘信息

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;
    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;
    @Override
    public R<List<InnerMarketDomain>> getInnerIndexAll() {
        //TODO 国内大盘实现
        //1.获取最新的股票交易时间点
        Date lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //TODO 伪造数据，后续删除
        lastDate=DateTime.parse("2022-01-03 09:47:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //2.获取国内大盘编码集合
        List<String> innerCodes = stockInfoConfig.getInner();
        //3.调用mapper查询
        List<InnerMarketDomain> infos= stockMarketIndexInfoMapper.getInnerIndexByTimeAndCodes(lastDate,innerCodes);
        //4.响应
        return R.ok(infos);
    }

    /**
     *需求说明: 沪深两市板块分时行情数据查询，以交易时间和交易总金额降序查询，取前10条数据
     * @return
     */
    @Override
    public R<List<StockBlockDomain>> sectorAllLimit() {
        //获取股票最新交易时间点
        Date lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //TODO mock数据,后续删除
        lastDate=DateTime.parse("2021-12-21 14:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //1.调用mapper接口获取数据
        List<StockBlockDomain> infos=stockBlockRtInfoMapper.sectorAllLimit(lastDate);
        //2.组装数据
        if (CollectionUtils.isEmpty(infos)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return R.ok(infos);
    }
}
