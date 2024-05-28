package org.mmj.stock.mapper;

import org.apache.ibatis.annotations.Param;
import org.mmj.stock.pojo.domain.StockDescribeDomain;
import org.mmj.stock.pojo.entity.StockBusiness;
import org.mmj.stock.pojo.entity.StockRtInfo;

import java.util.List;
import java.util.Map;

/**
* @author mmj
* @description 针对表【stock_business(主营业务表)】的数据库操作Mapper
* @createDate 2024-05-19 15:13:03
* @Entity org.mmj.stock.pojo.entity.StockBusiness
*/
public interface StockBusinessMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBusiness record);

    int insertSelective(StockBusiness record);

    StockBusiness selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBusiness record);

    int updateByPrimaryKey(StockBusiness record);
    /**
     * 获取所有股票的code
     * @return
     */
    List<String> getStockCodes();

    /**
     * 批量插入功能
     * @param stockRtInfoList
     */
    int insertBatch(@Param("stockRtInfoList") List<StockRtInfo> stockRtInfoList);

    /**
     * 根据输入的个股代码，进行模糊查询，返回证券代码和证券名称
     * @return
     */
    List<Map> getSearchStr(@Param("searchStr") String searchStr);
    /**
     * 个股主营业务查询接口
     * @return
     */
    StockDescribeDomain getStockDescribe(String code);
}
