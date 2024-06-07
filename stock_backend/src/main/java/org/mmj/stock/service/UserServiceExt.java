package org.mmj.stock.service;

import org.mmj.stock.pojo.entity.SysUser;
import org.mmj.stock.vo.req.LoginReqVo;
import org.mmj.stock.vo.req.UserPageReqVo;
import org.mmj.stock.vo.resp.LoginRespVo;
import org.mmj.stock.vo.resp.PageResult;
import org.mmj.stock.vo.resp.R;

import java.util.Map;

public interface UserServiceExt {
    /**
     * 多条件综合查询用户分页信息，条件包含：分页信息 用户创建日期范围
     * @param userPageReqVo
     * @return
     */
    R<PageResult> pageUsers(UserPageReqVo userPageReqVo);
}
