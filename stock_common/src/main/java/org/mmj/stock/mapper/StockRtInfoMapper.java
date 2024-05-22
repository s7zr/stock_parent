package org.mmj.stock.mapper;

import org.apache.ibatis.annotations.Param;
import org.mmj.stock.pojo.domain.StockUpdownDomain;
import org.mmj.stock.pojo.entity.StockRtInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author mmj
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
* @createDate 2024-05-19 15:13:03
* @Entity org.mmj.stock.pojo.entity.StockRtInfo
*/
public interface StockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockRtInfo record);

    int insertSelective(StockRtInfo record);

    StockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockRtInfo record);

    int updateByPrimaryKey(StockRtInfo record);

    /**
     * 查询指定时间点下股票数据的集合
     * @param curDate 日期时间
     * @return
     */
    List<StockUpdownDomain> getNewestStockInfo(@Param("curDate") Date curDate);

    /**
     * 查询指定时间范围内每分钟涨停或者跌停的数量
     * @param openTime 开始时间
     * @param curTime 结束时间 一般开始时间和结束时间在同一天
     * @param flag 约定:1->涨停 0:->跌停
     * @return
     */
    List<Map> getStockUpdownCount(@Param("openTime") Date openTime, @Param("curTime") Date curTime, @Param("flag") int flag);
}
