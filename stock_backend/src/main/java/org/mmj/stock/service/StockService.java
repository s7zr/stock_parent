package org.mmj.stock.service;

import org.mmj.stock.pojo.domain.*;
import org.mmj.stock.vo.resp.PageResult;
import org.mmj.stock.vo.resp.R;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface StockService {
    /**
     * 查询最新的国内大盘信息
     * @return
     */
    R<List<InnerMarketDomain>> getInnerIndexAll();
    /**
     * 需求说明: 获取沪深两市板块最新数据，以交易总金额降序查询，取前10条数据
     * @return
     */
    R<List<StockBlockDomain>> sectorAllLimit();
    /**
     * 分页查询股票最新数据，并按照涨幅排序查询
     * @param page
     * @param pageSize
     * @return
     */
    R<PageResult<StockUpdownDomain>> getStockPageInfo(Integer page, Integer pageSize);

    /**
     * 统计最新交易日下股票每分钟涨跌停的数量
     * @return
     */
    R<Map> getStockUpdownCount();
    /**
     * 将指定页的股票数据导出到excel表下
     * @param response
     * @param page  当前页
     * @param pageSize 每页大小
     */
    void stockExport(HttpServletResponse response, Integer page, Integer pageSize);
    /**
     * 功能描述：统计国内A股大盘T日和T-1日成交量对比功能（成交量为沪市和深市成交量之和）
     * @return
     */
    R<Map> stockTradeVol4InnerMarket();
    /**
     * 查询当前时间下股票的涨跌幅度区间统计功能
     * 如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询点
     * @return
     */
    R<Map> stockUpDownScopeCount();

    /**
     * 功能描述：查询单个个股的分时行情数据，也就是统计指定股票T日每分钟的交易数据；
     *         如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询时间点
     * @param code 股票编码
     * @return
     */
    R<List<Stock4MinuteDomain>> stockScreenTimeSharing(String code);

    /**
     * 单个个股日K 数据查询 ，可以根据时间区间查询数日的K线数据
     * @param code 股票编码
     */
    R<List<Stock4EvrDayDomain>> stockCreenDkLine(String code);

    /**
     * 查询最新的国外大盘信息
     * @return
     */
    R<List<OuterMarketDomain>> getOuterIndexAll();

    /**
     * 根据输入的个股代码，进行模糊查询，返回证券代码和证券名称
     * @return
     */
    R<List<Map>> getSearchStr(String searchStr);

    /**
     * 个股主营业务查询
     * @param code
     * @return
     */
    R<StockDescribeDomain> getStockDescribe(String code);

    /**
     * 获取个股最新分时行情数据
     * @param code
     * @return
     */
    R<Stock4HourDomain> getSecondDetail(String code);

    /**
     * 个股交易流水行情数据查询--查询最新交易流水，按照交易时间降序取前10
     * @param code
     * @return
     */
    R<List<StockRtLimit10>> getScreenSecond(String code);
}
