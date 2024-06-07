package org.mmj.stock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mmj.stock.mapper.SysLogMapper;
import org.mmj.stock.pojo.entity.SysLog;
import org.mmj.stock.service.LogService;
import org.mmj.stock.vo.req.LogPageReqVo;
import org.mmj.stock.vo.resp.PageResult;
import org.mmj.stock.vo.resp.R;
import org.mmj.stock.vo.resp.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mmj
 * @Description
 * @create 2024-06-07 22:06
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public R<PageResult> logPageQuery(LogPageReqVo vo) {
        if (vo==null) {
            return R.error(ResponseCode.DATA_ERROR.getMessage()) ;
        }
        //组装数据
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        //分页查询
        List<SysLog> logList=this.sysLogMapper.findByCondition(vo.getUsername(),vo.getOperation(),vo.getStartTime(),vo.getEndTime());
        //封装PageResult
        PageResult<SysLog> pageResult = new PageResult<>(new PageInfo<>(logList));
        return R.ok(pageResult);
    }
}
