package org.mmj.stock.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.mmj.stock.pojo.domain.InnerMarketDomain;
import org.mmj.stock.pojo.domain.StockBlockDomain;
import org.mmj.stock.service.StockService;
import org.mmj.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 定义关于股票数据查询的接口bean
 */
@Api(value = "/api/quot", tags = {"定义关于股票数据查询的接口bean"})
@ApiModel(description = "定义关于股票数据查询的接口bean")
@RestController
@RequestMapping("/api/quot")
public class StockController {

    @Autowired
    private StockService stockService;

    /**
     * 查询最新的国内大盘信息
     *
     * @return
     */
    @ApiOperation("查询最新的国内大盘信息")
    @GetMapping("/index/all")
    public R<List<InnerMarketDomain>> getInnerIndexAll() {
        return stockService.getInnerIndexAll();
    }

    /**
     *需求说明: 获取沪深两市板块最新数据，以交易总金额降序查询，取前10条数据
     * @return
     */
    @ApiOperation("获取沪深两市板块最新数据，以交易总金额降序查询，取前10条数据")
    @GetMapping("/sector/all")
    public R<List<StockBlockDomain>> sectorAll(){
        return stockService.sectorAllLimit();
    }
}
