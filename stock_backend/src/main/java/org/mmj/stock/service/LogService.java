package org.mmj.stock.service;

import org.mmj.stock.vo.req.LogPageReqVo;
import org.mmj.stock.vo.resp.PageResult;
import org.mmj.stock.vo.resp.R;

import java.util.List;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 21:24
 */
public interface LogService {
    /**
     * 日志信息分页综合查询
     * @param vo
     * @return
     */
    R<PageResult> logPageQuery(LogPageReqVo vo);
}
