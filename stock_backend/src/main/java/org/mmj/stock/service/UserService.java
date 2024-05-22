package org.mmj.stock.service;

import org.mmj.stock.pojo.entity.SysUser;
import org.mmj.stock.vo.req.LoginReqVo;
import org.mmj.stock.vo.resp.LoginRespVo;
import org.mmj.stock.vo.resp.R;

import java.util.Map;

public interface UserService {
    /**
     * 根据账户名称查询用户信息
     * @param userName
     * @return
     */
    SysUser getUserByUserName(String userName);

    /**
     * 登录接口
     * @param vo
     * @return
     */
    R<LoginRespVo> login(LoginReqVo vo);

    /**
     * 登录校验码生成服务方法
     * @return
     */
    R<Map> getCaptchaCode();
}
