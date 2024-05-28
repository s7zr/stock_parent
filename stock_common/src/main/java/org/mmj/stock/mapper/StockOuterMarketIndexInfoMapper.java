package org.mmj.stock.mapper;

import org.apache.ibatis.annotations.Param;
import org.mmj.stock.pojo.domain.OuterMarketDomain;
import org.mmj.stock.pojo.entity.StockOuterMarketIndexInfo;

import java.util.Date;
import java.util.List;

/**
* @author mmj
* @description 针对表【stock_outer_market_index_info(外盘详情信息表)】的数据库操作Mapper
* @createDate 2024-05-19 15:13:03
* @Entity org.mmj.stock.pojo.entity.StockOuterMarketIndexInfo
*/
public interface StockOuterMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockOuterMarketIndexInfo record);

    int insertSelective(StockOuterMarketIndexInfo record);

    StockOuterMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockOuterMarketIndexInfo record);

    int updateByPrimaryKey(StockOuterMarketIndexInfo record);
    /**
     * 根据时间获取国外大盘数据
     * @param dateTime
     * @param outerCodes
     * @return
     */
    List<OuterMarketDomain> getOuterIndexByTimeAndCodes(@Param("dateTime") Date dateTime, @Param("outerCodes") List<String> outerCodes);

}
