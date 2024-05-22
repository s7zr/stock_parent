package org.mmj.stock.service;

import org.mmj.stock.pojo.domain.InnerMarketDomain;
import org.mmj.stock.pojo.domain.StockBlockDomain;
import org.mmj.stock.vo.resp.R;

import java.util.List;

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
}
