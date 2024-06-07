package org.mmj.stock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.mmj.stock.mapper.*;
import org.mmj.stock.pojo.domain.*;
import org.mmj.stock.service.UserServiceExt;
import org.mmj.stock.vo.req.UserPageReqVo;
import org.mmj.stock.vo.resp.PageResult;
import org.mmj.stock.vo.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service("userServiceExt")
@Slf4j
public class UserServiceIExtImpl implements UserServiceExt {
    @Autowired
    private SysUserMapperExt sysUserMapperExt;

    /**
     * 多条件综合查询用户分页信息，条件包含：分页信息 用户创建日期范围
     * @param vo
     * @return
     */
    @Override
    public R<PageResult> pageUsers(UserPageReqVo vo) {
        //组装分页数据
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        //设置查询条件
        List<UserQueryDomain> users= this.sysUserMapperExt.pageUsers(vo.getUsername(),vo.getNickName(),vo.getStartTime(),vo.getEndTime());
        if (CollectionUtils.isEmpty(users)) {
            return R.error("没有数据");
        }
        PageResult<UserQueryDomain> pageResult = new PageResult<>(new PageInfo<>(users));
        return R.ok(pageResult);
    }
}
