package org.mmj.stock.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.mmj.stock.service.LogService;
import org.mmj.stock.vo.req.LogPageReqVo;
import org.mmj.stock.vo.resp.PageResult;
import org.mmj.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 21:22
 */
@Api(tags = "日志接口")
@RestController
@RequestMapping("/api")
public class LogController {
    @Autowired
    private LogService logService;
    /**
     * 日志信息综合查询
     * @param vo
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "LogPageReqVo", name = "vo", value = "", required = true)
    })
    @ApiOperation(value = "日志信息综合查询", notes = "日志信息综合查询", httpMethod = "POST")
    @PreAuthorize("hasAuthority('sys:log:list')")
    @PostMapping("/logs")
    public R<PageResult> logPageQuery(@RequestBody LogPageReqVo vo){
        return logService.logPageQuery(vo);
    }
}
