package org.mmj.stock.service;

import org.mmj.stock.pojo.domain.InnerMarketDomain;
import org.mmj.stock.pojo.domain.StockBlockDomain;
import org.mmj.stock.pojo.domain.StockUpdownDomain;
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
}
