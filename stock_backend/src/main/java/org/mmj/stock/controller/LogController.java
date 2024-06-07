package org.mmj.stock.controller;

import org.mmj.stock.service.LogService;
import org.mmj.stock.vo.req.LogPageReqVo;
import org.mmj.stock.vo.resp.PageResult;
import org.mmj.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 21:22
 */
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
    @PostMapping("/logs")
    public R<PageResult> logPageQuery(@RequestBody LogPageReqVo vo){
        return logService.logPageQuery(vo);
    }
}
