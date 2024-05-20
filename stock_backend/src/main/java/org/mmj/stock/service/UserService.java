package org.mmj.stock.service;

import org.mmj.stock.pojo.entity.SysUser;
import org.mmj.stock.vo.req.LoginReqVo;
import org.mmj.stock.vo.resp.LoginRespVo;
import org.mmj.stock.vo.resp.R;

public interface UserService {
    /**
     * 根据账户名称查询用户信息
     * @param userName
     * @return
     */
    SysUser getUserByUserName(String userName);

    R<LoginRespVo> login(LoginReqVo vo);
}
