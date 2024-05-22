package org.mmj.stock.mapper;

import org.apache.ibatis.annotations.Param;
import org.mmj.stock.pojo.domain.InnerMarketDomain;
import org.mmj.stock.pojo.entity.StockMarketIndexInfo;

import java.util.Date;
import java.util.List;

/**
* @author mmj
* @description 针对表【stock_market_index_info(国内大盘数据详情表)】的数据库操作Mapper
* @createDate 2024-05-19 15:13:03
* @Entity org.mmj.stock.pojo.entity.StockMarketIndexInfo
*/
public interface StockMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockMarketIndexInfo record);

    int insertSelective(StockMarketIndexInfo record);

    StockMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockMarketIndexInfo record);

    int updateByPrimaryKey(StockMarketIndexInfo record);

    List<InnerMarketDomain> getInnerIndexByTimeAndCodes(@Param("dateTime") Date dateTime, @Param("innerCodes") List<String> innerCodes);
}
